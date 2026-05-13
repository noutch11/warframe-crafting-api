# Project Overview

- This is a RESTful API that manages Warframe blueprint crafting requirements,
    including components and required player rank.
- Uses Spring Boot 4 (Java 25) with Gradle, Spring Data JPA, and H2 in-memory
    database.

# How to Run

- In CMD Prompt: ./gradlew bootRun
- In browser: [http://localhost:8080/blueprints](http://localhost:8080/blueprints)

# Project Structure

The main source files are split into respective folders for professional organizational
practices.

src/main/java/com/noutch/warframe_crafting_api/

├── controller/

│ └── BlueprintController.java

├── exception/

│ └── BlueprintNotFoundException.java

├── model/

│ ├── Blueprint.java

│ ├── BlueprintType.java

│ └── Component.java

├── repository/

│ └── BlueprintRepository.java

└── WarframeCraftingApiApplication.java

There is also data.sql in src/main/resources which is AI generated SQL seed data so that
the app can be tested with real data.


# Logistics

**Blueprint.java:** This is the single entity in the API.

- I utilize the **Id** and **GeneratedValue** annotations to auto-generate each blueprint’s
    unique ID. This works better than manually entering the IDs myself because I would
    have to keep track of how many blueprint entries already exist in order to enter the
    correct ID. This saved me a lot of boilerplate code.
- I use various validation constraint annotations to make sure all essential fields of
    the **Blueprint** object are present during construction ( **name, type, requiredLvl,**
    **buildTime, components** ).
- I used the **@ElementCollection** annotation on the **List** of components. This is
    because Component.java is not an entity, so a standard entity relationship was not
    an option, and H2 databases don’t work well with storing **List** objects into a single
    cell. **@ElementCollection** allows JPA to create a sub-table within each blueprint
    table entry containing its required components.
- Notably, I added logic to make the description field optional. The description is not
    an essential field. It’s helpful, but a blueprint can technically exist without one.
       o For the getter of the description field, I use the **Optional** class to return either
          the description (if present) or null.
       o I use an **@JsonInclude** annotation to only include this field in the JSON
          output if it is not null. This is ideal for cleaner readability, compared to seeing
          a null table entry.

**BlueprintType.java:** This is simply an enum for the three types of Blueprints: Warframe,
Weapon, or Companion.

- It was not necessary, but it was a more professional and explicit way of handling the
    blueprint types. Forcing the field to be of type **BlueprintType** makes it easier to
    throw an error for an illegal argument in the type field. If it was a string or integer, I’d
    have to write conditionals for input validation, which adds more boilerplate code.

**Component.java:** This is a public record class.

- Annotated with **@Embedded** so that it can be set as an **ElementCollection** in the
    **Blueprint** class.
- It’s not its own entity because it is only a value-object—all it holds is the name of the
    component. It only needs to exist within the blueprint that contains it and is not
    needed elsewhere in the application.

**BlueprintNotFoundException.java:** A custom exception class for nonexistent Blueprints


- Rather than throwing a standard 404 error, I wanted a custom message to let the
    user know that they requested an invalid blueprint.
- It has a regular constructor that takes a custom message to use as the error
    response. It also has a custom constructor that takes the query ID and sends a
    custom message stating that a blueprint with that ID does not exist in the database.

**BlueprintRepository.java:** An interface which handles all SQL query methods.

- Extends the **JpaRepository** interface in order to get the standard CRUD operations
    present in that interface.
- I’ve added several custom query methods to allow the user to find specific
    blueprints in other ways than just the ID.
       o You can find a blueprint by name. This returns an **Optional<Blueprint>**
          object because the blueprint may or may not be found. The **Optional** class
          handles a null value gracefully.
       o You can also filter blueprints by **type** , **requiredLvl** , or **buildTime** , either
          separately or simultaneously. I originally implemented each of these
          methods as a returnable **List** , but then opted for the **Page** class. Returning
          them as pages allows me to use default pagination and sorting. As the
          database becomes more robust, this is essential for readability and
          performance, rather than listing every single entry found all at once.

**BlueprintController.java:** The **RestController** for the **Blueprint** entity.

- It handles all the **RequestMapping**. It uses a common URL prefix ‘ **/blueprints** ’ for
    all the API endpoints.
- I use **@Autowired** on the **BlueprintRepository** object, so that Spring knows to inject
    it as a dependency.
- It supports **GET** , **PUT** , **POST** , and **DELETE** mapping.
- I use **@RequestBody** to accept a **Blueprint** object for **addBlueprint** and
    **updateBlueprint** methods. I use **@Valid** to make sure these blueprint arguments
    are validated across each validation constraint in the **Blueprint** class.
- The methods **getAllBlueprints** and **findBlueprintsByType** return a **Page** of
    blueprints, rather than a list for the same reasons as noted in the
    **BlueprintRepository** section. I use an **@PageableDefault** for default pagination
    and sorting settings in case the user doesn’t enter any. This is a future proof
    implementation, preventing memory issues that can arise when the database grows
    substantially.


- For the **GET** mappings that support filtering, I use **@RequestParam** with **Optional**
    objects. I handle the logic of which query methods to call based on which **Optional**
    is present.
- Every single method in this class returns a **ResponseEntity**. I wanted specific HTTP
    response codes for better readability for the users. Most methods return either a
    404 (not found) response or a 200 (OK) response. There are two exceptions:
       o The **POST** mapping returns a 201 (created) response when a new blueprint is
          successfully created, which is more specific.
       o A 400 (bad request) response is returned if any method is called with illegal
          arguments. Again, more specific.
- I opted for controller-level exception handling using **@ExceptionHandler** because
    the application only has a single controller. One method handles an
    **IllegalArgumentException** and the other handles a **BlueprintNotFoundException**.

# Further Expansions

There are Spring Boot features that I opted not to implement due to simplicity and
miniature nature of the project. However, if I were to make this a more robust application,
there are a number of changes I would consider:

- **Component** would become its own entity. It would be made up of materials, which
    would become the new record class.
- There would be a one-to-many **Blueprint** to **Component** entity relationship, which
    would allow for more flexibility with table entries.
- Currently, the **Blueprint** class doesn’t really have any functionality. I would add a
    method to the **Blueprint** class to actually craft it.
       o This would mean creating a **Player** entity with a one-to-many relationship,
          meaning one player can have multiple blueprints.
- I would consider adding a configuration for content negotiation. There may be some
    legacy systems that request objects in XML format rather than the standard JSON.
    Content negotiation would make my app more versatile by adding compatibility with
    legacy systems.
- I could utilize **HttpHeaders** , either to request them or to allow me to send custom
    headers in my **ResponseEntity**.
- If I wanted more complex custom SQL queries, I could use JPQL with the **@Query**
    annotation.


