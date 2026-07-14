package com.bizplus.mes.common.advice;

import com.bizplus.mes.domain.menu.Menu;
import com.bizplus.mes.domain.menu.MenuRepository;
import com.bizplus.mes.domain.menu.MenuType;
import com.bizplus.mes.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributeAdvice {

    private final MenuRepository menuRepository;

    @ModelAttribute
    public void addPaginationPath(Model model, HttpServletRequest request) {

        String requestPath = request.getServletPath();

        String queryString = request.getParameterMap().entrySet().stream()
                .filter(entry -> !"page".equals(entry.getKey()))
                .flatMap(entry -> Arrays.stream(entry.getValue())
                        .map(value -> entry.getKey() + "=" + value))
                .collect(Collectors.joining("&"));

        String path = queryString.isBlank()
                ? requestPath
                : requestPath + "?" + queryString;

        model.addAttribute("path", path);
    }

    @ModelAttribute
    public void addAccessibleMenu(Model model,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return;
        }

        Set<String> permissionCodes = userDetails.getPermissionCode();

        List<Menu> menus = menuRepository.findAll();

        List<Menu> accessibleMenus = menus.stream()
                .filter(menu -> {

                    // 메뉴 그룹
                    if (menu.getType() == MenuType.GROUP) {

                        return menus.stream()
                                .anyMatch(childMenu ->
                                        childMenu.getParent() != null
                                                && childMenu.getParent().getId().equals(menu.getId())
                                                && permissionCodes.contains(childMenu.getCode() + "_READ")
                                );
                    }

                    // 하위 메뉴
                    return permissionCodes.contains(menu.getCode() + "_READ");
                })
                .toList();

        model.addAttribute("menus", accessibleMenus);
    }
}
