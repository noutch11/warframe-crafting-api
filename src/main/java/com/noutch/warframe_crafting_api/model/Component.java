package com.noutch.warframe_crafting_api;

import jakarta.persistence.Embeddable;

@Embeddable
/** 
 * Components are the individual parts required to craft a blueprint
 * rather than its own entity, it is an embeddable record that will always be used as a part of the blueprint entity
 */
public record Component (String name) {}
