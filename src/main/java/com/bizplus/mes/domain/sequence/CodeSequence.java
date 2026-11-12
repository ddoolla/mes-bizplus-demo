package com.bizplus.mes.domain.sequence;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "code_sequences")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeSequence extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sequenceKey;

    @Column(nullable = false)
    private Long lastNumber;

    public CodeSequence(String sequenceKey) {
        this.sequenceKey = sequenceKey;
        this.lastNumber = 0L;
    }

    public long nextNumber() {
        return ++lastNumber;
    }
}
