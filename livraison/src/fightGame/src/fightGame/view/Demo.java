package fightGame.view;

import fightGame.controller.GameBoardAdapterToTable;
import fightGame.model.GameBoard;
import fightGame.view.widgets.GameBoardTable;
import fightGame.view.widgets.GameView;
import gamePlayers.fighters.Unit;
import gamePlayers.util.Position;

public class Demo {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(4, 4);
        Unit unit = new Unit(new Position(1,1), "Patrice", null, 10, null, null, null, 5);
        gameBoard.addEntity(unit, unit.getPosition());
        GameBoardAdapterToTable adapterToTable = new GameBoardAdapterToTable(gameBoard);
        GameBoardTable gameBoardTable = new GameBoardTable(adapterToTable);
        GameView view = new GameView("Interface du jeu", gameBoardTable);

    }
}
