package io.ambar.featuretoggles.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureListResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<FeatureResponse> features;
}