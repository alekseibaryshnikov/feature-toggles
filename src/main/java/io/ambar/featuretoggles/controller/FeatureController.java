package io.ambar.featuretoggles.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.ambar.featuretoggles.dto.request.GetFeatureListRequest;
import io.ambar.featuretoggles.dto.request.GetFeatureListRequest.FeatureRequestData;
import io.ambar.featuretoggles.dto.request.GetFeatureListRequest.FeatureRequestType;
import io.ambar.featuretoggles.dto.request.PutFeatureRequest;
import io.ambar.featuretoggles.dto.response.FeatureListResponse;
import io.ambar.featuretoggles.dto.response.FeatureResponse;
import io.ambar.featuretoggles.repository.FeatureTogglesRepository;
import io.ambar.featuretoggles.repository.entity.FeatureToggle;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FeatureController {
    private final FeatureTogglesRepository featureToggleRepository;

    @Autowired
    public FeatureController(FeatureTogglesRepository featureToggleRepository) {
        this.featureToggleRepository = featureToggleRepository;
    }

    @PostMapping(path = "feature")
    public FeatureListResponse postFeatureList(@RequestBody GetFeatureListRequest featureRequest) {
        FeatureListResponse response = new FeatureListResponse();
        FeatureRequestData featureRequestData = featureRequest.getFeatureRequest();
        List<String> featureNames = Optional.ofNullable(featureRequestData.getFeatures())
                .orElseGet(Collections::emptyList).stream().map(FeatureRequestType::getName)
                .collect(Collectors.toList());
        List<FeatureToggle> features = featureToggleRepository.findByTechnicalNameIn(featureNames);

        response.setFeatures(Optional.ofNullable(features).orElseGet(Collections::emptyList).stream()
                .filter(f -> !featureRequest.getFeatureRequest().isArchive())
                .map(featureToggle -> new FeatureResponse(featureToggle, featureRequest)).collect(Collectors.toList()));

        return response;
    }

    @GetMapping(path = "feature/names")
    public List<String> getFeaturesNames() {
        return StreamSupport.stream(featureToggleRepository.findAll().spliterator(), true)
                .filter(f -> !f.isArchived())
                .map(FeatureToggle::getTechnicalName).collect(Collectors.toList());
    }

    @GetMapping(path = "feature")
    public List<FeatureToggle> getFeatureList() {
        return StreamSupport.stream(featureToggleRepository.findAll().spliterator(), true).collect(Collectors.toList());
    }

    @PutMapping(path = "feature")
    public FeatureToggle createNewFeature() {
        return featureToggleRepository.save(new FeatureToggle());
    }

    @PatchMapping(path = "feature")
    public void insertOrUpdate(@RequestBody PutFeatureRequest feature) {
        if (feature.getFeatureId() == null) {
            featureToggleRepository.save(new FeatureToggle().convert(feature));
            return;
        }

        Optional<FeatureToggle> featureFromDb = featureToggleRepository.findById(feature.getFeatureId());

        if (featureFromDb.isPresent()) {
            featureToggleRepository.save(featureFromDb.get().convert(feature));
        } else {
            featureToggleRepository.save(new FeatureToggle().convert(feature));
        }
    }

    @DeleteMapping(path = "feature")
    public void deleteFeature(@RequestParam Long id) {
        featureToggleRepository.deleteById(id);
    }
}