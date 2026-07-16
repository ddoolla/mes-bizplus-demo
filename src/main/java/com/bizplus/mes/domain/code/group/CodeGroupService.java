package com.bizplus.mes.domain.code.group;

import com.bizplus.mes.domain.code.group.dto.CodeGroupDto;

import java.util.List;

public interface CodeGroupService {

    List<CodeGroupDto> getCodeGroups(String menu, String name);

    CodeGroupDto getCodeGroup(Long id);
}
