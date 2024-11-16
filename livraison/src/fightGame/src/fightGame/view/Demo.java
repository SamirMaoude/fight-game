package fightGame.view;

import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;

import fightGame.UnchangeableSettings;
import fightGame.controller.GameBoardAdapterToTable;
import fightGame.model.FightGameAction;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.aiAlgorithms.FightGameRandomPlayer;
import fightGame.model.objects.Wall;
import fightGame.view.widgets.DashBordView;
import fightGame.view.widgets.GameBoardTable;
import fightGame.view.widgets.GameView;
import gamePlayers.util.Position;

public class Demo {
    public static void main(String[] args) {
        UnchangeableSettings.loadSettings();
        List<FightGamePlayer> players = new ArrayList<>();
        GameBoard gameBoard = new GameBoard(5, 5);

        //players.add(new FightGamePlayer(null, null, 0, null))
        FightGameRandomPlayer randomPlayer1 = new FightGameRandomPlayer(gameBoard,"Zade",1,new Position(0,0));
        FightGameRandomPlayer randomPlayer2 = new FightGameRandomPlayer(gameBoard,"Patrice",2,new Position(0,1));
        FightGameRandomPlayer randomPlayer3 = new FightGameRandomPlayer(gameBoard,"Rico",2,new Position(1,1));
        FightGameRandomPlayer randomPlayer4 = new FightGameRandomPlayer(gameBoard,"Manu",2,new Position(2,2));
        FightGameRandomPlayer randomPlayer5 = new FightGameRandomPlayer(gameBoard,"Padre",2,new Position(1,2));
        FightGameRandomPlayer randomPlayer6 = new FightGameRandomPlayer(gameBoard,"Mum",2,new Position(4,3));
        FightGameRandomPlayer randomPlayer7 = new FightGameRandomPlayer(gameBoard,"Mich",2,new Position(2,0));
        FightGameRandomPlayer randomPlayer8 = new FightGameRandomPlayer(gameBoard,"Sonia",2,new Position(3,3));

        players.add(randomPlayer1);
        players.add(randomPlayer2);
        players.add(randomPlayer3);
        players.add(randomPlayer4);
        players.add(randomPlayer5);
        players.add(randomPlayer6);
        players.add(randomPlayer7);
        players.add(randomPlayer8);

        gameBoard.setPlayers(players);

        GameBoardAdapterToTable adapterToTable = new GameBoardAdapterToTable(gameBoard);
        GameBoardTable gameBoardTable = new GameBoardTable(adapterToTable);
        DashBordView dashBordView = new DashBordView(gameBoard);
        GameView view = new GameView("Interface du jeu", gameBoardTable,dashBordView);
        try {
            gameBoard.run();

        } catch (Exception e) {
            // TODO: handle exception
        }
        
            
        
        
    }
}
