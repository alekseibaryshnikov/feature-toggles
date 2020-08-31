package io.ambar.featuretoggles.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ambar.featuretoggles.repository.entity.FeatureToggle;
import io.ambar.featuretoggles.dto.request.GetFeatureListRequest;
import io.ambar.featuretoggles.dto.request.PutFeatureRequest;
import io.ambar.featuretoggles.dto.request.GetFeatureListRequest.FeatureRequestData;
import io.ambar.featuretoggles.dto.request.GetFeatureListRequest.FeatureRequestType;
import io.ambar.featuretoggles.dto.response.FeatureListResponse;
import io.ambar.featuretoggles.dto.response.FeatureResponse;
import io.ambar.featuretoggles.repository.FeatureTogglesRepository;

@RestController
public class FeatureController {
    @Autowired
    private FeatureTogglesRepository featureToggleRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "features")
    public FeatureListResponse getFeatureList(@RequestBody GetFeatureListRequest featureRequest) {
        FeatureListResponse response = new FeatureListResponse();
        FeatureRequestData featureRequestData = featureRequest.getFeatureRequest();
        List<String> featureNames = 
            Optional.ofNullable(featureRequestData.getFeatures()).orElseGet(Collections::emptyList).stream()
                .map(FeatureRequestType::getName)
                .collect(Collectors.toList());
        List<FeatureToggle> features = featureToggleRepository.findByTechnicalNameIn(featureNames);
        
        response.setFeatures(Optional.ofNullable(features).orElseGet(Collections::emptyList).stream()
                .map(featureToggle -> new FeatureResponse(featureToggle, featureRequest))
                .collect(Collectors.toList()));
        
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