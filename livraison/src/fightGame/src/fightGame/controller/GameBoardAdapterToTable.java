package fightGame.controller;

import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import gamePlayers.*;
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
        Position position = new Position(rowIndex, columnIndex);
        Set<AbstractGameEntity> entities = this.proxy.getEntitiesAt(position);
        List<Position> positionsImpactedByBombs = gameBoard.getImpactedPositionsByBomb();
        List<Position> positionsImpactedByMines = gameBoard.getImpactedPositionsByMine();
        List<Position> positionsImpactedByProjectils = gameBoard.getImpactedPositionsByProjectile();
        List<ImageIcon> icons = new ArrayList<>();
        if (positionsImpactedByBombs.contains(position)) {
            icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/bombexplosion.jpg"));
        }
        if (positionsImpactedByMines.contains(position)) {
            icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/minesexplosion.jpg"));
        }
        if (positionsImpactedByProjectils.contains(position)) {
            icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/projectiles.jpg"));
        }
        for (AbstractGameEntity entity : entities) {
            switch (entity.getType()) {
                case BOMB:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/bomb.png"));
                    break;
                case WALL:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/wall.png"));
                    break;
                case MINE:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/mines.jpg"));
                    break;
                case UNIT:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/unit.png"));
                    break;
                case PELLET:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/pellet.jpg"));
                    break;
               
                default:
                    break;
            }
        }
        if (icons.size() == 0) {
            icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/univers.png"));
        }
        return icons;
    }

    @Override
    public void update(ListenableModel source) {
        super.fireTableDataChanged();
    }

}
