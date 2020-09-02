package io.ambar.featuretoggles.repository.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ambar.featuretoggles.dto.request.PutFeatureRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "customers")
@ToString(exclude = "customers")
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

    @JsonIgnore
    @ManyToMany(mappedBy = "featureToggles")
    private Set<Customer> customers = new HashSet<>();

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