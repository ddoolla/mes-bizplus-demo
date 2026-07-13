package com.bizplus.mes.common.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalModelAttributeAdvice {

    @ModelAttribute
    public void addPaginationPath(Model model, HttpServletRequest request) {

        String requestPath = request.getServletPath();

        String queryString = request.getParameterMap().entrySet().stream()
                .filter(entry -> !"page".equals(entry.getKey()))
                .flatMap(entry -> Arrays.stream(entry.getValue())
                        .map(value -> entry.getKey() + "=" + value))
                .collect(Collectors.joining("&"));

        String path = queryString.isBlank()
                ? requestPath
                : requestPath + "?" + queryString;

        model.addAttribute("path", path);
    }
}
