package com.bizplus.mes.domain.code.group;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.code.group.dto.CodeGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeGroupServiceImpl implements CodeGroupService {

    private final CodeGroupRepository codeGroupRepository;

    @Override
    public List<CodeGroupDto> getCodeGroups(String menu, String name) {

        return codeGroupRepository.findCodeGroups(menu, name);
    }

    @Override
    public CodeGroupDto getCodeGroup(Long id) {

        return codeGroupRepository.findCodeGroup(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.CODE_GROUP_NOT_FOUND, "id: " + id));
    }
}
