// Central Strategy
package edu.curtin.city_planning_app.strategies;

import edu.curtin.city_planning_app.CityBuilderManager;
import edu.curtin.city_planning_app.CityDisplayer;
import edu.curtin.city_planning_app.grids.CityGrid;

import java.util.logging.Logger;

// Central Strategy Implementation
public class CentralStrategy extends TemplateStrategy {
    private static final Logger logger = Logger.getLogger(CentralStrategy.class.getName());

    public CentralStrategy(CityGrid grid, CityDisplayer display, CityBuilderManager builder) {
        super(grid, display, builder);
    }

     //Central strategy for building structures on the grid. It builds structures
     // with floors and materials based on the centrality of the grid square.
    @Override
    protected boolean buildStructureWithStrategy(int row, int col) {
        // Calculate floors and material based on the centrality of the grid square
        int floors = calculateFloors(row, col);
        String material = calculateMaterial(row, col);
        String foundation = "slab"; // Always slab foundation in central strategy

        // Use CityBuilderManager's buildStructure method and get the total cost for
        // this structure
        double totalCost = builder.buildStructure(row, col, floors, foundation, material, display);

        if (totalCost >= 0) { // Structure successfully built
            // Add the structure's cost to the total final cost in the builder
            builder.addStructure(totalCost);

            logger.info(
                    () -> "Built at Grid (" + row + "," + col + ") - Material: " + material + ", Floors: " + floors);
            displayStructureDetails(row, col, grid.getGridSquare(row, col), material, floors, totalCost);
            return true;
        } else {
            logger.warning(() -> "Build failed at Grid (" + row + "," + col + ")");
            return false;
        }
    }

    // Methods to calculate floors and materials based on distance from the center
    private int calculateFloors(int row, int col) {
        double distance = calculateDistance(row, col);
        return (int) Math.round(1 + (20 / (distance + 1)));
    }

    // Find the material based on the distance from the center
    private String calculateMaterial(int row, int col) {
        double distance = calculateDistance(row, col);
        if (distance <= 2) {
            return "concrete";
        } else if (distance <= 4) {
            return "brick";
        } else if (distance <= 6) {
            return "stone";
        } else {
            return "wood";
        }
    }

    // Calculate the distance from the center
    private double calculateDistance(int row, int col) {
        int centerRow = (grid.getHeight() - 1) / 2;
        int centerCol = (grid.getWidth() - 1) / 2;
        return Math.sqrt(Math.pow(row - centerRow, 2) + Math.pow(col - centerCol, 2));
    }

}
