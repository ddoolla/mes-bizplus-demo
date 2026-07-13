package com.bizplus.mes.domain.user.role;

import com.bizplus.mes.domain.role.Role;
import com.bizplus.mes.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    boolean existsByUserAndRole(User user, Role role);
}
