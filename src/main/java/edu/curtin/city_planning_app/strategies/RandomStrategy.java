// RandomStrategy.java
package edu.curtin.city_planning_app.strategies;

import edu.curtin.city_planning_app.CityBuilderManager;
import edu.curtin.city_planning_app.CityDisplayer;
import edu.curtin.city_planning_app.grids.CityGrid;

import java.util.Random;
import java.util.logging.Logger;

// Random Strategy Implementation
public class RandomStrategy extends TemplateStrategy {
    private static final Logger logger = Logger.getLogger(RandomStrategy.class.getName());
    private static final String[] MATERIALS = { "wood", "stone", "brick", "concrete" };
    private static final String[] FOUNDATIONS = { "slab", "stilts" };
    private Random random;

    private String currentMaterial;
    private int currentFloors;

    public RandomStrategy(CityGrid grid, CityDisplayer display, CityBuilderManager builder) {
        super(grid, display, builder);
        this.random = new Random();
    }

     // Randomly selects the material, floors, and foundation for a structure to
     // be built at the given grid coordinates, and then uses the
     // CityBuilderManager's buildStructure method to build the structure.
    @Override
    protected boolean buildStructureWithStrategy(int row, int col) {
        // Randomly select the material, floors, and foundation
        currentMaterial = MATERIALS[random.nextInt(MATERIALS.length)];
        currentFloors = random.nextInt(5) + 1;
        String foundation = FOUNDATIONS[random.nextInt(FOUNDATIONS.length)];

        // Use CityBuilderManager's buildStructure method and get the total cost for
        // this structure
        double totalCost = builder.buildStructure(row, col, currentFloors, foundation, currentMaterial, display);

        if (totalCost >= 0) { // Structure successfully built
            // Add the structure's cost to the total final cost in the builder
            builder.addStructure(totalCost);

            logger.info(() -> "Built at Grid (" + row + "," + col + ") - Material: " + currentMaterial + ", Floors: "
                    + currentFloors);
            displayStructureDetails(row, col, grid.getGridSquare(row, col), currentMaterial, currentFloors, totalCost);
            return true;
        } else {
            logger.warning(() -> "Build failed at Grid (" + row + "," + col + ")");
            return false;
        }
    }

}
