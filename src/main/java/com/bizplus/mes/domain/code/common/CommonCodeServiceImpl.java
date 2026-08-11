package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.domain.code.common.dto.CommonCodeCreateDto;
import com.bizplus.mes.domain.code.common.dto.CommonCodeDto;
import com.bizplus.mes.domain.code.common.dto.CommonCodeUpdateDto;
import com.bizplus.mes.domain.code.group.CodeGroup;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.code.group.CodeGroupReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    private final CommonCodeReader commonCodeReader;
    private final CodeGroupReader codeGroupReader;

    @Override
    public List<CommonCodeDto> getCommonCodes(Long codeGroupId, String code, String name) {

        return commonCodeRepository.findCommonCodes(codeGroupId, code, name);
    }

    @Override
    public List<CommonCodeDto> getCommonCodes(CodeGroupKey groupKey) {
        return commonCodeRepository.findCommonCodes(groupKey);
    }

    @Override
    public CommonCodeDto getCommonCode(Long id) {

        return commonCodeRepository.findByIdAndDeletedAtIsNull(id)
                .map(CommonCodeMapper::toDto)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.COMMON_CODE_NOT_FOUND, "id: " + id));
    }

    @Override
    public boolean checkCode(Long groupId, Long id, String code) {

        CodeGroup codeGroup = codeGroupReader.getById(groupId);

        boolean exists = commonCodeRepository.existsByGroupAndCodeAndIdNot(codeGroup, code, id);

        return !exists;
    }

    @Override
    public void createCommonCode(Long groupId, CommonCodeCreateDto dto) {

        CodeGroup codeGroup = codeGroupReader.getById(groupId);

        commonCodeRepository.save(CommonCodeMapper.toEntity(dto, codeGroup));
    }

    @Transactional
    @Override
    public void updateCommonCode(Long id, CommonCodeUpdateDto dto) {

        commonCodeReader.getById(id)
                .update(dto.getCode(),
                        dto.getName(),
                        dto.getDescription());
    }

    @Transactional
    @Override
    public void deleteCommonCodes(List<Long> ids) {

        ids.forEach(id -> {
            commonCodeReader.getById(id).delete();
        });
    }
}
