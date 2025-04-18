// CityPlannerMain.java
package edu.curtin.city_planning_app;

import edu.curtin.city_planning_app.grids.CityGrid;
import edu.curtin.city_planning_app.grids.GridLoader;
import edu.curtin.city_planning_app.grids.GridSquare;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class CityPlannerMain {
    private static final Logger logger = Logger.getLogger(CityPlannerMain.class.getName());

    // The main entry point of the City Planner application.
    // Accepts the name of the grid data file as a command-line argument.
    // And then Loads the grid and displays the menu
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("You must include the grid data file name as a command-line argument.\n" +
                    "Usage: ./gradlew run --args=\"YourGridFile.txt\"");
            return;
        }

        String filename = args[0];
        GridLoader loader = new GridLoader();

        // Handling the file path here and passing it to GridLoader
        Path resourcePath = Paths.get("src/main/resources", filename);

        try {
            GridSquare[][] gridArray = loader.loadGrid(resourcePath);

            if (gridArray.length == 0 || gridArray[0].length == 0) {
                System.err.println("Error: Grid cannot be empty. Make sure grid has valid dimensions.");
                return;
            }

            CityGrid grid = new CityGrid(gridArray);
            System.out.println("Grid loaded successfully. Proceeding with city planning...");

            // Create a Menu instance and load the menu
            Menu menu = new Menu(grid);
            menu.displayMenu();

        } catch (IOException e) {
            logger.severe(() -> "Error loading grid file: " + e.getMessage());
            System.err.println();
            System.err.println("Error loading grid file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warning(() -> "Validation Error: " + e.getMessage());
            System.err.println();
            System.err.println("Validation Error: " + e.getMessage());
        }
    }
}
