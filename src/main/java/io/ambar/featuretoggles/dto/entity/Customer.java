package io.ambar.featuretoggles.dto.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Customer {
    @Id @GeneratedValue
    private Long customerBaseId;
    private String customerId;

    @ManyToOne
    @JoinColumn(name = "feature_id", nullable = false)
    private FeatureToggle featureToggle;
}