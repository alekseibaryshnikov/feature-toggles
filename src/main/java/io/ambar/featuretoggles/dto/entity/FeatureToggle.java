package io.ambar.featuretoggles.dto.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class FeatureToggle {
    @Id 
    @GeneratedValue
    private Long featureId;
    private String displayName;
    private String technicalName;
    private LocalDateTime expiresOn;
    private String description;
    private boolean inverted;

    @ManyToMany
    @JoinTable(
        name = "feature_toggle__customer",
        joinColumns = @JoinColumn(name = "feature_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers;
}