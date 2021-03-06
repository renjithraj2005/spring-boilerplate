package com.boilerplate.demo.domain.model.base;

import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Comparable<BaseEntity>, Serializable {

    private static final long serialVersionUID = 1L;

    @Access(AccessType.PROPERTY)
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    @Override
    public int compareTo(final BaseEntity obj) {
        return ObjectUtils.compare(this.getId(), obj.getId());
    }

    @Override
    public boolean equals(final Object otherInstance) {
        if (this == otherInstance) {
            return true;
        }
        if (otherInstance == null || this.getClass() != otherInstance.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) otherInstance;
        if (this.id == null)  {
            return false;
        }
        return this.id.equals(other.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
