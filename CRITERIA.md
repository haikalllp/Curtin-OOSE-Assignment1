# City Planning Application - Criteria Response

This document explains how the **City Planning Application** project satisfies the criteria outlined in the assignment instructions.

## a) General Code Quality

- Based on thorough testing, the final build of the program produces no errors or warnings from the compiler or linting tools such as PMD.
- Running `./gradlew build` completes without any errors, and the program functions as expected.
- The code is structured to the best of my ability, adhering to clean coding practices.
- The base project configuration remains unchanged to ensure adherence to coding standards, with PMD rules left at their default settings.
- The only change made to the `build.gradle` file was to align with the project’s file structure.
- Warnings have only been suppressed when necessary, with clear and reasonable justifications provided inline in the code.
- Each class and method is properly documented with comments explaining their purpose and functionality.


## b) Appropriate Use of Containers

- The program makes effective use of containers for managing city elements. For instance:
  - The grid-handling classes, `CityGrid` and `GridLoader`, utilize 2D arrays of `GridSquare` objects to store and manage grid cells.
  - The `CheckBuildValidation` class uses collections of validation rules to verify building constraints, designed for easy reusability by other classes.
  - These containers allow for flexible data handling while maintaining a clean and scalable codebase.


## c) Clear and Distinct Class/Interface/Method Responsibilities

- Each class, interface, and method in the project has clear and distinct responsibilities. For example:
  - The `CityBuilderManager` class handles the entire process of managing city development, including constructing buildings, managing costs through decorators, and utilizing the strategy and template method patterns to build cities based on the selected strategy.
  - The `CityDisplayer` class is responsible for displaying built structures within the city grid, along with the specific locations where the structures are placed.
  - The `CityPlannerMain` class serves as the main entry point and is responsible for managing command-line arguments.
  - The `GridLoader` class is solely responsible for loading city grids from resource files.
  - The `Menu` class manages user interaction, orchestrating the actions of building structures, managing the city, and handling configurations by calling appropriate classes.
- Naming conventions for all classes, methods, and attributes follow logical and descriptive standards, ensuring readability and maintainability.


## d) Appropriate Error Handling and Logging

- The application implements **exception handling** to gracefully manage errors. For example:
  - `CityPlannerMain` includes logic to handle argument-related errors and uses try-catch blocks to manage file I/O, particularly when calling `GridLoader` to load grid files.
  - `GridLoader` also employs try-catch blocks to handle file I/O errors, and it validates the grid file's structure, including zoning rules, grid size, and format. This class ensures that the contents of grid files (such as booleans for contamination, flood risk ranges, and numeric values) are valid before they are loaded, preventing runtime errors caused by malformed files.
  - `Menu` features proper handling of user input, with reusable validation methods like `checkValidInteger()` to ensure user entries are correctly processed.
  
- **Logging** is used to track important events, such as loading grids, building structures, and calculating costs. This helps with debugging and monitoring the application's runtime behavior.


## e) Appropriate Use of the Strategy Pattern / Template Method Pattern

### **Why I Utilized the Strategy Pattern**
The **Strategy Pattern** was chosen to provide flexibility in the city-building logic. By employing this pattern, I could define multiple algorithms for city-building, each encapsulated in its own class, while maintaining a consistent interface. This design simplifies future extensions and modifications by allowing new strategies to be introduced without altering the core logic of the `CityBuilderManager` class.

The Strategy Pattern provides:
- **Flexibility**: New city-building algorithms can be introduced by simply creating new strategy classes, without modifying existing code.
- **Separation of Concerns**: The logic for planning the city is encapsulated within distinct strategy classes, keeping the city management logic in `CityBuilderManager` focused and clean.
- **Polymorphism**: Strategies are interchangeable at runtime, allowing `CityBuilderManager` to dynamically apply different city-building strategies.

### **How I Implemented the Strategy Pattern**
The **Strategy Pattern** is implemented in the `strategies` package, where each strategy defines a distinct approach to city planning:
- **`TemplateStrategy`** serves as the base interface that defines the structure for all city-building strategies. It acts as the template for various city-building methods which follows a template method pattern.
  
  The following strategies implement this interface:
  - **`CentralStrategy`**: Builds the city from a central point and expands outward, using specific rules to allocate materials, floors, and foundations based on proximity to the center grid.
  - **`RandomStrategy`**: Assigns random materials, floors, and foundations across the grid.
  - **`UniformStrategy`**: Ensures uniform development across the grid.

