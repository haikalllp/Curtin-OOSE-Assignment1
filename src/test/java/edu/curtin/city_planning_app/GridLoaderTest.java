// src/test/java/edu/curtin/city_planning_app/GridLoaderTest.java

package edu.curtin.city_planning_app;

import edu.curtin.city_planning_app.grids.GridLoader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class GridLoaderTest {
    private GridLoader loader;

    @BeforeEach
    public void setUp() {
        loader = new GridLoader();
    }

    @Test
    public void testEmptyGrid() {
        Path filePath = Paths.get("src/test/testResources/empty_grid");
        Exception exception = assertThrows(IOException.class, () -> {
            loader.loadGrid(filePath);
        });
        assertTrue(exception.getMessage().contains("Grid dimensions must not be empty."));
    }

    @Test
    public void testInvalidDimensions1() {
        Path filePath = Paths.get("src/test/testResources/invalid_dimensions1");
        Exception exception = assertThrows(IOException.class, () -> {
            loader.loadGrid(filePath);
        });
        assertTrue(exception.getMessage().contains("Invalid grid dimensions format"));
    }

    @Test
    public void testInvalidDimensions2() {
        Path filePath = Paths.get("src/test/testResources/invalid_dimensions2");
        Exception exception = assertThrows(IOException.class, () -> {
            loader.loadGrid(filePath);
        });
        assertTrue(exception.getMessage().contains("Grid dimensions must be Integers eg.(3,4)"));
    }

    @Test
    public void testMissingRecords() {
        Path filePath = Paths.get("src/test/testResources/missing_records");
        Exception exception = assertThrows(IOException.class, () -> {
            loader.loadGrid(filePath);
        });
        assertTrue(exception.getMessage().contains("Insufficient grid data for expected dimensions."));
    }

    @Test
    public void testInvalidTerrain() {
        Path filePath = Paths.get("src/test/testResources/invalid_terrain");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loader.loadGrid(filePath);
        });
        assertTrue(exception.getMessage().contains("Invalid terrain type"));
    }

    @Test
    public void testInvalidZoningRule() {
        Path filePath = Paths.get("src/test/testResources/invalid_zoning_rule");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loader.loadGrid(filePath);
        });
        System.out.println("Actual exception message: " + exception.getMessage());
        // Adjust to match the actual exception message
        assertTrue(exception.getMessage().contains("Unknown zoning rule"));
    }

    @Test
    public void testInvalidHeritage() {
        Path filePath = Paths.get("src/test/testResources/invalid_heritage");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loader.loadGrid(filePath);
        });
        assertTrue(exception.getMessage().contains("Invalid heritage value"));
    }

    @Test
    public void testInvalidFloodRisk1() {
        Path filePath = Paths.get("src/test/testResources/invalid_flood_risk1");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loader.loadGrid(filePath);
        });
        assertTrue(exception.getMessage().contains("Flood risk must be between 0 and 100"));
    }

    @Test
    public void testInvalidFloodRisk2() {
        Path filePath = Paths.get("src/test/testResources/invalid_flood_risk2");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loader.loadGrid(filePath);
        });
        assertTrue(exception.getMessage().contains("Flood risk must be a numeric value"));
    }

    @Test
    public void testInvalidContamination() {
        Path filePath = Paths.get("src/test/testResources/invalid_contamination");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loader.loadGrid(filePath);
        });
        assertTrue(exception.getMessage().contains("Contamination value must be 'true' or 'false'"));
    }

    @Test
    public void testInvalidHeightLimit1() {
        Path filePath = Paths.get("src/test/testResources/invalid_height_limit1");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loader.loadGrid(filePath);
        });
        assertTrue(exception.getMessage().contains("Height limit must be a positive integer"));
    }

    @Test
    public void testInvalidHeightLimit2() {
        Path filePath = Paths.get("src/test/testResources/invalid_height_limit2");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loader.loadGrid(filePath);
        });
        assertTrue(exception.getMessage().contains("Height limit must be an integer"));
    }
}
