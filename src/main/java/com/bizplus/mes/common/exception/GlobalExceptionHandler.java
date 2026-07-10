package com.bizplus.mes.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public String handleBusinessException(BusinessException e, Model model) {
        log.error("{} {}", e.getMessage(), e.getDetail());
        model.addAttribute("message", e.getMessage());
        return "error/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        log.error("System error", e);
        return "error/500";
    }
}
