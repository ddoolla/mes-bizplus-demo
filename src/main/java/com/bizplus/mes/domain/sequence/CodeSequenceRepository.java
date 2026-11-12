package com.bizplus.mes.domain.sequence;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CodeSequenceRepository extends JpaRepository<CodeSequence, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT s
            FROM CodeSequence s
            WHERE s.sequenceKey = :sequenceKey
            """)
    Optional<CodeSequence> findForUpdate(@Param("sequenceKey") String sequenceKey);
}
