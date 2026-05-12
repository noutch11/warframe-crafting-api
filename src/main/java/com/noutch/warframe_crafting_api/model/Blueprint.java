package com.noutch.warframe_crafting_api.model;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * The blueprint class will be used to represent all "blueprints" in the game. 
 * This includes weapons, warframes, companions, and more. 
 * They are made up of components
 */
@Entity
public class Blueprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name cannot be blank.")
    @Size(min = 3, message = "The name must be at least 3 characters.")
    private String name;
    @NotNull(message = "The blueprint must have a type (Warframe, Weapon, Companion).")
    private BlueprintType type; // type of blueprint (weapon, warframe, companion)

    @Min(value = 0, message = "The required mastery rank cannot be negative.")
    @Max(value = 30, message = "The max rank in the game is 30.")
    private int requiredLvl; // required mastery rank to craft the blueprint

    @Min(value = 1, message = "The build time must be at least 1 hour.")
    private int buildTime; // time required to build the blueprint in hours

    @NotNull(message = "The blueprint must have a list of required components.")
    // component is not an entity, so an element collection will store the list of components as a separate table in the database
    @ElementCollection
    private List<Component> components; // list of components required to craft the blueprint

    // description will be optional, so only include it in the JSON response if it is not empty
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;

    public Blueprint() {
        // default constructor for JPA
    }

    public Blueprint(String name, BlueprintType type, int requiredLvl, int buildTime, List<Component> components, String description) {
        this.name = name;
        this.type = type;
        this.requiredLvl = requiredLvl;
        this.buildTime = buildTime;
        this.components = components;
        this.description = description;
    }

    // getters and setters
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BlueprintType getType() {
        return type;
    }

    public void setType(BlueprintType type) {
        this.type = type;
    }

    public int getRequiredLvl() {
        return requiredLvl;
    }

    public void setRequiredLvl(int requiredLvl) {
        this.requiredLvl = requiredLvl;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(int buildTime) {
        this.buildTime = buildTime;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    // can be empty, so return an optional
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
