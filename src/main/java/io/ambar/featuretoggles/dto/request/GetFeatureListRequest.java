package io.ambar.featuretoggles.dto.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class GetFeatureListRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private FeatureRequestData featureRequest;

    @Data
    public static class FeatureRequestData implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private Long customerId;
        private List<FeatureRequestType> features;
        private boolean isArchive;
    }

    @Data
    public static class FeatureRequestType implements Serializable {
        private static final long serialVersionUID = 1L;

        private String name;
    }
}