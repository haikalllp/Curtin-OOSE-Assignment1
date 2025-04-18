// CityDisplayer.java
package edu.curtin.city_planning_app;

import java.util.logging.Logger;

// Responsible for displaying the grid, showing where structures were built
public class CityDisplayer {
    private static final Logger logger = Logger.getLogger(CityDisplayer.class.getName());
    private boolean[][] builtStructures;

    public CityDisplayer(int height, int width) {
        this.builtStructures = new boolean[height][width];
        logger.info(() -> "Initialized CityDisplayer with grid size: " + height + "x" + width);
    }

    // Mark a structure as built
    public void markStructure(int row, int col) {
        if (isWithinGridBounds(row, col)) {
            builtStructures[row][col] = true;
            logger.info(() -> "Structure built at (" + row + "," + col + ")");
        } else {
            logger.warning(() -> "Invalid position: (" + row + "," + col + "). Cannot mark structure.");
        }
    }

    // Display the grid showing where structures were built
    public void displayBuiltStructures() {
        System.out.println("\n--- Display Built Structures in Grid ---\n");
        logger.info("Displaying built structures");

        // Add grid border
        System.out.println("     " + createColumnHeaders(builtStructures[0].length)); // Column numbers
        System.out.println("    " + createHorizontalBorder(builtStructures[0].length));

        for (int row = 0; row < builtStructures.length; row++) {
            System.out.printf("%2d | ", row); // Row numbers
            for (int col = 0; col < builtStructures[row].length; col++) {
                char displayChar = builtStructures[row][col] ? 'X' : '.';
                System.out.print(displayChar + "  ");
            }
            System.out.println("|");
        }

        System.out.println("    " + createHorizontalBorder(builtStructures[0].length));
        displayLegend();
    }

    
    // Creates a string of column headers for the grid display.
    // Each column header is a number (0, 1, 2, ..., width-1) with two spaces after each number.
    private String createColumnHeaders(int width) {
        StringBuilder header = new StringBuilder();
        for (int i = 0; i < width; i++) {
            header.append(i).append("  ");
        }
        return header.toString();
    }

    // Creates a horizontal border string of the given width
    private String createHorizontalBorder(int width) {
        StringBuilder border = new StringBuilder();
        for (int i = 0; i < width; i++) {
            border.append("---");
        }
        return border.toString();
    }

    // Checks if the given row and column represent a valid position on the grid.
    private boolean isWithinGridBounds(int row, int col) {
        boolean valid = row >= 0 && row < builtStructures.length && col >= 0 && col < builtStructures[0].length;
        if (!valid) {
            logger.warning(() -> "Invalid grid position: (" + row + "," + col + ")");
        }
        return valid;
    }

    // Display legend for visual representation
    private void displayLegend() {
        System.out.println("\nLegend:");
        System.out.println("X  = Built Structure");
        System.out.println(".  = Empty Grid");
    }
}
