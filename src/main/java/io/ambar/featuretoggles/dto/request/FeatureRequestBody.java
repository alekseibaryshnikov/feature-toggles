package io.ambar.featuretoggles.dto.request;

import java.io.Serializable;
import java.util.List;

import io.ambar.featuretoggles.dto.response.FeatureResponse;
import lombok.Data;

@Data
public class FeatureRequestBody implements Serializable {
    private static final long serialVersionUID = 1L;
    private FeatureRequestData featureRequest;

    @Data
    public static class FeatureRequestData implements Serializable {
        private static final long serialVersionUID = 1L;
        private String customerId;
        private List<FeatureResponse> features;
    }
}