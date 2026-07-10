package com.bizplus.mes.domain.menu;

import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuInitializeService {

    private final MenuRepository menuRepository;

    @Transactional
    public void initialize() {

        for (MenuSeed menuSeed : MenuSeed.values()) {

            Menu parentMenu = null;

            if (menuSeed.getParentMenu() != null) {
                parentMenu = menuRepository.findByCode(menuSeed.getParentMenu())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.MENU_NOT_FOUND, menuSeed.getCode()));
            }

            Menu menu = menuRepository.findByCode(menuSeed.getCode())
                    .orElse(null);

            if (menu != null) {
                menu.update(
                        parentMenu,
                        menuSeed.getCode(),
                        menuSeed.getName(),
                        menuSeed.getType(),
                        menuSeed.getPath(),
                        menuSeed.getSortOrder()
                );
            } else {

                Menu newMenu = new Menu(
                        parentMenu,
                        menuSeed.getCode(),
                        menuSeed.getName(),
                        menuSeed.getType(),
                        menuSeed.getPath(),
                        menuSeed.getSortOrder()
                );

                menuRepository.save(newMenu);
            }
        }
    }
}
