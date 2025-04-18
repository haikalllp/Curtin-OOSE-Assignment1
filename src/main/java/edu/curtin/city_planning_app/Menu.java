// Menu.java
package edu.curtin.city_planning_app;

import edu.curtin.city_planning_app.grids.CityGrid;
import edu.curtin.city_planning_app.grids.GridSquare;
import edu.curtin.city_planning_app.strategies.*;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.InputMismatchException;
import java.util.Scanner;

// Menu for the City Planning program
public class Menu {
    private Scanner scanner;
    private CityGrid grid;
    private CityBuilderManager builder;
    private CityDisplayer displayBuiltCity;

    public Menu(CityGrid grid) {
        this.grid = grid;
        this.scanner = new Scanner(System.in);
        this.displayBuiltCity = new CityDisplayer(grid.getHeight(), grid.getWidth());
        this.builder = new CityBuilderManager(grid); // Pass CityGrid to CityBuilderManager

        // Set default strategy to Random
        builder.setStrategy(new RandomStrategy(grid, displayBuiltCity, builder));
    }

    /**
     * Displays the main menu for the City Planning program.
     * The options are:
     * 1. Build Structure (Validation Check)
     * 2. Build City
     * 3. Configure Strategy
     * 4. Display Previously Built Structures
     * 5. Quit
     * The user is prompted to select a valid option, and the corresponding action
     * is taken.
     * The loop continues until the user selects option 5 (Quit).
     */
    public void displayMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n==============================");
            System.out.println("      City Planning Menu");
            System.out.println("==============================\n");
            System.out.println("1. Build Structure (Validation Check)");
            System.out.println("2. Build City");
            System.out.println("3. Configure Strategy");
            System.out.println("4. Display Previously Built Structures");
            System.out.println("5. Quit\n");
            System.out.print("Select an option (1-5): ");

