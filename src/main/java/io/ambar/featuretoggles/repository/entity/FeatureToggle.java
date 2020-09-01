package io.ambar.featuretoggles.repository.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import io.ambar.featuretoggles.dto.request.PutFeatureRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class FeatureToggle {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long featureId;
    private String displayName;
    private String technicalName;
    private LocalDateTime expiresOn;
    private String description;
    private boolean inverted;
    private boolean archived;

    @ManyToMany
    @JoinTable(
        name = "feature_toggle__customer",
        joinColumns = @JoinColumn(name = "feature_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers;

    public FeatureToggle convert(PutFeatureRequest putFeatureRequest) {
        displayName = putFeatureRequest.getDisplayName();
        technicalName = putFeatureRequest.getTechnicalName();
        expiresOn = putFeatureRequest.getExpiresOn();
        description = putFeatureRequest.getDescription();
        inverted = putFeatureRequest.isInverted();
        archived = putFeatureRequest.isArchived();
        return this;
    }
}