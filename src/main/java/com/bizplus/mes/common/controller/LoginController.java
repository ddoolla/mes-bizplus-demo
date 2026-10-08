package com.bizplus.mes.common.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    /*
    * PLC 프로그램 연결용
    * 원래 다른 프로젝트에서 로그인 정보를 담아서 요청하면 JWT 토큰 발급 후 통신했었음.
    * */
    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<Void> loginForPlcDataCollector(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + "test")
                .build();
    }

    public record LoginRequest(
            @NotBlank String loginId,
            @NotBlank String password
    ) {
    }
}
