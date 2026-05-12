package com.noutch.warframe_crafting_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noutch.warframe_crafting_api.model.Blueprint;
import com.noutch.warframe_crafting_api.model.BlueprintType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface BlueprintRepository extends JpaRepository<Blueprint, Long> {
    // custom query methods to filter blueprints by required mastery rank and build time
    Optional<Blueprint> findByName(String name);

    // using pages instead of lists, so that we never load everything at once for the sake of performance
    Page<Blueprint> findByRequiredLvlLessThanEqual(int requiredLvl, Pageable pageable);
    Page<Blueprint> findByBuildTimeLessThanEqual(int buildTime, Pageable pageable);
    Page<Blueprint> findByRequiredLvlLessThanEqualAndBuildTimeLessThanEqual(int requiredLvl, int buildTime, Pageable pageable);
    Page<Blueprint> findByType(BlueprintType type, Pageable pageable);
    Page<Blueprint> findByTypeAndRequiredLvlLessThanEqualAndBuildTimeLessThanEqual(BlueprintType type, int requiredLvl, int buildTime, Pageable pageable);
    Page<Blueprint> findByTypeAndRequiredLvlLessThanEqual(BlueprintType type, int requiredLvl, Pageable pageable);
    Page<Blueprint> findByTypeAndBuildTimeLessThanEqual(BlueprintType type, int buildTime, Pageable pageable);

}
