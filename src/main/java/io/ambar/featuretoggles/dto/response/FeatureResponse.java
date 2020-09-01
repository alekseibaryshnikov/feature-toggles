package io.ambar.featuretoggles.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.ambar.featuretoggles.repository.entity.FeatureToggle;
import io.ambar.featuretoggles.dto.request.GetFeatureListRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeatureResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private boolean active;
    private boolean inverted;
    private boolean expired;
    private LocalDateTime expiresOn;
    private String displayName;
    private String description;

    public FeatureResponse(FeatureToggle featureToggle, GetFeatureListRequest featureRequest) {
        boolean isActive = featureToggle.getCustomers().stream().anyMatch(
            f -> f.getCustomerId().equals(featureRequest.getFeatureRequest().getCustomerId()));
            
        name = featureToggle.getTechnicalName();
        active = isActive;
        expired = LocalDateTime.now().isAfter(featureToggle.getExpiresOn());
        inverted = !featureToggle.isInverted();
        expiresOn = featureToggle.getExpiresOn();
        displayName = featureToggle.getDisplayName();
        description = featureToggle.getDescription();
    }
}