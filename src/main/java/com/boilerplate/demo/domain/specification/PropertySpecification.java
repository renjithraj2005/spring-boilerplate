package com.boilerplate.demo.domain.specification;

import com.boilerplate.demo.domain.model.property.Property;
import com.boilerplate.demo.model.common.ListingType;
import com.boilerplate.demo.model.property.PropertyFilter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PropertySpecification extends PaginableSortableSpecification<Property, PropertyFilter> {
    private static final long serialVersionUID = 4931354834218594246L;
    private PropertyFilter rateFilter;

    public PropertySpecification(final PropertyFilter rateFilter) {
        super();
        this.rateFilter = rateFilter;
    }

    @Override
    protected PropertyFilter getFilter() {
        return rateFilter;
    }

    @Override
    public Predicate toPredicate(final Root<Property> root, final CriteriaQuery<?> query, final CriteriaBuilder criteria) {
        final List<Predicate> filters = new ArrayList<>(6);


        if (StringUtils.isNotBlank(rateFilter.getQuery())) {
            String q = StringUtils.trimToEmpty(rateFilter.getQuery());

            String likeQuery = "%" + q + "%";
            filters.add(
                    criteria.or(
                            criteria.like(root.<String>get("propertyName"), likeQuery),
                            criteria.like(root.<String>get("description"), likeQuery)
                    )
            );
        }

        if(this.rateFilter.getListingType() != null){
            if(this.rateFilter.getListingType() == ListingType.APPROVED){
                filters.add(criteria.equal(root.get("isApproved"), true));
            }
            if(this.rateFilter.getListingType() == ListingType.PENDING){
                filters.add(criteria.equal(root.get("isApproved"), false));
            }

        }

        return filters.isEmpty() ? null : criteria.and(filters.toArray(new Predicate[filters.size()]));
    }
}