Each strategy provides a different method of city planning, offering a variety of outcomes. These strategies are implemented independently and can be plugged into `CityBuilderManager` at runtime, allowing for dynamic behavior. In `CityBuilderManager`, I maintain a reference to `TemplateStrategy`. Based on user input or system conditions, an appropriate strategy (such as `CentralStrategy`, `RandomStrategy`, or `UniformStrategy`) is assigned, and the `currentStrategy.buildCity()` method is called to apply the selected strategy.

This separation of concerns makes the application flexible and scalable, allowing for future strategy extensions without modifying existing code.


## f) Appropriate Use of the Decorator Pattern

### **Why I Utilized the Decorator Pattern**
The **Decorator Pattern** was chosen to dynamically add construction costs based on multiple factors (such as contamination, terrain, heritage, and flood risk). Different buildings can have multiple cost factors applied simultaneously, and I wanted to ensure that this calculation could be handled flexibly and extendably.

The Decorator Pattern provides:
- **Scalability**: New cost factors can be introduced as decorators without altering the core cost calculation logic.
- **Dynamic Behavior**: Costs can be applied conditionally or in combination, allowing for a flexible cost structure that adapts to various building conditions.
- **Separation of Concerns**: Each cost calculation (e.g., contamination, terrain, heritage) is encapsulated within its own class, making the codebase easier to manage and extend.

### **How I Implemented the Decorator Pattern**
I created an abstract base class `BaseCost` in the `decorators` package, which provides the basic structure for calculating costs through the `calculateCost()` method.

**Concrete Decorators**: Each specific cost factor (such as contamination, flood risk, heritage, and terrain) is implemented as a concrete decorator class extending `BaseCost`. These decorators override the `calculateCost()` method to add their specific cost to the total:
- **`ContaminationCost`**: Adds costs associated with contamination in certain areas of the grid.
- **`FloodRiskCost`**: Adds costs based on the flood risk of specific grid squares.
- **`HeritageCost`**: Adds costs for protecting heritage buildings or zones.
- **`TerrainCost`**: Adds costs associated with challenging terrain features.

Each decorator wraps around an existing `BaseCost` object, enabling multiple decorators to be chained together. This design allows the calculation of cumulative costs by combining multiple cost factors.

For example, if a building is in a contaminated area with flood risk, I can wrap the `BaseCost` with both `ContaminationCost` and `FloodRiskCost` decorators. This chain of decorators ensures that the total cost reflects all relevant factors dynamically.


## g) UML Class Diagram

A comprehensive **UML class diagram** representing the entire system is included in the 'diagrams' folder located within the project’s root directory (`UML_Diagrams/UML.png or UML_drawio`).
- The diagram illustrates all the classes, interfaces, and the relationships between them, including **inheritance**, **composition**, **aggregation**, **associations**, and **dependencies**.
- It adheres strictly to UML conventions and was created using **Draw.io**, ensuring clarity and consistency throughout.
- Each class is fully detailed, with both **attributes** and **methods** clearly presented, reflecting the precise structure of the system.

The diagram emphasizes the key relationships between core components:
- **`CityBuilderManager`**: Responsible for coordinating the city-building process using various strategies and cost calculations.
- **`CityGrid`**: Represents the layout and management of the grid on which structures are built.
- **`HandleCostCalculation` Interface**: Central to the cost calculation, implemented by the core class `BaseCost` and extended through several decorators like `ContaminationCost`, `FloodRiskCost`, `HeritageCost`, and `TerrainCost`.
- **`TemplateStrategy` and Its Variants**: Defines the city-building strategies (`RandomStrategy`, `CentralStrategy`, and `UniformStrategy`) using the strategy pattern, dynamically altering how the city is built.
- **Decorators**: The system’s cost calculation follows the **Decorator Pattern**, where each cost-related decorator (`ContaminationCost`, `FloodRiskCost`, `HeritageCost`, `TerrainCost`) enhances the base cost calculation, wrapping around `HandleCostCalculation` instances to apply additional logic.

This UML diagram offers a complete and detailed visual overview of the program's architecture, showcasing the relationships between key components such as the **strategies**, **cost calculators**, and the **grid management**. The diagram is designed to aid in understanding the system’s modular structure, its use of design patterns, and how different components interact to fulfill the city-building functionality.
