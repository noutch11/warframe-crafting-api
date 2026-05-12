package com.noutch.warframe_crafting_api;

import java.util.List;
import java.util.Optional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The blueprint class will be used to represent all "blueprints" in the game. 
 * This includes weapons, warframes, companions, and more. 
 * They are made up of components
 */
public class Blueprint {
    @NotBlank(message = "The name cannot be blank.")
    @Size(min = 3, message = "The name must be at least 3 characters.")
    String name;
    @NotBlank(message = "The type cannot be blank.")
    BlueprintType type; // type of blueprint (weapon, warframe, companion)
    int requiredLvl; // required mastery rank to craft the blueprint
    int buildTime; // time required to build the blueprint in hours
    List<Component> components; // list of components required to craft the blueprint
    Optional<String> description;

    public Blueprint(String name, BlueprintType type, int requiredLvl, int buildTime, List<Component> components, Optional<String> description) {
        this.name = name;
        this.type = type;
        this.requiredLvl = requiredLvl;
        this.buildTime = buildTime;
        this.components = components;
        this.description = description;
    }

    /* getters 
        setters are not needed since the blueprint will be immutable after creation
    */
    public String getName() {
        return name;
    }

    public String getType() {
        return type.name();
    }

    public int getRequiredLvl() {
        return requiredLvl;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public List<Component> getComponents() {
        return components;
    }

    public Optional<String> getDescription() {
        return description;
    }
}
