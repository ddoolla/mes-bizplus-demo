package com.bizplus.mes.domain.uom;

import com.bizplus.mes.domain.uom.dto.UomCreateDto;
import com.bizplus.mes.domain.uom.dto.UomDto;
import com.bizplus.mes.domain.uom.dto.UomUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UomServiceImpl implements UomService {

    private final UomRepository uomRepository;

    private final UomReader uomReader;

    @Override
    public List<UomDto> getUoms(String code, String name) {
        return uomRepository.findUoms(code,name).stream()
                .map(UomMapper::toDto).toList();
    }

    @Override
    public List<UomDto> getUoms() {
        return getUoms(null,  null);
    }

    @Override
    public UomDto getUom(Long id) {
        return UomMapper.toDto(uomReader.getById(id));
    }

    @Override
    public boolean checkCode(Long id, String code) {
        boolean exists = uomRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Override
    public void createUom(UomCreateDto dto) {
        uomRepository.save(UomMapper.toEntity(dto));
    }

    @Transactional
    @Override
    public void updateUom(Long id, UomUpdateDto dto) {
        Uom uom = uomReader.getById(id);
        UomMapper.apply(uom, dto);
    }

    @Transactional
    @Override
    public void deleteUoms(List<Long> ids) {
        ids.forEach(id -> uomReader.getById(id).delete());
    }
}
