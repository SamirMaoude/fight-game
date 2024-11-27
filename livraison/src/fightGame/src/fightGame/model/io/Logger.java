package fightGame.model.io;

import java.io.FileWriter;
import java.io.IOException;
import fightGame.view.widgets.InfosView;
import gamePlayers.util.Player;

/**
 * Logger class for recording game actions performed by players into a log file.
 */
public class Logger {

    /**
     * Path to the log file.
     */
    private static final String LOG_FILE = "livraison/src/fightGame/log.txt";

    /**
     * Constructs a Logger instance and clears the existing contents of the log file.
     * Displays an error message if the file operation fails.
     */
    public Logger() {
        try (FileWriter writer = new FileWriter(LOG_FILE)) {
            // Clears the log file.
        } catch (IOException e) {
            new InfosView(null, "Error in log process", "Error deleting file contents: \n" + e.getMessage(), false);
        }
    }

    /**
     * Logs an action performed by a player to the log file.
     *
     * @param player the {@link Player} performing the action
     * @param action the action description to log
     */
    public void log(Player player, String action) {
        String texte = player.getUnit().getName() + " play " + action;
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(texte + System.lineSeparator());
        } catch (IOException e) {
            new InfosView(null, "Error in log process", "Log failed \n" + e.getMessage(), false);
        }
    }
}

