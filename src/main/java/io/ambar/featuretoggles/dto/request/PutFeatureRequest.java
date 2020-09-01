package io.ambar.featuretoggles.dto.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class PutFeatureRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long featureId;
    private String displayName;
    private String technicalName;
    private LocalDateTime expiresOn;
    private String description;
    private boolean inverted;
    private boolean archived;
    private List<String> customers;
}
