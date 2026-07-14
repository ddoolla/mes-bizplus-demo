package com.bizplus.mes.domain.user.role;

import com.bizplus.mes.domain.role.Role;
import com.bizplus.mes.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByUser(User user);

    boolean existsByUserAndRole(User user, Role role);

    void deleteByUser(User user);
}
