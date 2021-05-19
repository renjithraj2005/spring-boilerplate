package com.boilerplate.demo.model.common;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaginableSortableResponse<T> implements Serializable {

    private Integer pageNr;
    private Integer pageSize;
    private String sortedBy;
    private SortedDirection sortedDirection;

    private Integer totalPages;
    private Long totalRecords;

    private List<T> items = new ArrayList<>();

    public PaginableSortableResponse(){}
    public PaginableSortableResponse(Page<T> page, PaginableSortableFilter<?> filter) {
        pageNr = page.getNumber();
        pageSize = page.getSize();
        sortedBy = filter.getSortedByOrDefault();
        sortedDirection = filter.getSortedDirectionOrDefault();
        totalPages = page.getTotalPages();
        totalRecords = page.getTotalElements();
        items = page.getContent();
    }

    public Integer getPageNr() {
        return pageNr;
    }

    public void setPageNr(Integer pageNr) {
        this.pageNr = pageNr;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(String sortedBy) {
        this.sortedBy = sortedBy;
    }

    public SortedDirection getSortedDirection() {
        return sortedDirection;
    }

    public void setSortedDirection(SortedDirection sortedDirection) {
        this.sortedDirection = sortedDirection;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
