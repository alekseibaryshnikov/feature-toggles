package io.ambar.featuretoggles.controller;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ambar.featuretoggles.dto.entity.FeatureToggle;
import io.ambar.featuretoggles.dto.request.GetFeatureListRequest;
import io.ambar.featuretoggles.dto.request.PutFeatureRequest;
import io.ambar.featuretoggles.dto.response.FeatureListResponse;
import io.ambar.featuretoggles.dto.response.FeatureResponse;
import io.ambar.featuretoggles.repository.FeatureTogglesRepository;

@RestController
public class FeatureController {
    @Autowired
    private FeatureTogglesRepository featureToggleRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "features")
    public FeatureListResponse getFeatureList(@RequestBody GetFeatureListRequest featureRequest) {
        FeatureListResponse response = new FeatureListResponse();

        if (featureRequest.getFeatureRequest() != null) {
            response.setFeatures(Optional.ofNullable(featureRequest.getFeatureRequest().getFeatures())
                    .orElseGet(Collections::emptyList).stream()
                    .map(feature -> featureToggleRepository.findByTechnicalName(feature.getName()))
                    .map(featureToggle -> new FeatureResponse(featureToggle, featureRequest))
                    .collect(Collectors.toList()));
        }

        return response;
    }

    @PutMapping(path = "features")
    public FeatureToggle addFeature(@RequestBody PutFeatureRequest feature) {
        return featureToggleRepository.save(new FeatureToggle(feature));
    }

    @PatchMapping(path = "features")
    public FeatureToggle updateFeature(@RequestBody PutFeatureRequest feature) {
        return featureToggleRepository.save(new FeatureToggle(feature));
    }

    @DeleteMapping(path = "features")
    public void deleteFeature(@RequestBody Long id) {
        featureToggleRepository.deleteById(id);
    }
}