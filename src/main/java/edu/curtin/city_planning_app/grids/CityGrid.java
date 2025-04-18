// CityGrid.java
package edu.curtin.city_planning_app.grids;

import java.util.logging.Logger;

public class CityGrid {
    private static final Logger logger = Logger.getLogger(CityGrid.class.getName());

    private int height;
    private int width;
    private GridSquare[][] gridSquares;

    public CityGrid(GridSquare[][] gridSquares) {
        this.gridSquares = gridSquares;
        this.height = gridSquares.length;
        this.width = gridSquares[0].length;
        logger.info(() -> "CityGrid created with dimensions: " + height + "x" + width);
    }

    // Returns the GridSquare object at the given row and column.
    public GridSquare getGridSquare(int row, int col) {
        return gridSquares[row][col];
    }

    // Checks if the given row and column represent a valid position on the grid.
    public boolean isValidGridSquarePosition(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    // Returns the height of the grid.
    public int getHeight() {
        return height;
    }

    // Returns the width of the grid.
    public int getWidth() {
        return width;
    }
}
