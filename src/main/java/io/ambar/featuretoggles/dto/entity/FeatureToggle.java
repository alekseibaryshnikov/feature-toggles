package io.ambar.featuretoggles.dto.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @OneToMany(mappedBy = "featureToggle")
    private List<Customer> customers;
}