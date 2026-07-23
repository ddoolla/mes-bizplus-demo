package com.bizplus.mes.domain.partner;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.partner.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerReader partnerReader;

    @Override
    public PartnerListDto getPartners(PartnerSearchDto dto, Pageable pageable) {

        Page<PartnerDto> partnerPage = partnerRepository.findPartners(dto, pageable)
                .map(PartnerMapper::toDto);

        return new PartnerListDto(
                partnerPage.getContent(),
                Pagination.of(partnerPage));
    }

    @Override
    public PartnerDto getPartner(Long id) {

        return PartnerMapper.toDto(partnerReader.getById(id));
    }

    @Override
    public boolean checkPartnerCode(Long id, String code) {

        boolean exists = partnerRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Override
    public void createPartner(PartnerCreateDto dto) {

        partnerRepository.save(PartnerMapper.toEntity(dto));
    }

    @Transactional
    @Override
    public void updatePartner(Long id, PartnerUpdateDto dto) {

        Partner partner = partnerReader.getById(id);
        PartnerMapper.apply(partner, dto);
    }

    @Transactional
    @Override
    public void deletePartners(List<Long> ids) {

        ids.forEach(id -> partnerReader.getById(id).delete());
    }
}
