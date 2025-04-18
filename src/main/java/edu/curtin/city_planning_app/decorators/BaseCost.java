// BaseCost.java
package edu.curtin.city_planning_app.decorators;

import java.util.logging.Logger;

// Handle base cost calculation
public class BaseCost implements HandleCostCalculation {
    private static final Logger logger = Logger.getLogger(BaseCost.class.getName());

    private int floors;
    private String material;

    public BaseCost(int floors, String material) {
        this.floors = floors;
        this.material = material;
        logger.info(() -> "BaseCost created with " + floors + " floors and material: " + material);
    }

    // Calculate the cost of a structure by multiplying the material cost by the number of floors.
    @Override
    public double calculateCost() {
        double cost = getMaterialCost(material) * floors;
        logger.info(() -> "Calculated base cost: " + cost + " for " + floors + " floors of " + material);
        return cost;
    }

    // Calculates the material cost based on the material type.
    private double getMaterialCost(String material) {
        switch (material.toLowerCase()) { // to make not case sensitive
            case "wood": // for wood
                return 10000;
            case "stone": // for stone
                return 50000;
            case "brick": // for brick
                return 30000;
            case "concrete": // for concrete
                return 20000;
            default:
                logger.severe(() -> "Unknown material: " + material);
                throw new IllegalArgumentException("Unknown material: " + material);
        }
    }
}
