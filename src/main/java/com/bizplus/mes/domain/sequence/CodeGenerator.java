package com.bizplus.mes.domain.sequence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class CodeGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    private static final DateTimeFormatter KEY_DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE; // yyyyMMdd

    private final CodeSequenceRepository codeSequenceRepository;

    @Transactional
    public String generate(CodePrefix prefix) {
        String sequenceKey = prefix.getValue();

        Long number = nextNumber(sequenceKey);

        return "%s-%06d".formatted(prefix.getValue(), number);
    }

    @Transactional
    public String generate(CodePrefix prefix, LocalDate date) {
        String sequenceKey = "%s-%s".formatted(
                prefix.getValue(),
                date.format(KEY_DATE_FORMATTER)
        );

        long number = nextNumber(sequenceKey);

        return "%s-%s-%03d".formatted(
                prefix.getValue(),
                date.format(DATE_FORMATTER),
                number
        );
    }

    private Long nextNumber(String sequenceKey) {
        CodeSequence sequence = codeSequenceRepository.findForUpdate(sequenceKey)
                .orElseGet(() -> codeSequenceRepository.save(new CodeSequence(sequenceKey)));

        return sequence.nextNumber();
    }
}
