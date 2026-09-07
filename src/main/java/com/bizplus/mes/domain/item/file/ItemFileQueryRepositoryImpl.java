package com.bizplus.mes.domain.item.file;

import com.bizplus.mes.domain.file.FileType;
import com.bizplus.mes.domain.item.file.dto.ItemFileDto;
import com.bizplus.mes.domain.item.file.dto.QItemFileDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.domain.file.QFile.file;
import static com.bizplus.mes.domain.item.file.QItemFile.itemFile;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemFileQueryRepositoryImpl implements ItemFileQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<ItemFileDto> findItemFiles(Long itemId, FileType fileType) {
        return query
                .select(new QItemFileDto(
                        itemFile.id,
                        itemFile.type,
                        itemFile.sortOrder,
                        file.id,
                        file.originalName,
                        file.extension
                ))
                .from(itemFile)
                .innerJoin(file).on(itemFile.file.id.eq(file.id))
                .where(eq(itemFile.item.id, itemId))
                .orderBy(itemFile.sortOrder.asc())
                .fetch();
    }

    @Override
    public Optional<ItemFileDto> findItemFile(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QItemFileDto(
                                itemFile.id,
                                itemFile.type,
                                itemFile.sortOrder,
                                file.id,
                                file.originalName,
                                file.extension
                        ))
                        .from(itemFile)
                        .innerJoin(file).on(itemFile.file.id.eq(file.id))
                        .where(eq(itemFile.id, id))
                        .fetchOne()
        );
    }
}
