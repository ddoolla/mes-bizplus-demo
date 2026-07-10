package com.bizplus.mes.common.pagination;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class Pagination {

    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    private final int blockSize;
    private final int blockStartPage;
    private final int blockEndPage;

    private final boolean hasFirst;
    private final boolean hasPrevious;
    private final boolean hasNext;
    private final boolean hasLast;

    private Pagination(Page<?> page, int blockSize) {

        this.page = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();

        this.blockSize = blockSize;
        this.blockStartPage = ((this.page - 1) / blockSize) * blockSize + 1;
        this.blockEndPage = Math.min((blockStartPage + blockSize - 1), totalPages);

        this.hasFirst = blockStartPage > 1;
        this.hasPrevious = blockStartPage > 1;
        this.hasNext = blockEndPage < totalPages; // 첫 번째 블록에서는 첫 페이지 이동 버튼 비활성
        this.hasLast = blockEndPage < totalPages; // 마지막 블록에서는 마지막 페이지 이동 버튼 비활성
    }

    public static Pagination of(Page<?> page) {
        return new Pagination(page, 10);
    }

    public static Pagination of(Page<?> page, int blockSize) {
        return new Pagination(page, blockSize);
    }
}
