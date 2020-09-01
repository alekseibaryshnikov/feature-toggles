package io.ambar.featuretoggles.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

import io.ambar.featuretoggles.repository.entity.FeatureToggle;
import io.ambar.featuretoggles.dto.request.GetFeatureListRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeatureResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long featureId;
    private String name;
    private boolean active;
    private boolean inverted;
    private boolean expired;
    private LocalDateTime expiresOn;
    private String displayName;
    private String description;

    public FeatureResponse(FeatureToggle featureToggle, GetFeatureListRequest featureRequest) {
        boolean isActive = featureToggle.getCustomers().stream()
                .anyMatch(f -> f.getCustomerId().equals(featureRequest.getFeatureRequest().getCustomerId()));

        name = featureToggle.getTechnicalName();
        active = isActive;
        Optional.ofNullable(featureToggle.getExpiresOn()).ifPresent(e -> expired = LocalDateTime.now().isAfter(e));
        inverted = !featureToggle.isInverted();
        expiresOn = featureToggle.getExpiresOn();
        displayName = featureToggle.getDisplayName();
        description = featureToggle.getDescription();
        featureId = featureToggle.getFeatureId();
    }
}