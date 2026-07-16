package com.bizplus.mes.domain.code.group;

import com.bizplus.mes.domain.code.group.dto.CodeGroupDto;

import java.util.List;
import java.util.Optional;

public interface CodeGroupQueryRepository {

    List<CodeGroupDto> findCodeGroups(String menuName, String name);

    Optional<CodeGroupDto> findCodeGroup(Long id);
}
