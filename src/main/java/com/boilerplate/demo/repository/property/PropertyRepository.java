package com.boilerplate.demo.repository.property;

import com.boilerplate.demo.domain.model.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
