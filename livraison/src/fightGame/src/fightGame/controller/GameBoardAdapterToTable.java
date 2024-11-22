package fightGame.controller;

import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import gamePlayers.*;
import gamePlayers.fighters.Unit;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;
import gamePlayers.util.Position;

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
        List<Position> positionsImpactedByBombs = gameBoard.getImpactedPositionsByBomb();
        List<Position> positionsImpactedByMines = gameBoard.getImpactedPositionsByMine();
        List<Position> positionsImpactedByProjectils = gameBoard.getImpactedPositionsByProjectile();
        List<ImageIcon> icons = new ArrayList<>();

        Set<AbstractGameEntity> entities;
        if (this.proxy == null) {
            entities = this.gameBoard.getEntitiesAt(position);
        } else {
            entities = this.proxy.getEntitiesAt(position);
        }

        if (positionsImpactedByBombs.contains(position)) {
            icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/bombexplosion.jpg"));
        }
        if (positionsImpactedByMines.contains(position)) {
            icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/minesexplosion.jpg"));
        }

        if (positionsImpactedByProjectils.contains(position)) {
            // System.out.println(this.gameBoard.getLastActionPlayed().getTYPE());
            switch (this.gameBoard.getLastActionPlayed().getTYPE()) {
                case USE_PROJECTILE_AT_RIGHT:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/right.jpg"));
                    break;
                case USE_PROJECTILE_AT_LEFT:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/left.jpg"));
                    break;
                case USE_PROJECTILE_AT_TOP:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/top.jpg"));
                    break;
                case USE_PROJECTILE_AT_BOTTOM:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/bottom.jpg"));
                    break;
                default:
                    break;
            }

        }

        for (AbstractGameEntity entity : entities) {
            switch (entity.getType()) {
                case BOMB:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/bombicon.png"));
                    break;
                case WALL:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/wall.png"));
                    break;
                case MINE:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/mines.jpg"));
                    break;
                case UNIT:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/unit.png"));
                    Unit u = (Unit) entity;
                    if (u.isShieldActivated()) {
                        icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/shield.jpg"));
                    }
                    break;
                case PELLET:
                    icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/pellet.jpg"));
                    break;

                default:
                    break;
            }
        }
        if (icons.size() == 0) {
            icons.add(new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/blanc.png"));
        }
        return icons;
    }

    @Override
    public void update(ListenableModel source) {
        super.fireTableDataChanged();
    }

}
