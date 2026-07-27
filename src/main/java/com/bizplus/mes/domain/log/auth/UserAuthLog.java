package com.bizplus.mes.domain.log.auth;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_auth_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String sessionId;

    private String ipAddress;

    @Column(nullable = false)
    private LocalDateTime loginAt;

    private LocalDateTime logoutAt;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private LogoutType logoutType;

    public UserAuthLog(String userId,
                       String userName,
                       String sessionId,
                       String ipAddress,
                       LocalDateTime loginAt) {
        this.userId = userId;
        this.userName = userName;
        this.sessionId = sessionId;
        this.ipAddress = ipAddress;
        this.loginAt = loginAt;
    }

    public void updateLogout(LocalDateTime logoutAt, LogoutType logoutType) {

        this.logoutAt = logoutAt;
        this.logoutType = logoutType;
    }
}
