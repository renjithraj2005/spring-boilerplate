package com.boilerplate.demo.model.common;

import org.apache.commons.lang3.StringUtils;

public abstract class PaginableSortableFilter<T extends PaginableSortableFilter> {
    private static final Integer DEFAULT_PAGE_NR = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 500;

    /**
     * the page number, first page is 0 (ZERO), default is zero.
     */
    private Integer pageNr = DEFAULT_PAGE_NR;
    /**
     * number of items per page, default is 500.
     */
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    /**
     * the field name to sort. list of fields to be defined per API, default per API.
     */
    private String sortedBy;

    /**
     * ASC (for ascendingly) or DESC (for descendingly), default direction is DESC
     * because most of the time we sort by date.
     */
    private SortedDirection sortedDirection = SortedDirection.DESC;

    // ABSTRACT METHODS:
    protected abstract String getDefaultSortedBy();
    protected abstract SortedDirection getDefaultSortedDirection();

    public Integer getPageNrOrDefault() {
        return getPageNr() != null ? getPageNr() : DEFAULT_PAGE_NR;
    }

    public Integer getPageSizeOrDefault() {
        return getPageSize() != null ? getPageSize() : DEFAULT_PAGE_SIZE;
    }

    public String getSortedByOrDefault() {
        return StringUtils.isNotBlank(getSortedBy()) ? getSortedBy() : getDefaultSortedBy();
    }

    public SortedDirection getSortedDirectionOrDefault() {
        return getSortedDirection() != null ? getSortedDirection() : getDefaultSortedDirection();
    }

    public Integer getPageNr() {
        return pageNr;
    }

    public T setPageNr(Integer pageNr) {
        this.pageNr = pageNr;
        return (T) this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public T setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return (T) this;
    }

    public String getSortedBy() {
        return sortedBy;
    }

    public T setSortedBy(String sortedBy) {
        this.sortedBy = sortedBy;
        return (T) this;
    }

    public SortedDirection getSortedDirection() {
        return sortedDirection;
    }

    public T setSortedDirection(SortedDirection sortedDirection) {
        this.sortedDirection = sortedDirection;
        return (T) this;
    }
}
