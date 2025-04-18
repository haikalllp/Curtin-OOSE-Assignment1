// HeritageCost.java
package edu.curtin.city_planning_app.decorators;

import java.util.logging.Logger;

// Handle extra cost calculation for heritage sites
public class HeritageCost implements HandleCostCalculation {
    private static final Logger logger = Logger.getLogger(HeritageCost.class.getName());

    private HandleCostCalculation decoratedCost;

    public HeritageCost(HandleCostCalculation decoratedCost) {
        this.decoratedCost = decoratedCost;
        logger.info("HeritageCost decorator added.");
    }

    // Calculate the cost of a structure by adding a fixed surcharge for heritage sites.
    @Override
    public double calculateCost() {
        double cost = decoratedCost.calculateCost() + 20000; // Fixed surcharge for heritage sites
        logger.info(() -> "Heritage cost applied, final cost: " + cost);
        return cost;
    }
}
