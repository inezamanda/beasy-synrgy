package com.synrgybootcamp.project.util;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class Pagination {
    long total;
    int perPage;
    int currentPage;
    int totalPage;

    public Pagination(Page pageable) {
        this.total = pageable.getTotalElements();
        this.perPage = pageable.getPageable().getPageSize();
        this.currentPage = pageable.getPageable().getPageNumber();
        this.totalPage = pageable.getTotalPages();
    }
}

