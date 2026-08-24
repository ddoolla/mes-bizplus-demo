package com.bizplus.mes.common.advice;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // todo validation 예외처리

    @ExceptionHandler(BusinessException.class)
    public Object handleBusinessException(BusinessException e,
                                          HttpServletRequest request,
                                          Model model) {
        log.error("{} {}", e.getMessage(), e.getDetail());

        if (isAjax(request)) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.fail("오류가 발생했습니다."));
        }

        model.addAttribute("message", e.getMessage());

        return "error/error";
    }

    /*
    * DevTools 관련 오류가 콘솔에 계속 출력되서 추가한 핸들러
    * */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFound(NoResourceFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest request) {
        log.error("System error", e);

        if (isAjax(request)) {
            return ResponseEntity
                    .internalServerError()
                    .body(ApiResponse.fail("오류가 발생했습니다. 관리자에게 문의해주세요."));
        }

        return "error/500";
    }

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
