package com.bizplus.mes.domain.material.input;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialInputRepository extends JpaRepository<MaterialInput, Long>, MaterialInputQueryRepository {
}
