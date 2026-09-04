package com.bizplus.mes.domain.item.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemFileRepository extends JpaRepository<ItemFile, Long>, ItemFileQueryRepository {
}
