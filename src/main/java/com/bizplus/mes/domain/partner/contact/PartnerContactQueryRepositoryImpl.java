package com.bizplus.mes.domain.partner.contact;

import com.bizplus.mes.domain.code.common.QCommonCode;
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
    public List<PartnerContact> findPartnerContacts(Long partnerId) {

        return query
                .selectFrom(partnerContact)
                .leftJoin(partnerContact.department, departmentCode).fetchJoin()
                .leftJoin(partnerContact.position, positionCode).fetchJoin()
                .where(
                        notDeleted(partnerContact.deletedAt),
                        eq(partnerContact.partner.id, partnerId)
                )
                .orderBy(partnerContact.name.asc())
                .fetch();
    }

    @Override
    public Optional<PartnerContact> findPartnerContact(Long id) {

        return Optional.ofNullable(
                query
                        .selectFrom(partnerContact)
                        .leftJoin(partnerContact.department, departmentCode).fetchJoin()
                        .leftJoin(partnerContact.position, positionCode).fetchJoin()
                        .where(
                                notDeleted(partnerContact.deletedAt),
                                eq(partnerContact.id, id)
                        )
                        .fetchOne()
        );
    }
}
