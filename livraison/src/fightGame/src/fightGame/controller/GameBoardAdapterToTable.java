package fightGame.controller;

import java.util.Set;

import javax.swing.table.AbstractTableModel;

import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import gamePlayers.AbstractGameEntity;
import gamePlayers.util.Position;

public class GameBoardAdapterToTable extends AbstractTableModel {
    private GameBoard gameBoard;

    public GameBoardAdapterToTable(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }
    @Override
    public int getColumnCount() {
        return this.gameBoard.getCols();
    }

    @Override
    public int getRowCount() {
        return this.gameBoard.getRows();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //GameBoardProxy proxy = this.gameBoard.getNextPlayer().getGameBoardProxy();
        Set<AbstractGameEntity> list =  gameBoard.getEntitiesAt(new Position(rowIndex,columnIndex));
        String chaine = "";
        for (AbstractGameEntity entity : list) {
            chaine += entity.toString();
        }
        return  chaine;
    }
    
}
