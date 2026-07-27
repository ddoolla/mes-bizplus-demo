package com.bizplus.mes.domain.partner.contact;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactDto;
import com.bizplus.mes.domain.partner.contact.dto.QPartnerContactDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.common.util.PredicateUtils.notDeleted;
import static com.bizplus.mes.domain.partner.contact.QPartnerContact.partnerContact;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartnerContactQueryRepositoryImpl implements PartnerContactQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode departmentCode = new QCommonCode("departmentCode");
    private static final QCommonCode positionCode = new QCommonCode("positionCode");

    @Override
    public List<PartnerContactDto> findPartnerContacts(Long partnerId) {

        return query
                .select(new QPartnerContactDto(
                        partnerContact.id,
                        partnerContact.partner.id,
                        departmentCode.id,
                        departmentCode.name,
                        positionCode.id,
                        positionCode.name,
                        partnerContact.name,
                        partnerContact.phone,
                        partnerContact.tel,
                        partnerContact.email,
                        partnerContact.remark,
                        partnerContact.active
                ))
                .from(partnerContact)
                .leftJoin(departmentCode).on(partnerContact.department.id.eq(departmentCode.id))
                .leftJoin(positionCode).on(partnerContact.position.id.eq(positionCode.id))
                .where(
                        notDeleted(partnerContact.deletedAt),
                        eq(partnerContact.partner.id, partnerId)
                )
                .orderBy(partnerContact.name.asc())
                .fetch();
    }

    @Override
    public Optional<PartnerContactDto> findPartnerContact(Long id) {

        return Optional.ofNullable(
                query
                        .select(new QPartnerContactDto(
                                partnerContact.id,
                                partnerContact.partner.id,
                                departmentCode.id,
                                departmentCode.name,
                                positionCode.id,
                                positionCode.name,
                                partnerContact.name,
                                partnerContact.phone,
                                partnerContact.tel,
                                partnerContact.email,
                                partnerContact.remark,
                                partnerContact.active
                        ))
                        .from(partnerContact)
                        .leftJoin(departmentCode).on(partnerContact.department.id.eq(departmentCode.id))
                        .leftJoin(positionCode).on(partnerContact.position.id.eq(positionCode.id))
                        .where(
                                notDeleted(partnerContact.deletedAt),
                                eq(partnerContact.id, id)
                        )
                        .fetchOne()
        );
    }
}
