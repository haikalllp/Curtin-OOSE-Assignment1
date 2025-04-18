// TemplateStrategy.java
package edu.curtin.city_planning_app.strategies;

import edu.curtin.city_planning_app.CityBuilderManager;
import edu.curtin.city_planning_app.CityDisplayer;
import edu.curtin.city_planning_app.grids.CityGrid;
import edu.curtin.city_planning_app.grids.GridSquare;

import java.util.logging.Logger;

public abstract class TemplateStrategy {
    private static final Logger logger = Logger.getLogger(TemplateStrategy.class.getName());
    protected CityGrid grid; // The grid representing the city's layout
    protected CityDisplayer display; // Display for showing built structures
    protected CityBuilderManager builder; // Reference to CityBuilderManager for tracking built structures and costs

    // Constructor that initializes the grid, display, and CityBuilderManager
    public TemplateStrategy(CityGrid grid, CityDisplayer display, CityBuilderManager builder) {
        this.grid = grid;
        this.display = display;
        this.builder = builder;
    }

    // Template method for building the city
    // Iterates through all grid squares and attempts to build structures according
    // to the strategy
    public final void buildCity() {
        // Loop through all grid squares
        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                final int finalRow = row;
                final int finalCol = col;

                // Call buildStructure and return boolean value as structureBuilt
                // buildStructure of the chosen strategy is repeteadly called in the loop
                boolean structureBuilt = buildStructureWithStrategy(finalRow, finalCol);

                // Check if the structure was built
                if (structureBuilt) {
                    // Mark structure as built in the display
                    // Mark Struture is used in the CityDisplayer class
                    // Allowing to Mark which part of the grid has structure built or not
                    display.markStructure(finalRow, finalCol);
                    logger.info(() -> "Structure built at (" + finalRow + "," + finalCol + ").");
                }
            }
        }
        // Display the results of the built city
        displayResults();
    }

    // Abstract hook method for building a structure (used by strategies)
    // Each concrete strategy will implement its own logic for building a structure
    // Each concrete strategy will calculate the total cost of current structure
    // Total cost of each build will then be tracked in the CityBuilderManager
    // Which is then accumulated in the CityBuilderManager.addStructure() method
    protected abstract boolean buildStructureWithStrategy(int row, int col);

     // Displays the details of a built structure in the console.
     // The details include the material used, the number of floors, the flood risk
     // (if any), the contamination status, the terrain type, and the total cost of
     // the structure.
    protected void displayStructureDetails(int row, int col, GridSquare square, String material, int floors,
            double totalCost) {
        System.out.println("Built at Grid Location (" + row + ", " + col + "):");
        System.out.println(" - Material: " + material);
        System.out.println(" - Floors: " + floors);
        System.out.println(" - Flood Risk: " + (square.hasFloodRisk() ? square.getFloodRisk() + "%" : "None"));
        System.out.println(" - Contamination: " + (square.isContaminated() ? "Yes" : "No"));
        System.out.println(" - Terrain: " + square.getTerrain());
        System.out.printf(" - Total Cost: $%.2f\n", totalCost); // Format the total cost to 2 decimal places
    }

    // Method to display the results of the city-building process
    private void displayResults() {
        logger.info(() -> "Total structures built: " + builder.getTotalBuiltStructures());
        logger.info(() -> "Total final cost: $" + builder.getTotalFinalCost());
        display.displayBuiltStructures(); // Display the city with all built structures
    }
}
