package fightGame.view;

import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;

import fightGame.UnchangeableSettings;
import fightGame.controller.GameBoardAdapterToTable;
import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.aiAlgorithms.FightGameRandomPlayer;
import fightGame.model.objects.Wall;
import fightGame.view.widgets.GameBoardTable;
import fightGame.view.widgets.GameView;
import gamePlayers.fighters.Unit;
import gamePlayers.fighters.UnitBuilder;
import gamePlayers.objects.Bomb;
import gamePlayers.objects.Mine;
import gamePlayers.util.Direction;
import gamePlayers.util.Player;
import gamePlayers.util.Position;

public class Demo {
    public static void main(String[] args) {
        UnchangeableSettings.loadSettings();
        List<Player> players = new ArrayList<>();
        GameBoard gameBoard = new GameBoard(4, 4);


        //players.add(new FightGamePlayer(null, null, 0, null))
        FightGameRandomPlayer randomPlayer1 = new FightGameRandomPlayer(gameBoard,"Zade",1,new Position(0,0));
        FightGameRandomPlayer randomPlayer2 = new FightGameRandomPlayer(gameBoard,"Patrice",2,new Position(3,3));
        players.add(randomPlayer1);
        players.add(randomPlayer2);

        //gameBoard.setPlayers();
        Bomb bomb = new Bomb(new Position(1,1), 10, null, 4);
        gameBoard.addEntity(bomb, bomb.getPosition());
        Mine mine = new Mine(new Position(1,2), 14, null);
        gameBoard.addEntity(mine, mine.getPosition());
        Wall wall = new Wall(new Position(3,1));
        gameBoard.addEntity(wall, wall.getPosition());
        Unit unit3 = new Unit(new Position(1,3), "Patrice", null, 10, null, null, null, 5);
        gameBoard.addEntity(unit3, unit3.getPosition());

        //

        GameBoardAdapterToTable adapterToTable = new GameBoardAdapterToTable(gameBoard);
        GameBoardTable gameBoardTable = new GameBoardTable(adapterToTable);
        DashBordView dashBordView = new DashBordView(gameBoard);
        GameView view = new GameView("Interface du jeu", gameBoardTable,dashBordView);

        //Thread.sleep(5000);
        gameBoard.moveUnit(new Position(0,0), Direction.LEFT);
    }
}
