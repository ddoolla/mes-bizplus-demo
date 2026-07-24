package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.domain.code.group.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonCodeRepository extends
        JpaRepository<CommonCode, Long>, CommonCodeQueryRepository {

    Optional<CommonCode> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByGroupAndCodeAndIdNot(CodeGroup group, String code, Long id);
}
