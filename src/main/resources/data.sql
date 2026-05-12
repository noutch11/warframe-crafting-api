-- AI generated SQL seed data so that the API can be tested with real data

-- ==========================================================
-- WARFRAME BLUEPRINTS (Type 0)
-- ==========================================================
INSERT INTO blueprint (name, type, required_lvl, build_time, description) 
VALUES ('Excalibur', 0, 0, 72, 'A master of gun and blade, Excalibur is a perfect balance of mobility and offense.');

INSERT INTO blueprint (name, type, required_lvl, build_time, description) 
VALUES ('Volt', 0, 0, 72, 'Volt can create and harness electrical elements. This is a high-damage Warframe perfect for players who love speed.');

INSERT INTO blueprint (name, type, required_lvl, build_time, description) 
VALUES ('Rhino', 0, 2, 72, 'Rhino is the heaviest Warframe, combining offensive and defensive capabilities.');

-- Components for Warframes
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Excalibur'), 'Excalibur Chassis');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Excalibur'), 'Excalibur Neuroptics');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Excalibur'), 'Excalibur Systems');

INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Volt'), 'Volt Chassis');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Volt'), 'Volt Neuroptics');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Volt'), 'Volt Systems');

INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Rhino'), 'Rhino Chassis');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Rhino'), 'Rhino Neuroptics');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Rhino'), 'Rhino Systems');


-- ==========================================================
-- WEAPON BLUEPRINTS (Type 1)
-- ==========================================================
INSERT INTO blueprint (name, type, required_lvl, build_time, description) 
VALUES ('Boltor', 1, 2, 12, 'A primary weapon that fires kinetic bolts capable of pinning enemies to walls.');

INSERT INTO blueprint (name, type, required_lvl, build_time, description) 
VALUES ('Paris', 1, 0, 12, 'A classic Tenno bow. Silent and deadly.');

INSERT INTO blueprint (name, type, required_lvl, build_time, description) 
VALUES ('Orthos', 1, 2, 12, 'A double-bladed polearm with a massive reach.');

-- Components for Weapons
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Boltor'), 'Boltor Barrel');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Boltor'), 'Boltor Receiver');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Boltor'), 'Boltor Stock');

INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Paris'), 'Paris Grip');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Paris'), 'Paris Upper Limb');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Paris'), 'Paris Lower Limb');

INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Orthos'), 'Orthos Handle');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Orthos'), 'Orthos Blade');


-- ==========================================================
-- COMPANION BLUEPRINTS (Type 2)
-- ==========================================================
INSERT INTO blueprint (name, type, required_lvl, build_time, description) 
VALUES ('Taxon', 2, 0, 24, 'A sentinel designed to assist new Tenno with shield restoration.');

INSERT INTO blueprint (name, type, required_lvl, build_time, description) 
VALUES ('Carrier', 2, 0, 24, 'A support sentinel that automates ammo collection.');

-- Components for Companions
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Taxon'), 'Taxon Cerebrum');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Taxon'), 'Taxon Chassis');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Taxon'), 'Taxon Systems');

INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Carrier'), 'Carrier Cerebrum');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Carrier'), 'Carrier Chassis');
INSERT INTO blueprint_components (blueprint_id, name) VALUES ((SELECT id FROM blueprint WHERE name = 'Carrier'), 'Carrier Systems');