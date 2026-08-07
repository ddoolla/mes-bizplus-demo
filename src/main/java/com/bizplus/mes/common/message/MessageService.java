package com.bizplus.mes.common.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public String get(MessageCode code) {

        return messageSource.getMessage(
                code.getKey(),
                null,
                LocaleContextHolder.getLocale()
        );
    }

    public String get(MessageCode code, Object... args) {

        return messageSource.getMessage(
                code.getKey(),
                args,
                LocaleContextHolder.getLocale()
        );
    }
}
