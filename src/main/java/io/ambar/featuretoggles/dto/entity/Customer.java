package io.ambar.featuretoggles.dto.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class Customer {
    @Id @GeneratedValue
    private Long customerId;
    private String customerName;

    @ManyToMany
    private Set<FeatureToggle> featutreToggles;

    Customer(String customerName) {
        this.customerName = customerName;
    }
}