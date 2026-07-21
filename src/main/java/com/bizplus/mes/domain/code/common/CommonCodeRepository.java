package com.bizplus.mes.domain.code.common;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonCodeRepository extends
        JpaRepository<CommonCode, Long>, CommonCodeQueryRepository {

    Optional<CommonCode> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndIdNot(String code, Long id);
}
