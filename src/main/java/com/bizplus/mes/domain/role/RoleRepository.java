package com.bizplus.mes.domain.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleQueryRepository {

    Optional<Role> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCode(String code);
}
