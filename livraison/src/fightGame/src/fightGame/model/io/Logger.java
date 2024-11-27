package fightGame.model.io;

import java.io.FileWriter;
import java.io.IOException;
import fightGame.view.widgets.InfosView;
import gamePlayers.util.Player;

public class Logger {
    private static final String LOG_FILE = "livraison/src/fightGame/log.txt";

    public Logger(){
        try (FileWriter writer = new FileWriter(LOG_FILE)) { 
        } catch (IOException e) {
            new InfosView(null, "Error in log process",  "Error deleting file contents: \n" + e.getMessage(), false);
        }
    }
    public void log(Player player, String action){
        String texte = player.getUnit().getName() + " play " + action;
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) { 
            writer.write(texte + System.lineSeparator());
        } catch (IOException e) {
            new InfosView(null, "Error in log process",  "Log failed \n" + e.getMessage(), false);
        }
    }
}
