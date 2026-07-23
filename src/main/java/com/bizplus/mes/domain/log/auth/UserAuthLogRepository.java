package com.bizplus.mes.domain.log.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthLogRepository extends
        JpaRepository<UserAuthLog, Long>, UserAuthLogQueryRepository {

    Optional<UserAuthLog> findBySessionIdAndLogoutAtIsNull(String sessionId);
}
