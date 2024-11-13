package fightGame.model;

import fightGame.controller.GameBoardAdapterToTable;
import gamePlayers.fighters.Unit;
import gamePlayers.util.Position;

public class Demo {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(4, 4);
        Unit unit = new Unit(new Position(1,1), "Patrice", null, 10, null, null, null, 5);
        gameBoard.addEntity(unit, unit.getPosition());

        GameBoardAdapterToTable adapterToTable = new GameBoardAdapterToTable(gameBoard);
        


    }
}
