package com.bizplus.mes.domain.code.group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, Long> {

    Optional<CodeGroup> findByGroupKey(CodeGroupKey groupKey);
}
