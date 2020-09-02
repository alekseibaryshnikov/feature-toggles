package io.ambar.featuretoggles.dto.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class BindFeatureToCustomerRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long featureId;
    private Long customerId;
    private boolean active;
}