            int choice = checkValidInteger();
            switch (choice) {
                case 1:
                    buildStructure();
                    break;
                case 2:
                    buildTheCity();
                    break;
                case 3:
                    configure();
                    break;
                case 4:
                    // Display Previously built structures
                    displayBuiltCity.displayBuiltStructures(); // CityDisplayer.java
                    break;
                case 5:
                    running = false;
                    System.out.println("\nExiting the program... Goodbye!");
                    break;
                default:
                    System.out.println("\nInvalid option. Please try again.\n");
            }
        }
    }

    /**
     * Build a structure on the grid with validation checks.
     * 
     * Asks the user for the grid coordinates, number of floors, foundation type,
     * and construction material. Then, it checks if the given coordinates and
     * values are valid. If valid, it builds the structure by calling the
     * buildStructure method in CityBuilderManager. The total cost of the
     * structure is then displayed. If the input is invalid, it shows an error
     * message and clears the invalid input from the scanner buffer.
     */
    private void buildStructure() {
        int row, col, floors;
        String foundation, material;

        try {
            System.out.println("\n--- Build Structure (Validation Check) ---");

            System.out.print("Enter the grid row index: ");
            row = checkValidInteger();

            System.out.print("Enter the grid column index: ");
            col = checkValidInteger();

            // Check if the grid coordinates are valid
            if (!grid.isValidGridSquarePosition(row, col)) {
                System.out.println("\nInvalid grid coordinates. Please try again.\n");
                return;
            }

            // Fetch and display the details of the selected grid square
            showGridSquareDetails(row, col);

            System.out.print("Enter the number of floors to be built: ");
            floors = checkValidInteger();

            System.out.print("Enter the foundation type (slab/stilts): ");
            foundation = scanner.next().toLowerCase();

            if (!foundation.equals("slab") && !foundation.equals("stilts")) {
                System.out.println("\nInvalid foundation type. Please enter 'slab' or 'stilts'.\n");
                return;
            }

            System.out.print("Enter the construction material (wood/stone/brick/concrete): ");
            material = scanner.next().toLowerCase();

            if (!material.equals("wood") && !material.equals("stone") &&
                    !material.equals("brick") && !material.equals("concrete")) {
                System.out.println("\nInvalid material type. Please enter 'wood', 'stone', 'brick', or 'concrete'.\n");
                return;
            }

            // Run the build structure method in CityBuilderManager
            // Now it returns the total cost on success, -1 on failure
            double totalCost = builder.buildStructure(row, col, floors, foundation, material, displayBuiltCity);

            if (totalCost >= 0) {
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
                String formattedTotalCost = currencyFormatter.format(totalCost);
                System.out.println("\nStructure successfully built at (" + row + ", " + col + ").");
                System.out.println("Total cost: " + formattedTotalCost);
            } else {
                System.out.println("Failed to build structure at (" + row + ", " + col
                        + ")");
            }

        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Please enter the correct values.\n");
            scanner.nextLine(); // Clear the invalid input from the scanner buffer
        }
    }

    // Method to build the city
    // Utilises strategy chosen
    // Send grid details as well as strategy chosen to CityBuilderManager
    private void buildTheCity() {
        String strategyName = builder.getStrategyName();
        System.out.println("\n--- Current Strategy: " + strategyName + " ---");

        // Let CityBuilderManager handle the city building
        builder.buildCityWithStrategy();

        // Summary of city building result
        int totalBuilt = builder.getTotalBuiltStructures();
        double totalFinalCost = builder.getTotalFinalCost();

        // Format the total final cost with commas
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedTotalFinalCost = currencyFormatter.format(totalFinalCost);

        System.out.println("\n--- City Building Summary ---");
        System.out.println("Strategy Used: '" + strategyName + "'");
        System.out.println("Total Structures Built: " + totalBuilt);
        System.out.println("Final Total Cost: " + formattedTotalFinalCost); // Use formatted total final cost
        System.out.println("-----------------------------\n");
    }

    /**
     * Configure the city-building strategy. This method will print out a menu
     * allowing the user to select one of the three strategies: Uniform, Random,
     * or Central. Depending on the choice, the user will be prompted to enter
     * additional information such as material, number of floors, and foundation
     * type. The chosen strategy will then be passed to the CityBuilderManager.
     */
    private void configure() {
        System.out.println("\n--- Configure City-Building Strategy ---");
        System.out.println("1. Uniform");
        System.out.println("2. Random");
        System.out.println("3. Central");
        System.out.print("Select a strategy (1-3): ");

        int choice = checkValidInteger();
        switch (choice) {
            case 1:
                System.out.print("Enter material (wood/stone/brick/concrete): ");
                String material = scanner.next().toLowerCase();
                System.out.print("Enter number of floors: ");
                int floors = checkValidInteger();
                System.out.print("Enter foundation type (slab/stilts): ");
                String foundation = scanner.next().toLowerCase();

                // Pass CityBuilderManager instance to the strategy
                builder.setStrategy(
                        new UniformStrategy(grid, material, floors, foundation, displayBuiltCity, builder));
                System.out.println("Strategy set to: 'Uniform'\n");
                break;

            case 2:
                // Pass CityBuilderManager instance to the strategy
                builder.setStrategy(
                        new RandomStrategy(grid, displayBuiltCity, builder));
                System.out.println("\nStrategy set to: 'Random'\n");
                break;

            case 3:
                // Pass CityBuilderManager instance to the strategy
                builder.setStrategy(
                        new CentralStrategy(grid, displayBuiltCity, builder));
                System.out.println("\nStrategy set to: 'Central'\n");
                break;

            default:
                System.out.println("\nInvalid option. Returning to menu.\n");
        }
    }


     // Prints the details of the given grid square
     // The details include the location, terrain type, heritage status, height
     // limit, flood risk, and contamination status.
    private void showGridSquareDetails(int row, int col) {
        GridSquare square = grid.getGridSquare(row, col);
        System.out.println("\n==============================");
        System.out.println(" Current Grid Square Details");
        System.out.println("==============================");
        System.out.println("Location: (" + row + ", " + col + ")");
        System.out.println("Terrain: " + square.getTerrain());

        if (square.getHeritage() != null) {
            System.out.println("Heritage: " + square.getHeritage());
        }

        if (square.hasHeightLimit()) {
            System.out.println("Height Limit: " + square.getHeightLimit());
        }

        if (square.hasFloodRisk()) {
            System.out.println("Flood Risk: " + square.getFloodRisk() + "%");
        }

        if (square.isContaminated()) {
            System.out.println("Contamination: Yes");
        } else {
            System.out.println("Contamination: No");
        }

        System.out.println();
    }

    
     // Checks if the user input is a valid integer.
     // If the input is not a valid integer, it will print a message and ask for
     // input again.
    private int checkValidInteger() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid integer.\n");
                scanner.nextLine(); // Clear the invalid input from the scanner buffer
            }
        }
    }
}
