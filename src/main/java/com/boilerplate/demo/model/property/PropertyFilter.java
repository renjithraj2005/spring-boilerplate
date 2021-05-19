package com.boilerplate.demo.model.property;

import com.boilerplate.demo.model.common.ListingType;
import com.boilerplate.demo.model.common.PaginableSortableFilter;
import com.boilerplate.demo.model.common.SortedDirection;

public class PropertyFilter extends PaginableSortableFilter<PropertyFilter> {
    private String query;
    private ListingType listingType;

    public PropertyFilter() {
    }

    @Override
    protected String getDefaultSortedBy() {
        return "createdDate";
    }

    @Override
    protected SortedDirection getDefaultSortedDirection() {
        return SortedDirection.DESC;
    }

    public String getQuery() {
        return query;
    }

    public PropertyFilter setQuery(String query) {
        this.query = query;
        return this;
    }

    public ListingType getListingType() {
        return listingType;
    }

    public PropertyFilter setListingType(ListingType listingType) {
        this.listingType = listingType;
        return this;
    }
}
