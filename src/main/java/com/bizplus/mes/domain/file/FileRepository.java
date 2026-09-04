package com.bizplus.mes.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long>, FileQueryRepository {

    Optional<File> findByIdAndDeletedAtIsNull(Long id);
}
