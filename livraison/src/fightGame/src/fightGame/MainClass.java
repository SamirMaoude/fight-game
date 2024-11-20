package fightGame;

import java.util.ArrayList;
import java.util.List;

import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.aiAlgorithms.FightGamePlayerStrategy;
import fightGame.model.aiAlgorithms.MinimaxStrategy;
import fightGame.model.aiAlgorithms.RandomStrategy;
import fightGame.model.aiAlgorithms.strategy.RandaomFillStrategy;

public class MainClass {
    public static void main(String[] args) {

        // Charger les paramètres de jeu depuis le fichier XML
        UnchangeableSettings.loadSettings();

        GameBoard gameBoard = new GameBoard(UnchangeableSettings.NB_ROWS, UnchangeableSettings.NB_COLS,new RandaomFillStrategy());

        List<FightGamePlayer> players = new ArrayList<>();

        FightGamePlayerStrategy randomStrategy = new RandomStrategy();
        FightGamePlayerStrategy minimaxStrategy = new MinimaxStrategy();

        FightGamePlayer player1 = new FightGamePlayer(gameBoard, "Player 1", 0);
        player1.setStrategy(minimaxStrategy);
        FightGamePlayer player2 = new FightGamePlayer(gameBoard, "Player 2", 1);
        player2.setStrategy(randomStrategy);

        players.add(player1);
        players.add(player2);
        gameBoard.setPlayers(players);
        gameBoard.fillGameBoard();

        gameBoard.run();


    }
}
