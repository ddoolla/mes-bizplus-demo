package com.bizplus.mes.domain.log.auth;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String loginId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String sessionId;

    private String ipAddress;

    @Column(nullable = false)
    private LocalDateTime loginAt;

    private LocalDateTime logoutAt;

    @Column(columnDefinition = "varchar255")
    @Enumerated(EnumType.STRING)
    private LogoutType logoutType;

    public UserAuthLog(String loginId,
                       String userName,
                       String sessionId,
                       String ipAddress,
                       LocalDateTime loginAt) {
        this.loginId = loginId;
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
