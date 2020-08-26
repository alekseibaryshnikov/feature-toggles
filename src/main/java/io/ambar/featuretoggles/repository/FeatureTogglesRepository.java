package io.ambar.featuretoggles.repository;

import org.springframework.data.repository.CrudRepository;

import io.ambar.featuretoggles.dto.entity.FeatureToggle;

public interface FeatureTogglesRepository extends CrudRepository<FeatureToggle, Long>{
    FeatureToggle findByTechnicalName(String technicalName);
}