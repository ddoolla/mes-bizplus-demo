package com.bizplus.mes.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryRepository {

    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    Optional<User> findByUserIdAndDeletedAtIsNull(String userId);

    boolean existsByUserId(String userId);
}
