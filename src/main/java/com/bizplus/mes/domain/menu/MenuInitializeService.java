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

        for (MenuInitialData data : MenuInitialData.values()) {

            Menu parentMenu = null;

            if (data.getParentMenu() != null) {
                parentMenu = menuRepository.findByCode(data.getParentMenu().getCode())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.MENU_NOT_FOUND, data.getCode()));
            }

            Menu menu = menuRepository.findByCode(data.getCode())
                    .orElse(null);

            if (menu != null) {
                menu.update(
                        parentMenu,
                        data.getCode(),
                        data.getName(),
                        data.getType(),
                        data.getPath(),
                        data.getSortOrder()
                );
            } else {

                Menu newMenu = new Menu(
                        parentMenu,
                        data.getCode(),
                        data.getName(),
                        data.getType(),
                        data.getPath(),
                        data.getSortOrder()
                );

                menuRepository.save(newMenu);
            }
        }
    }
}
