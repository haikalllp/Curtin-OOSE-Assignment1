// ContaminationCost.java
package edu.curtin.city_planning_app.decorators;

import java.util.logging.Logger;

// Handle extra cost calculation for contamination
public class ContaminationCost implements HandleCostCalculation {
    private static final Logger logger = Logger.getLogger(ContaminationCost.class.getName());

    private HandleCostCalculation decoratedCost;

    public ContaminationCost(HandleCostCalculation decoratedCost) {
        this.decoratedCost = decoratedCost;
        logger.info("ContaminationCost decorator added.");
    }

    // Calculate the cost of a structure by adding a fixed multiplier for contamination.
    @Override
    public double calculateCost() {
        double cost = (decoratedCost.calculateCost() * 1.5); // 1.5x multiplier for contamination
        logger.info(() -> "Contamination applied, cost modified to: " + cost);
        return cost;
    }
}
