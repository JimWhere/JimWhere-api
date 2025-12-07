package com.jimwhere.api.global.comman;

import java.util.List;
import org.springframework.data.domain.Page;


public record PageResponse<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean first,
    boolean last,
    boolean hasNext,
    boolean hasPrevious
) {

    public static <T> PageResponse<T> of(Page<T> page) {

        // 페이지가 비었거나 Null일 때 기본값 세팅
        if (page == null || page.getTotalElements() == 0) {
            return new PageResponse<>(
                    List.of(),  // content
                    0,          // page
                    20,          // size
                    0,          // totalElements
                    0,          // totalPages
                    true,       // first
                    true,       // last
                    false,      // hasNext
                    false       // hasPrevious
            );
        }

        // 정상 페이지 처리
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

}
