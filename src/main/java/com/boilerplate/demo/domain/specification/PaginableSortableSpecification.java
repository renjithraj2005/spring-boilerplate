package com.boilerplate.demo.domain.specification;

import com.boilerplate.demo.domain.model.base.BaseEntity;
import com.boilerplate.demo.model.common.PaginableSortableFilter;
import com.boilerplate.demo.model.common.SortedDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public abstract class PaginableSortableSpecification<T extends BaseEntity, E extends PaginableSortableFilter>
        implements Specification<T> {

    private static final long serialVersionUID = -1201479310427084718L;

    protected abstract E getFilter();

    public PageRequest buildPageRequest() {
        E filter = getFilter();
        Sort sortBy = Sort.by(filter.getSortedByOrDefault());


        if (filter.getSortedDirectionOrDefault() == SortedDirection.ASC) {
            sortBy = sortBy.ascending();
        } else {
            sortBy = sortBy.descending();
        }
        return PageRequest.of(filter.getPageNrOrDefault(), filter.getPageSizeOrDefault(), sortBy);
    }
}