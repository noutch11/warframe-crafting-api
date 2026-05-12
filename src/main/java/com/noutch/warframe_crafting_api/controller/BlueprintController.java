package com.noutch.warframe_crafting_api.controller;

import java.util.Optional;

import com.noutch.warframe_crafting_api.exception.BlueprintNotFoundException;
import com.noutch.warframe_crafting_api.model.Blueprint;
import com.noutch.warframe_crafting_api.model.BlueprintType;
import com.noutch.warframe_crafting_api.repository.BlueprintRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
// common url prefix for all endpoints in the blueprint controller
// all mappings are a ResponseEntity to allow precise response status codes
@RequestMapping("/blueprints")
public class BlueprintController {
    @Autowired
    private BlueprintRepository blueprintRepo; // extends JpaRepository interface for its CRUD operations

    @GetMapping
    // return all blueprints in the database
    public ResponseEntity<Page<Blueprint>> getAllBlueprints(@RequestParam Optional<Integer> requiredLvl,
        @RequestParam Optional<Integer> buildTime, 
        // default pagination and sorting settings to prevent loading everything at once if user is lazy
        @PageableDefault(page = 0, size = 20, sort = {"type", "name"}) Pageable pageable) {

        // optional query parameters to filter blueprints by required mastery rank and build time
        if (requiredLvl.isPresent() && buildTime.isPresent()) {
            return ResponseEntity.ok().body(blueprintRepo
                .findByRequiredLvlLessThanEqualAndBuildTimeLessThanEqual(requiredLvl.get(), buildTime.get(), pageable));
        } else if (requiredLvl.isPresent()) {
            return ResponseEntity.ok().body(blueprintRepo.findByRequiredLvlLessThanEqual(requiredLvl.get(), pageable));
        } else if (buildTime.isPresent()) {
            return ResponseEntity.ok().body(blueprintRepo.findByBuildTimeLessThanEqual(buildTime.get(), pageable));
        }

        return ResponseEntity.ok().body(blueprintRepo.findAll(pageable));
    }
    
    @PostMapping
    // add a new blueprint to the database, validating the request body before saving it
    public ResponseEntity<Blueprint> addBlueprint(@Valid @RequestBody Blueprint blueprint) {
        blueprintRepo.save(blueprint);
        // return 201 (created) if the blueprint is successfully added to the database
        return ResponseEntity.status(HttpStatus.CREATED).body(blueprint); 
    }

    @GetMapping("/type/{type}")
    // return all blueprints of a specific type (weapon, warframe, companion)
    public ResponseEntity<Page<Blueprint>> getBlueprintsByType(@PathVariable BlueprintType type, 
        @RequestParam Optional<Integer> requiredLvl, 
        @RequestParam Optional<Integer> buildTime,
        // default pagination and sorting settings to prevent loading everything at once if user is lazy
        @PageableDefault(page = 0, size = 20, sort = {"name"}) Pageable pageable) {

        // option to filter blueprints of the same type by required mastery rank and build time
        if (requiredLvl.isPresent() && buildTime.isPresent()) {
            return ResponseEntity.ok().body(blueprintRepo
                .findByTypeAndRequiredLvlLessThanEqualAndBuildTimeLessThanEqual(type, requiredLvl.get(), buildTime.get(), pageable));
        } else if (requiredLvl.isPresent()) {
            return ResponseEntity.ok().body(blueprintRepo.findByTypeAndRequiredLvlLessThanEqual(type, requiredLvl.get(), pageable));
        } else if (buildTime.isPresent()) {
            return ResponseEntity.ok().body(blueprintRepo.findByTypeAndBuildTimeLessThanEqual(type, buildTime.get(), pageable));
        }

        return ResponseEntity.ok().body(blueprintRepo.findByType(type, pageable));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Blueprint> getBlueprintByName(@PathVariable String name) {
        Blueprint blueprint = blueprintRepo.findByName(name).orElseThrow(
            // throw custom exception if the blueprint name is not found
            () -> new BlueprintNotFoundException("A blueprint named " + name + " does not exist in the database"));
        return ResponseEntity.ok().body(blueprint);
    }
    

    @GetMapping("/{id}")
    // get a blueprint by its id
    public ResponseEntity<Blueprint> getBlueprintById(@PathVariable Long id) {
        Blueprint blueprint = blueprintRepo.findById(id).orElseThrow(
            // will return a custom exception if the blueprint is not found in the database
            () -> new BlueprintNotFoundException(id));
        // will return a 200 ok if the blueprint is found
        return ResponseEntity.ok().body(blueprint);
    }

    @PutMapping("/{id}")
    // making sure to validate the updated blueprint before saving it to the database
    public ResponseEntity<Blueprint> updateBlueprint(@PathVariable Long id, @Valid @RequestBody Blueprint updatedBlueprint) {
        Blueprint currentBlueprint = blueprintRepo.findById(id).orElseThrow(
            // will return a custom exception if the blueprint is not found in the database
            () -> new BlueprintNotFoundException(id));
        
        // update the corresponding fields
        currentBlueprint.setName(updatedBlueprint.getName());
        currentBlueprint.setType(updatedBlueprint.getType());
        currentBlueprint.setRequiredLvl(updatedBlueprint.getRequiredLvl());
        currentBlueprint.setBuildTime(updatedBlueprint.getBuildTime());
        currentBlueprint.setComponents(updatedBlueprint.getComponents());
        currentBlueprint.setDescription(updatedBlueprint.getDescription().orElse(null));

        blueprintRepo.save(currentBlueprint); // save the updated blueprint to the database
        return ResponseEntity.ok().body(currentBlueprint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlueprint(@PathVariable Long id) {
        Blueprint currentBlueprint = blueprintRepo.findById(id).orElseThrow(
            // return a custom exception if the blueprint is not found in the database
            () -> new BlueprintNotFoundException(id));

        blueprintRepo.deleteById(currentBlueprint.getId()); // delete the blueprint from the database

        // return a 200 status stating that the blueprint is successfully deleted
        return ResponseEntity.ok().body("The blueprint was successfully deleted."); 
    }

    // controller-level exception handling since we only have one controller
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        // return a 400 bad request if the request body is invalid
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(BlueprintNotFoundException.class)
    public ResponseEntity<String> handleBlueprintNotFoundException(BlueprintNotFoundException e) {
        // return 404 not found if the requested blueprint is not found in the database
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
