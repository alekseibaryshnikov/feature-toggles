package io.ambar.featuretoggles.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.ambar.featuretoggles.repository.entity.FeatureToggle;
import io.ambar.featuretoggles.dto.request.GetFeatureListRequest;
import io.ambar.featuretoggles.dto.request.PutFeatureRequest;
import io.ambar.featuretoggles.dto.request.GetFeatureListRequest.FeatureRequestData;
import io.ambar.featuretoggles.dto.request.GetFeatureListRequest.FeatureRequestType;
import io.ambar.featuretoggles.dto.response.FeatureListResponse;
import io.ambar.featuretoggles.dto.response.FeatureResponse;
import io.ambar.featuretoggles.repository.FeatureTogglesRepository;

import javax.swing.text.html.Option;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FeatureController {
    private final FeatureTogglesRepository featureToggleRepository;

    @Autowired
    public FeatureController(FeatureTogglesRepository featureToggleRepository) {
        this.featureToggleRepository = featureToggleRepository;
    }

    @PostMapping(path = "features")
    public FeatureListResponse postFeatureList(@RequestBody GetFeatureListRequest featureRequest) {
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

    @GetMapping(path = "features/names")
    public List<String> getFeaturesNames() {
        return StreamSupport.stream(featureToggleRepository.findAll().spliterator(), true)
            .map(FeatureToggle::getTechnicalName)
            .collect(Collectors.toList());
    }

    @GetMapping(path = "features")
    public List<FeatureToggle> postFeatureList() {
        return StreamSupport.stream(featureToggleRepository.findAll().spliterator(), true)
                .collect(Collectors.toList());
    }

    @PutMapping(path = "features")
    public FeatureToggle createNewFeature() {
        return featureToggleRepository.save(new FeatureToggle());
    }

    @PatchMapping(path = "features")
    public void insertOrUpdate(@RequestBody PutFeatureRequest feature) {
        Optional<FeatureToggle> featureToggle = featureToggleRepository.findById(feature.getFeatureId());

        if (featureToggle.isPresent()) {
            featureToggleRepository.save(featureToggle.get().convert(feature));
        } else {
            featureToggleRepository.save(new FeatureToggle().convert(feature));
        }
    }

    @DeleteMapping(path = "features")
    public void deleteFeature(@RequestParam Long id) {
        featureToggleRepository.deleteById(id);
    }
}