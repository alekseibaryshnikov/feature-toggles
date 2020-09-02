package io.ambar.featuretoggles.repository.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Entity
@Data
@EqualsAndHashCode(exclude = "featureToggles")
@ToString(exclude = "featureToggles")
public class Customer {
    @Id
    @GeneratedValue
    private Long customerId;
    private String customerName;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "customer__feature_toggle",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private Set<FeatureToggle> featureToggles = new LinkedHashSet<>();

}