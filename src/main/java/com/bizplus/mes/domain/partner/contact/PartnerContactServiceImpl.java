package com.bizplus.mes.domain.partner.contact;

import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.partner.PartnerReader;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactCreateDto;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactDto;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerContactServiceImpl implements PartnerContactService {

    private final PartnerContactRepository partnerContactRepository;

    private final CommonCodeReader commonCodeReader;
    private final PartnerReader partnerReader;
    private final PartnerContactReader partnerContactReader;

    @Override
    public List<PartnerContactDto> getPartnerContacts(Long partnerId) {

        return partnerContactRepository.findPartnerContacts(partnerId).stream()
                .map(PartnerContactMapper::toDto).toList();
    }

    @Override
    public PartnerContactDto getPartnerContact(Long id) {

        return PartnerContactMapper.toDto(partnerContactReader.getById(id));
    }

    @Override
    public void createPartnerContact(Long partnerId, PartnerContactCreateDto dto) {

        Partner partner = partnerReader.getById(partnerId);
        CommonCode department = commonCodeReader.getOrNull(dto.getDepartmentId());
        CommonCode position = commonCodeReader.getOrNull(dto.getPositionId());

        partnerContactRepository.save(PartnerContactMapper
                .toEntity(partner, department, position, dto));
    }

    @Transactional
    @Override
    public void updatePartnerContact(Long id, PartnerContactUpdateDto dto) {

        PartnerContact partnerContact = partnerContactReader.getById(id);
        CommonCode department = commonCodeReader.getOrNull(dto.getDepartmentId());
        CommonCode position = commonCodeReader.getOrNull(dto.getPositionId());

        PartnerContactMapper.apply(partnerContact, department, position, dto);
    }

    @Transactional
    @Override
    public void deletePartnerContacts(List<Long> ids) {

        ids.forEach(id -> partnerContactReader.getById(id).delete());
    }
}
