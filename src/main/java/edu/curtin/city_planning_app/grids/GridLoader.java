// GridLoader.java
package edu.curtin.city_planning_app.grids;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

public class GridLoader {
    // Logger to log important events
    private static final Logger logger = Logger.getLogger(GridLoader.class.getName());

    // Constants for zoning rule keys to avoid hardcoding strings
    private static final String ZONING_HERITAGE = "heritage";
    private static final String ZONING_FLOOD_RISK = "flood-risk";
    private static final String ZONING_CONTAMINATION = "contamination";
    private static final String ZONING_HEIGHT_LIMIT = "height-limit";

    /**
     * Loads the grid from a given file path.
     * resourcePath The path to the grid file. FROM CityPlannerMain.java
     * returns A 2D array of GridSquare objects.
     * it throws IOException if the file cannot be found or read.
     */
    public GridSquare[][] loadGrid(Path resourcePath) throws IOException {
        if (resourcePath == null || !resourcePath.toFile().exists()) {
            throw new IOException("File not found: " + resourcePath.getFileName()
                    + "\nMake sure grid file is located in: 'src/main/java/edu/curtin/city_planning_app/resources'");
        }

        // Open the file using BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(resourcePath.toFile()))) {
            String line = br.readLine();

            // Check if the file is empty
            if (line == null || line.trim().isEmpty()) {
                throw new IOException("Grid dimensions must not be empty.");
            }

            // Split the first line to get grid dimensions (height, width)
            String[] dimensions = line.split(",");
            if (dimensions.length != 2) {
                throw new IOException("Invalid grid dimensions format, expected format 'height,width' (with comma ',').");
            }

            // Parse grid dimensions
            int height = parseDimension(dimensions[0], "height");
            int width = parseDimension(dimensions[1], "width");

            logger.info(() -> "Loading grid of size: " + height + "x" + width);

            GridSquare[][] gridSquares = new GridSquare[height][width];

            // Read and parse each grid square from the file
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    line = br.readLine();
                    if (line == null) {
                        throw new IOException("Insufficient grid data for expected dimensions.");
                    }
                    gridSquares[i][j] = parseGridSquare(line);
                }
            }

            logger.info("Grid successfully loaded.");
            return gridSquares;
        }
    }

    // Parses a grid dimension (height or width) from the input string.
    // Unused parameter String type
    @SuppressWarnings("PMD.UnusedFormalParameter") // Surpress warnings for exception hanlding to work
    private int parseDimension(String dimension, String type) throws IOException {
        try {
            int value = Integer.parseInt(dimension.trim());
            if (value <= 0) {
                throw new IOException("Grid dimensions must not be empty.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IOException("Grid dimensions must be Integers eg.(3,4)", e);
        }
    }

     // Parses a line from the grid file and creates a GridSquare object.
     // The line should include terrain type and optional zoning rules.
    private GridSquare parseGridSquare(String line) {
        String[] properties = line.split(",");

        // Check if terrain type is provided
        if (properties.length == 0 || properties[0].trim().isEmpty()) {
            throw new IllegalArgumentException("Terrain type cannot be empty.");
        }

        // Parse the terrain type (e.g., FLAT, SWAMPY, ROCKY)
        GridSquare.TerrainType terrain;
        try {
            terrain = GridSquare.TerrainType.valueOf(properties[0].toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid terrain type: '" + properties[0]
                    + "'\nTerrain type must be: ('FLAT', 'SWAMPY', 'ROCKY')", e);
        }

        // Create a new GridSquare with the parsed terrain type
        GridSquare square = new GridSquare(terrain);

        // Process any zoning rules for this grid square
        for (int k = 1; k < properties.length; k++) {
            String[] rule = properties[k].split("=");
            final int logk = k;
            if (rule.length == 2) {
                String ruleKey = rule[0].trim();
                String ruleValue = rule[1].trim();
                validateAndApplyRule(square, ruleKey, ruleValue);  // Validate and apply zoning rule
            } else if (rule.length == 1 && ZONING_CONTAMINATION.equals(rule[0].trim())) {
                // Special case for contamination rule without value
                square.setZoningRule(ZONING_CONTAMINATION, "true");
                logger.info(() -> "Applied contamination rule to square: " + line);
            } else {
                logger.warning(() -> "Invalid zoning rule format: " + properties[logk]);
                throw new IllegalArgumentException("Invalid zoning rule: " + properties[k]);
            }
        }

        return square;
    }

    // Validates and applies a zoning rule to a given GridSquare.
    private void validateAndApplyRule(GridSquare square, String ruleKey, String ruleValue) {
        switch (ruleKey) {
            case ZONING_HERITAGE:
                validateHeritage(ruleValue);
                square.setZoningRule(ruleKey, ruleValue);
                break;
            case ZONING_FLOOD_RISK:
                validateFloodRisk(ruleValue);
                square.setZoningRule(ruleKey, ruleValue);
                break;
            case ZONING_CONTAMINATION:
                validateContamination(ruleValue);
                square.setZoningRule(ruleKey, ruleValue);
                break;
            case ZONING_HEIGHT_LIMIT:
                validateHeightLimit(ruleValue);
                square.setZoningRule(ruleKey, ruleValue);
                break;
            default:
                throw new IllegalArgumentException("Unknown zoning rule: '" + ruleKey
                        + "'\nValid rules: ('heritage', 'flood-risk', 'contamination', 'height-limit').");
        }
    }

    /* Validation methods for each zoning rules: */

    // Validates the heritage rule value (must be stone, brick, or wood).
    private void validateHeritage(String ruleValue) {
        if (!ruleValue.equals("stone") && !ruleValue.equals("brick") && !ruleValue.equals("wood")) {
            throw new IllegalArgumentException("Invalid heritage value: " + ruleValue
                    + "\nHeritage must be: ('stone', 'brick', 'wood').");
        }
    }

    // Validates the flood risk value (must be between 0 and 100).
    private void validateFloodRisk(String ruleValue) {
        try {
            double floodRisk = Double.parseDouble(ruleValue);
            if (floodRisk < 0 || floodRisk > 100) {
                throw new IllegalArgumentException("Flood risk must be between 0 and 100.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Flood risk must be a numeric value.", e);
        }
    }

    // Validates the contamination value (must be true or false).
    private void validateContamination(String ruleValue) {
        if (!ruleValue.equals("true") && !ruleValue.equals("false")) {
            throw new IllegalArgumentException("Contamination value must be 'true' or 'false'.");
        }
    }

    // Validates the height limit value (must be a positive integer).
    private void validateHeightLimit(String ruleValue) {
        try {
            int heightLimit = Integer.parseInt(ruleValue);
            if (heightLimit <= 0) {
                throw new IllegalArgumentException("Height limit must be a positive integer.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Height limit must be an integer.", e);
        }
    }
}
