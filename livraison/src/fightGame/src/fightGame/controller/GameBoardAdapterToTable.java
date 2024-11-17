package fightGame.controller;

import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import gamePlayers.*;
import gamePlayers.fighters.Unit;
import gamePlayers.util.*;

public class GameBoardAdapterToTable extends AbstractTableModel implements ModelListener {
    private GameBoard gameBoard;
    private GameBoardProxy proxy;

    public GameBoardAdapterToTable(GameBoard gameBoard, GameBoardProxy proxy) {
        this.gameBoard = gameBoard;
        this.proxy = proxy;
        this.gameBoard.addModelListerner(this);
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
        Set<AbstractGameEntity> entities = this.proxy.getEntitiesAt(new Position(rowIndex, columnIndex));
        List<Position> bombsPositions = gameBoard.getImpactedPositionsByBomb();
        if(bombsPositions.contains(new Position(rowIndex,columnIndex))){
            return new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/explosion.jpg");
        }
        if (entities.size() == 1) {
            Iterator<AbstractGameEntity> iterator = entities.iterator();
            AbstractGameEntity entity = iterator.next();
            switch (entity.getType()) {
                case BOMB:
                    return new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/bomb.png");
                case WALL:
                    return new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/wall.png");
                case MINE:
                    return new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/mines.jpg");
                case UNIT:
                    return new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/unit.png");
                case PELLET:
                    return new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/pellet.png");
                case PROJECTILE:
                    return new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/projectile.png");
                default:
                    break;
            }
        } else if (entities.size()>1){
            return new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/multiUnit.png");
        }
        return "";
    }

    @Override
    public void update(ListenableModel source) {
        super.fireTableDataChanged();
    }

}
