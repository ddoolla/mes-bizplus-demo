package com.bizplus.mes.domain.log.action;

import com.bizplus.mes.domain.menu.MenuCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_action_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserActionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuCode menu;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LogResult result;

    @Column(nullable = false)
    private String requestUri;

    private String ipAddress;

    @Column(length = 500)
    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public UserActionLog(String userId,
                         String userName,
                         MenuCode menu,
                         ActionType type,
                         LogResult result,
                         String requestUri,
                         String ipAddress,
                         String errorMessage,
                         LocalDateTime createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.menu = menu;
        this.type = type;
        this.result = result;
        this.requestUri = requestUri;
        this.ipAddress = ipAddress;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
    }
}
