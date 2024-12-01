package fightGame.model.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import fightGame.controller.GameManager;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.strategy.HumainStrategy;
import fightGame.view.GUI;
import fightGame.view.widgets.GameView;
import fightGame.view.widgets.InfosView;

/**
 * Utility class for handling the saving and loading of the game state.
 * Provides methods to serialize and deserialize the `GameBoard` object
 * and create corresponding game views.
 */
public class GameBoardIO {

    /**
     * Opens a file chooser dialog to let the user select a game file for loading.
     * If a valid file is selected, the game state is loaded and views are created.
     *
     * @param component the parent GUI for the dialog
     */
    public static void chooseFile(GUI component) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Game File");

        // Add a file filter to show only .fightGame files
        fileChooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter("Fight Game Files (*.fightGame)", "fightGame"));

        int userSelection = fileChooser.showOpenDialog(component);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToLoad = fileChooser.getSelectedFile();

            // Ensure the selected file has the correct extension
            if (fileToLoad.getName().endsWith(".fightGame")) {
                loadGameFromFile(fileToLoad.getAbsolutePath(), component);
            } else {
                new InfosView(component, "Error", "Invalid file format selected. Please select a .fightGame file.",
                        false);
            }
        } else {
            new InfosView(component, "Error", "No file selected", false);
        }
    }

    /**
     * Deserializes the game state from a file and initializes the game with
     * corresponding views and managers.
     *
     * @param filePath the path to the serialized game file
     */
    public static void loadGameFromFile(String filePath, GUI gui) {
        try (FileInputStream fileIn = new FileInputStream(filePath);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            GameBoard gameBoard = (GameBoard) in.readObject();
            int nbPlayers = gameBoard.getPlayers().size();
            Logger logger = new Logger();
            GameManager manager = null;

            if (isHumainPlayed(gameBoard)) {
                manager = new GameManager(gameBoard, logger);
            }

            for (int i = 0; i < nbPlayers; i++) {
                FightGamePlayer player = gameBoard.getPlayers().get(i);
                GUI.gameViews.add(new GameView("View for Player " + player.getName(), gameBoard,
                        player.getGameBoardProxy(), null, logger));
            }
            GUI.gameViews.add(new GameView("Main frame", gameBoard, null, manager, logger));

            if (manager != null) {
                manager.playGame();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while loading: " + e.getMessage());
        }
    }

    /**
     * Checks if the game board contains a player with a `HumainStrategy`.
     *
     * @param gameBoard the game board to check
     * @return true if a player with a human strategy exists, false otherwise
     */
    private static boolean isHumainPlayed(GameBoard gameBoard) {
        for (FightGamePlayer player : gameBoard.getPlayers()) {
            if (player.getStrategy().getClass().equals(HumainStrategy.class)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Opens a file chooser dialog to save the current game state to a file.
     * The game state is serialized and written to the specified file with a
     * `.fightGame` extension.
     *
     * @param frame     the parent JFrame for the dialog
     * @param gameBoard the game board to be serialized and saved
     */
    public static void saveGame(JFrame frame, GameBoard gameBoard) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save game");
        int userSelection = fileChooser.showSaveDialog(frame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Ensure the file has the .fightGame extension
            if (!filePath.endsWith(".fightGame")) {
                filePath += ".fightGame";
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(gameBoard);
                new InfosView(frame, "Save", "Your game has been successfully saved in the file " + filePath, true);
            } catch (IOException e) {
                new InfosView(frame, "Save", "Error while saving: " + e.getMessage(), false);
            }
        }
    }
}
