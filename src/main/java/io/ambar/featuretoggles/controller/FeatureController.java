package io.ambar.featuretoggles.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ambar.featuretoggles.dto.request.FeatureRequestBody;
import io.ambar.featuretoggles.dto.response.FeatureListResponse;
import io.ambar.featuretoggles.dto.response.FeatureResponse;
import io.ambar.featuretoggles.repository.FeatureTogglesRepository;

@RestController
public class FeatureController {
    @Autowired
    private FeatureTogglesRepository featureToggleRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "features")
    public FeatureListResponse getFeatureList(@RequestBody FeatureRequestBody featureRequest) {
        List<FeatureResponse> featureResponseList = featureRequest.getFeatureRequest().getFeatures().stream()
                .map(feature -> featureToggleRepository.findByTechnicalName(feature.getName()))
                .map(featureToggle -> new FeatureResponse(featureToggle, featureRequest)).collect(Collectors.toList());

        return new FeatureListResponse(featureResponseList);
    }
}