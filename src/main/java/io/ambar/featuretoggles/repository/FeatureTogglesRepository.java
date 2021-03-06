package io.ambar.featuretoggles.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.ambar.featuretoggles.repository.entity.FeatureToggle;

public interface FeatureTogglesRepository extends CrudRepository<FeatureToggle, Long>{

    List<FeatureToggle> findByTechnicalNameIn(List<String> names);

}