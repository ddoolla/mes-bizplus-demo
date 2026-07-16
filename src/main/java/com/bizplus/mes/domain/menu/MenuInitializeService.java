package com.bizplus.mes.domain.menu;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuInitializeService {

    private final MenuRepository menuRepository;

    @Transactional
    public void initialize() {

        for (MenuCode menuCode : MenuCode.values()) {

            Menu parent = null;

            if (menuCode.getParent() != null) {
                parent = menuRepository.findByCode(menuCode.getParent())
                        .orElseThrow(() -> new BusinessException(
                                ErrorCode.MENU_NOT_FOUND, "menu: " + menuCode.getParent().name()));
            }

            Menu existingMenu = menuRepository.findByCode(menuCode)
                    .orElse(null);

            if (existingMenu != null) {

                existingMenu.update(parent, menuCode);

            } else {

                menuRepository.save(new Menu(parent, menuCode));
            }
        }
    }
}
