// TerrainCost.java
package edu.curtin.city_planning_app.decorators;

import java.util.logging.Logger;

import edu.curtin.city_planning_app.grids.GridSquare;

// Handle extra cost calculation for terrain types
public class TerrainCost implements HandleCostCalculation {
    private static final Logger logger = Logger.getLogger(TerrainCost.class.getName());
    private HandleCostCalculation decoratedCost;
    private GridSquare.TerrainType terrain;
    private int floors;

    // Constructor that takes HandleCostCalculation, TerrainType, and floors
    public TerrainCost(HandleCostCalculation decoratedCost, GridSquare.TerrainType terrain, int floors) {
        this.decoratedCost = decoratedCost;
        this.terrain = terrain;
        this.floors = floors;
    }

    // Calculate the cost of a structure by adding extra cost based on terrain type.
     // The extra cost is determined by the terrain type and the number of floors
    @Override
    public double calculateCost() {
        double baseCost = decoratedCost.calculateCost(); // Get the cost from previous decorators
        final double finalCost;

        // Modify cost based on terrain, considering the number of floors
        switch (terrain) {
            case SWAMPY: // for swampy (20000 * floo numbers) + base cost
                finalCost = baseCost + 20000 * floors;
                logger.info(() -> "Swampy terrain cost added, new cost: " + finalCost);
                break;
            case ROCKY: // for rocky (50000) + base cost
                finalCost = baseCost + 50000;
                logger.info(() -> "Rocky terrain cost added, new cost: " + finalCost);
                break;
            case FLAT: // for flat (base cost)
                finalCost = baseCost;
                logger.info("Flat terrain, no additional cost applied.");
                break;
            default:
                finalCost = baseCost;
                break;
        }

        return finalCost; // Return the modified cost
    }
}
