package com.bizplus.mes.domain.code.group;

import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.exception.NotFoundException;
import com.bizplus.mes.domain.menu.Menu;
import com.bizplus.mes.domain.menu.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CodeGroupInitializeService {

    private final MenuRepository menuRepository;
    private final CodeGroupRepository codeGroupRepository;

    @Transactional
    public void initialize() {

        for (CodeGroupKey groupKey : CodeGroupKey.values()) {

            Menu menu = menuRepository.findByCode(groupKey.getMenu())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.MENU_NOT_FOUND, groupKey.getMenu()));

            CodeGroup existingCodeGroup = codeGroupRepository.findByGroupKey(groupKey).orElse(null);

            if (existingCodeGroup != null) {

                existingCodeGroup.update(menu, groupKey);

            } else {

                codeGroupRepository.save(new CodeGroup(menu, groupKey));
            }
        }
    }
}
