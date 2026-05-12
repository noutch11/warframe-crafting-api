package com.noutch.warframe_crafting_api.exception;

// custom exception to be thrown when a blueprint is not found in the database
public class BlueprintNotFoundException extends RuntimeException {
    public BlueprintNotFoundException(String message) {
        super(message);
    }

    // a constructor to include the blueprint id for more detailed error logging
    public BlueprintNotFoundException(Long id) {
        super("Blueprint # " + id + " was not found in the database");
    }

}
