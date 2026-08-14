package com.bizplus.mes.domain.bom;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.bom.dto.*;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BomServiceImpl implements BomService {

    private final BomRepository bomRepository;

    private final BomReader bomReader;
    private final ItemReader itemReader;

    @Override
    public BomListDto getBoms(BomSearchDto dto, Pageable pageable) {
        Page<BomDto> bomPage = bomRepository.findBoms(dto, pageable);

        return new BomListDto(bomPage.getContent(), Pagination.of(bomPage));
    }

    @Override
    public BomDto getBom(Long id) {
        return bomRepository.findBom(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOM_NOT_FOUND, "id: " + id));
    }

    /*
    * 논리삭제된 코드도 중복으로 간주
    * */
    @Override
    public boolean checkCode(Long id, String code) {
        boolean exists = bomRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Override
    public Long createBom(BomCreateDto dto) {
        // todo 완제품 반제품 검증
        Item item = itemReader.getById(dto.getItemId());
        Integer nextRevisionNo = bomRepository.findNextRevisionNo(dto.getItemId());

        return bomRepository.save(BomMapper.toEntity(item, nextRevisionNo, dto)).getId();
    }

    @Transactional
    @Override
    public void updateBom(Long id, BomUpdateDto dto) {
        // todo 완제품 반제품 검증
        Bom bom = bomReader.getById(id);
        Item item = itemReader.getById(dto.getItemId());

        BomMapper.apply(bom, item, dto);
    }

    @Transactional
    @Override
    public void deleteBoms(List<Long> ids) {
        ids.forEach(id -> bomReader.getById(id).delete());
    }
}
