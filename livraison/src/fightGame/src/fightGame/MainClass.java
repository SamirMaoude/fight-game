package fightGame;

import java.util.ArrayList;
import java.util.List;

import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.aiAlgorithms.FightGameRandomPlayer;
import gamePlayers.fighters.Unit;
import gamePlayers.util.Position;

public class MainClass {
    public static void main(String[] args) {

        // Charger les paramètres de jeu depuis le fichier XML
        UnchangeableSettings.loadSettings();

        GameBoard gameBoard = new GameBoard(UnchangeableSettings.NB_ROWS, UnchangeableSettings.NB_COLS);

        List<FightGamePlayer> players = new ArrayList<>();

        FightGamePlayer player1 = new FightGameRandomPlayer(gameBoard, "Player 1", 0, new Position(0, 0));
        FightGamePlayer player2 = new FightGameRandomPlayer(gameBoard, "Player 2", 1, new Position(9, 9));

        players.add(player1);
        players.add(player2);
        gameBoard.setPlayers(players);

        gameBoard.run();


    }
}
