// UniformStrategy.java
package edu.curtin.city_planning_app.strategies;

import edu.curtin.city_planning_app.CityBuilderManager;
import edu.curtin.city_planning_app.CityDisplayer;
import edu.curtin.city_planning_app.grids.CityGrid;

import java.util.logging.Logger;

// Uniform Strategy Implementation
public class UniformStrategy extends TemplateStrategy {
    private static final Logger logger = Logger.getLogger(UniformStrategy.class.getName());
    private String material;
    private int floors;
    private String foundation;

    // Get the grid, material, floors and foundation from the Menu with user prompt,
    // and stores it here.
    public UniformStrategy(CityGrid grid, String material, int floors, String foundation, CityDisplayer display,
            CityBuilderManager builder) {
        super(grid, display, builder);
        this.material = material;
        this.floors = floors;
        this.foundation = foundation;
    }

     // Uniform strategy for building structures on the grid. It builds structures
     // with the same material and number of floors on each grid square.
    @Override
    protected boolean buildStructureWithStrategy(int row, int col) {
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

}
