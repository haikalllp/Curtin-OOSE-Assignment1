// CityBuilderManager.java
package edu.curtin.city_planning_app;

import edu.curtin.city_planning_app.decorators.*;
import edu.curtin.city_planning_app.grids.CityGrid;
import edu.curtin.city_planning_app.grids.GridSquare;
import edu.curtin.city_planning_app.strategies.TemplateStrategy;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;

public class CityBuilderManager {
    private static final Logger logger = Logger.getLogger(CityBuilderManager.class.getName());
    private TemplateStrategy currentStrategy;
    private int totalBuiltStructures; // To track the total structures built
    private double totalFinalCost; // To track the final total cost of building all structures
    private CityGrid cityGrid; // Add CityGrid dependency

    public CityBuilderManager(CityGrid cityGrid) {
        this.cityGrid = cityGrid; // Initialize CityGrid
        this.totalFinalCost = 0; // Initialize totalFinalCost to 0
    }

    /* --- For 'Build Structure' option --- */

    // Handles individual build structure, check validation, and cost calculation.
    // Also used for build city where runned multiple times with selected strategy
    public double buildStructure(int row, int col, int floors, String foundation, String material,
            CityDisplayer display) {
        if (!cityGrid.isValidGridSquarePosition(row, col)) {
            logger.warning("Invalid grid coordinates.");
            return -1; // Return -1 to indicate failure
        }

        GridSquare square = cityGrid.getGridSquare(row, col);
        logger.info(() -> "Attempting to build structure at (" + row + ", " + col + ")");

        // Validate the structure
        CheckBuildValidation checkBuild = new CheckBuildValidation();
        String validationMessage = checkBuild.validate(square, floors, foundation, material);
        if (!validationMessage.equals("valid")) { // Structure cannot be built
            System.out.println("\nValidation failed: " + validationMessage);
            return -1; // Return -1 if validation fails
        }

        // Using Decorators to add additional costs
        HandleCostCalculation costCalculation = new BaseCost(floors, material);
        if (square.isContaminated()) {
            costCalculation = new ContaminationCost(costCalculation);
        }
        if (square.hasFloodRisk()) {
            costCalculation = new FloodRiskCost(costCalculation, square.getFloodRisk());
        }
        if (square.getHeritage() != null) {
            costCalculation = new HeritageCost(costCalculation);
        }
        costCalculation = new TerrainCost(costCalculation, square.getTerrain(), floors);

        // Calculate total cost (this is for a single structure)
        double structureCost = costCalculation.calculateCost();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedTotalCost = currencyFormatter.format(structureCost);

        logger.info(() -> "Total cost for the structure calculated: " + formattedTotalCost);

        return structureCost; // Return the total cost for this structure on success
    }

    /* --- For 'Build City' option --- */

    // Set the strategy and use the Template for building city
    public void setStrategy(TemplateStrategy strategy) {
        this.currentStrategy = strategy;
        logger.info(() -> "Strategy set to: " + strategy.getClass().getSimpleName());
    }

    // Get the name of the strategy
    public String getStrategyName() {
        if (currentStrategy == null) {
            logger.warning("No strategy configured.");
            return "No strategy configured";
        }
        return currentStrategy.getClass().getSimpleName(); // Get the name of the strategy class
    }

    // Perform build city by utilising template method and the strategy chosen
    // Template strategy is overridden with the respective chosen strategy
    // Which performs the build city logic based on the chosen strategy.
    public void buildCityWithStrategy() {
        if (currentStrategy == null) {
            logger.warning("Attempted to build city with no strategy selected.");
            System.out.println("\nNo strategy selected. Please configure the city-building approach first.\n");
        } else {
            System.out.println("\nBuilding City using strategy: " + getStrategyName());
            logger.info(() -> "Building city using strategy: " + getStrategyName());

            // Reset the total built structures and total final cost before building
            totalBuiltStructures = 0;
            totalFinalCost = 0; // Reset total final cost before starting

            // Call the buildCity method within TemplateStrategy with the chosen strategy
            currentStrategy.buildCity();

            // Display the total final cost after the city is built
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
            String formattedTotalFinalCost = currencyFormatter.format(totalFinalCost);
            System.out.println("\n--- City Built Successfully! ---");
            System.out.println("Total Final Cost for Building the City: " + formattedTotalFinalCost + "\n");
        }
    }

    // Getters and Helper methods for strategies

    // Getter for total structures built
    public int getTotalBuiltStructures() {
        return totalBuiltStructures;
    }

    // Getter for total final cost
    public double getTotalFinalCost() {
        return totalFinalCost;
    }

    // Increment the built structures and total final cost (called by the strategy)
    public void addStructure(double cost) {
        totalBuiltStructures++;
        totalFinalCost += cost; // Accumulate the final total cost
    }
}
