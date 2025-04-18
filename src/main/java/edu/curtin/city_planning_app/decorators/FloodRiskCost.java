// FloodRiskCost.java
package edu.curtin.city_planning_app.decorators;

import java.util.logging.Logger;

// Handle extra cost calculation for flood risk
public class FloodRiskCost implements HandleCostCalculation {
    private static final Logger logger = Logger.getLogger(FloodRiskCost.class.getName());

    private HandleCostCalculation decoratedCost;
    private double floodRisk;

    public FloodRiskCost(HandleCostCalculation decoratedCost, double floodRisk) {
        this.decoratedCost = decoratedCost;
        this.floodRisk = floodRisk;
        logger.info(() -> "FloodRiskCost created with flood risk: " + floodRisk);
    }

    /*
     * Calculates the cost of the structure including the flood risk multiplier.
     * The flood risk multiplier is 1 + (floodRisk / 50), and the cost is
     * the base cost multiplied by this multiplier.
     */
    @Override
    public double calculateCost() {
        double floodMultiplier = 1 + (floodRisk / 50);
        double cost = (decoratedCost.calculateCost() * floodMultiplier);
        logger.info(() -> "Flood risk applied with multiplier: " + floodMultiplier + ", modified cost: " + cost);
        return cost;
    }
}
