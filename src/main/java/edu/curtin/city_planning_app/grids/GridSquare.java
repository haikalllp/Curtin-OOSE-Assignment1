// GridSquare.java
package edu.curtin.city_planning_app.grids;

import java.util.logging.Logger;

public class GridSquare {
    private static final Logger logger = Logger.getLogger(GridSquare.class.getName());

    public enum TerrainType {
        FLAT, SWAMPY, ROCKY
    }

    private TerrainType terrain;
    private String heritage;
    private Integer heightLimit;
    private Double floodRisk;
    private boolean contamination;

    public GridSquare(TerrainType terrain) {
        this.terrain = terrain;
        this.heritage = null;
        this.heightLimit = null;
        this.floodRisk = null;
        this.contamination = false;
        logger.info(() -> "Created GridSquare with terrain: " + terrain.name());
    }

    // Get the terrain type of this GridSquare.
    public TerrainType getTerrain() {
        return terrain;
    }

    // Set a zoning rule for this GridSquare.
    public void setZoningRule(String rule, String value) {
        switch (rule) {
            case "heritage":
                this.heritage = value;
                logger.info(() -> "Set heritage to: " + value);
                break;
            case "height-limit":
                this.heightLimit = Integer.parseInt(value);
                logger.info(() -> "Set height limit to: " + value);
                break;
            case "flood-risk":
                this.floodRisk = Double.parseDouble(value);
                logger.info(() -> "Set flood risk to: " + value);
                break;
            case "contamination":
                this.contamination = true;
                logger.info(() -> "Set contamination to true.");
                break;
            default:
                throw new IllegalArgumentException("Unknown zoning rule: " + rule);
        }
    }

    // Get the heritage status of this GridSquare.
    public String getHeritage() {
        return heritage;
    }

    // Get the height limit of this GridSquare.
    public Integer getHeightLimit() {
        return heightLimit;
    }

    // Get the flood risk of this GridSquare.
    public Double getFloodRisk() {
        return floodRisk;
    }

    // Check if this GridSquare is contaminated.
    public boolean isContaminated() {
        return contamination;
    }

    // Check if this GridSquare has a height limit.
    public boolean hasHeightLimit() {
        return heightLimit != null;
    }

    // Check if this GridSquare has a flood risk.
    public boolean hasFloodRisk() {
        return floodRisk != null;
    }
}
