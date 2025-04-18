// CheckBuildValidation.java
package edu.curtin.city_planning_app;

import java.util.logging.Logger;

import edu.curtin.city_planning_app.grids.GridSquare;

// Responsible for validating the build request
// Depending on the validation rules of the city, the request may or may not be valid
public class CheckBuildValidation {
    private static final Logger logger = Logger.getLogger(CheckBuildValidation.class.getName());

     // Validate a build request.
     // Check if the given build request is valid according to the city's building rules.
    public String validate(GridSquare square, int floors, String foundation, String material) {
        if (square.isContaminated()) { // contaminated land
            logger.info("Validation failed: contaminated land.");
            return "Cannot build on contaminated land.";
        }

        // building slab foundation in SWAMPY
        if (square.getTerrain() == GridSquare.TerrainType.SWAMPY && "slab".equals(foundation)) {
            logger.info("Validation failed: slab foundation in swampy terrain.");
            return "Cannot build slab foundation on swampy terrain.";
        }

        // building wooden structure in SWAMPY
        if (square.getTerrain() == GridSquare.TerrainType.SWAMPY && "wood".equals(material)) {
            logger.info("Validation failed: wooden structure in swampy terrain.");
            return "Cannot build a wooden structure in a swamp.";
        }

        // validate heritage rule
        if (square.getHeritage() != null && !material.equals(square.getHeritage())) {
            logger.info("Validation failed: heritage zoning rule mismatch.");
            return "Cannot build a structure with material " + material + " due to heritage zoning requiring "
                    + square.getHeritage() + ".";
        }

        // validate height limit
        if (square.hasHeightLimit() && floors > square.getHeightLimit()) {
            logger.info(() -> "Validation failed: height limit exceeded. Max floors: " + square.getHeightLimit()
                    + ", requested: " + floors);
            return "Cannot build structure with " + floors + " floors. Height limit is " + square.getHeightLimit()
                    + ".";
        }

        // validate flood risk
        if (square.hasFloodRisk() && floors < 2) {
            logger.info(() -> "Validation failed: insufficient floors for flood-risk area. Flood risk: "
                    + square.getFloodRisk());
            return "A structure in a flood-risk zone must have at least two floors.";
        }

        // return validation as valid if all checks passed
        logger.info("Validation passed for building.");
        return "valid";
    }
}
