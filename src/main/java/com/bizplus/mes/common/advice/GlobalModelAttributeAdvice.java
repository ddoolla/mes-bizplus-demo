package com.bizplus.mes.common.advice;

import com.bizplus.mes.domain.menu.Menu;
import com.bizplus.mes.domain.menu.MenuRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;
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
    public void addMenu(Model model) {

        List<Menu> menus = menuRepository.findAll();

        model.addAttribute("menus", menus);
    }
}
