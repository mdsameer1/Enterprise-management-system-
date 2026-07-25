package com.enterprise.dto;

import lombok.*;

/**
 * Pagination Response DTO
 * Generic pagination wrapper for list responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationDTO<T> {
    private java.util.List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
