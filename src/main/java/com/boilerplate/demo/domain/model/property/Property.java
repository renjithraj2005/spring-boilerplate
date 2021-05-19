package com.boilerplate.demo.domain.model.property;

import com.boilerplate.demo.domain.model.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "property")
public class Property extends AuditableEntity {

    @Column(name = "property_name")
    private String propertyName;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "is_approved")
    private Boolean is_approved = false;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getIs_approved() {
        return is_approved;
    }

    public void setIs_approved(Boolean is_approved) {
        this.is_approved = is_approved;
    }
}
