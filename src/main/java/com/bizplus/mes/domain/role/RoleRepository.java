package com.bizplus.mes.domain.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleQueryRepository {

    List<Role> findAllByDeletedAtIsNull();

    Optional<Role> findByIdAndDeletedAtIsNull(Long id);

    Optional<Role> findByCode(String code);
}
