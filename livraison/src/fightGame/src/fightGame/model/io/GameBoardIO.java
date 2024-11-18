package fightGame.model.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.view.widgets.GameView;
import fightGame.view.widgets.InfosView;

public class GameBoardIO {

    public GameBoardIO() {

    }

    public static void chooseFile(JFrame component) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Game File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Game Files", "game", "txt"));
        int userSelection = fileChooser.showOpenDialog(component);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToLoad = fileChooser.getSelectedFile();
            System.out.println("Fichier sélectionné : " + fileToLoad.getAbsolutePath());
            loadGameFromFile(fileToLoad.getAbsolutePath());
        }
    }

    public static void loadGameFromFile(String filePath) {
        try (FileInputStream fileIn = new FileInputStream(filePath);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            GameBoard gameBoard = (GameBoard) in.readObject();
            //System.out.println("GameBoard chargé depuis le fichier : " + filePath);
            int nbPlayers = gameBoard.getPlayers().size();
            for (int i = 0; i < nbPlayers; i++) {
                FightGamePlayer player = gameBoard.getPlayers().get(i);
                new GameView("View for Player " + player.getName(), gameBoard, player.getGameBoardProxy());
            }
            //play(gameBoard);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
            return;
        }
    }

     public static void saveGame(JFrame frame, GameBoard gameBoard) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sauvegarder la partie");
        int userSelection = fileChooser.showSaveDialog(frame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            try (FileOutputStream fileOut = new FileOutputStream(filePath);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(gameBoard);
                new InfosView(frame, "Sauvegarde", "Votre jeu a été bien sauvegardé dans le fichier " + filePath, true);
            } catch (IOException e) {
                new InfosView(frame, "Sauvegarde", "Erreur lors de la sauvegarde : " + e.getMessage(), false);
            }
        } else {
            System.out.println("L'utilisateur a annulé la sauvegarde.");
        }
    }
}
