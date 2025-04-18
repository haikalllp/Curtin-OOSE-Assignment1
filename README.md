## City Planning App Java Project
=================

## Project Overview

The **City Planning Application** is a program designed to assist in city development and planning with an interactive menu. User load in grid, then interact with menu to build structure (to validate whether a structure can built on the specific grid), build city with multiple strategies, display grids where structure built are marked, and calculate construction costs based on several factors such as contamination, flood risk, heritage, and terrain. The project follows Object-Oriented Design principles and uses design patterns such as **Strategy** and **Decorator** to handle the city's construction strategies and cost calculation mechanisms.

This project is built with **Java** using the **Gradle** build system.

## Features
- **Grid Management**: Load and manipulate city grids with buildings, infrastructure, and zoning.
- **Cost Calculation**: Dynamically calculate costs based on factors such as contamination, terrain, heritage, and flood risk using the **Decorator Pattern**.
- **City Planning Strategies**: Apply different city planning strategies like **Central Strategy**, **Random Strategy**, and **Uniform Strategy** using the **Strategy Pattern**.
- **User Interaction**: Interactive user menu to control city building, display grids, and validate build decisions.


## Running

To run your code (for debugging purposes), invoke the `gradlew` script with a `run` argument:

Since you will need to load grid file you need to provide command-line arguments:

$ ./gradlew run --args="YourGridFile"

If you run into permission problems:

$ bash gradlew run --args="YourGridFile"


## Linting and Testing

This project has been configured to use [PMD][] to check code quality, and to perform unit testing. perform these steps:

$ ./gradlew check

Alternatively, run 
$ ./gradlew build


## Logging

This project configures and utilised logging, all logs should be under `AppLog.log`


### **Folder Structure Breakdown**:

1. **`src/main/java/edu/curtin/city_planning_app/`**:
   - Contains the core Java source code.
   - Includes classes for managing the city-building process (`CityBuilderManager`, `CityPlannerMain`, etc.).
   - Sub-packages include:
     - **`decorators/`**: Holds classes for cost calculation using the decorator pattern.
     - **`grids/`**: Contains grid management classes such as `CityGrid` and `GridLoader`.
     - **`strategies/`**: Implements different city planning strategies using the strategy pattern.

2. **`src/main/resources/`**:
   - Contains resource files used by the application (e.g., grid files).
   
3. **`src/test/java/edu/curtin/city_planning_app/`**:
   - Contains unit tests for various parts of the application (e.g., `GridLoaderTest`).

4. **`README.md and CRITERIA.md`**:
   - Mark document file for both README file and CRTERIA.
   - CRITEA.md contains response to the criteria within the task brief.

5. **`UML_Diagrams`**:
   - Contains all the UML diagram as .png and .drawio
