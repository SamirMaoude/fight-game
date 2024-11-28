package fightGame.controller;

import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import fightGame.view.InterfaceSetting;
import gamePlayers.*;
import gamePlayers.fighters.Unit;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;
import gamePlayers.util.Position;

/**
 * Adapter class that bridges the {@link GameBoard} model to a {@link JTable}.
 * Implements {@link AbstractTableModel} and listens to model updates.
 */
public class GameBoardAdapterToTable extends AbstractTableModel implements ModelListener {
    /**
     * The underlying game board model.
     */
    private GameBoard gameBoard;
    /**
     * Proxy for the game board, providing a limited view of the game state.
     */
    private GameBoardProxy proxy;

    /**
     * Constructs the adapter with the given game board and optional proxy.
     * Registers this adapter as a listener to the game board.
     *
     * @param gameBoard the game board model
     * @param proxy     the proxy for the game board (nullable)
     */
    public GameBoardAdapterToTable(GameBoard gameBoard, GameBoardProxy proxy) {
        this.gameBoard = gameBoard;
        this.proxy = proxy;
        this.gameBoard.addModelListener(this);
    }

    /**
     * Returns the number of columns in the game board.
     *
     * @return the number of columns
     */
    @Override
    public int getColumnCount() {
        return this.gameBoard.getCols();
    }

    /**
     * Returns the number of rows in the game board.
     *
     * @return the number of rows
     */
    @Override
    public int getRowCount() {
        return this.gameBoard.getRows();
    }

    /**
     * Retrieves the value at a specified cell in the game board.
     * Returns a list of {@link ImageIcon} objects representing entities
     * and visual effects at the given position.
     *
     * @param rowIndex    the row index of the cell
     * @param columnIndex the column index of the cell
     * @return a list of {@link ImageIcon} objects representing the cell's content
     */

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
            icons.add(new ImageIcon(InterfaceSetting.BOMB_EXPLOSION_URL));
        }
        if (positionsImpactedByMines.contains(position)) {
            icons.add(new ImageIcon(InterfaceSetting.BOMB_EXPLOSION_URL));
        }

        if (positionsImpactedByProjectils.contains(position)) {
            switch (this.gameBoard.getLastActionPlayed().getTYPE()) {
                case USE_PROJECTILE_AT_RIGHT:
                    icons.add(new ImageIcon(InterfaceSetting.PROJECTIL_RIGHT_ICON_URL));
                    break;
                case USE_PROJECTILE_AT_LEFT:
                    icons.add(new ImageIcon(InterfaceSetting.PROJECTIL_LEFT_ICON_URL));
                    break;
                case USE_PROJECTILE_AT_TOP:
                    icons.add(new ImageIcon(InterfaceSetting.PROJECTIL_TOP_ICON_URL));
                    break;
                case USE_PROJECTILE_AT_BOTTOM:
                    icons.add(new ImageIcon(InterfaceSetting.PROJECTIL_BOTTOM_ICON_URL));
                    break;
                default:
                    break;
            }
        }

        for (AbstractGameEntity entity : entities) {
            switch (entity.getType()) {
                case BOMB:
                    icons.add(new ImageIcon(InterfaceSetting.BOMB_ICON_URL));
                    break;
                case WALL:
                    icons.add(new ImageIcon(InterfaceSetting.WALL_ICON_URL));
                    break;
                case MINE:
                    icons.add(new ImageIcon(InterfaceSetting.MINE_ICON_URL));
                    break;
                case UNIT:
                    if (proxy == null) {
                        icons.add(new ImageIcon(InterfaceSetting.UNIT_ICON_URL));
                    } else if (proxy.getPlayer().equals(entity.getOwner())) {
                        icons.add(new ImageIcon(InterfaceSetting.MAIN_UNIT_ICON_URL));

                    } else {
                        icons.add(new ImageIcon(InterfaceSetting.UNIT_ICON_URL));
                    }
                    Unit u = (Unit) entity;
                    if (u.isShieldActivated()) {
                        icons.add(new ImageIcon(InterfaceSetting.SHIELD_URL));
                    }
                    break;
                case PELLET:
                    icons.add(new ImageIcon(InterfaceSetting.PELLET_ICON_URL));
                    break;

                default:
                    break;
            }
        }
        if (icons.size() == 0) {
            icons.add(new ImageIcon(InterfaceSetting.BLANC_ICON_URL));
        }
        return icons;
    }

    /**
     * Notifies the table model that the game board has been updated.
     * Triggers a refresh of all table data.
     *
     * @param source the {@link ListenableModel} that triggered the update
     */
    @Override
    public void update(ListenableModel source) {
        super.fireTableDataChanged();
    }

    /**
     * Returns the underlying game board model.
     *
     * @return the {@link GameBoard} instance
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Returns the proxy for the game board, if any.
     *
     * @return the {@link GameBoardProxy} instance, or {@code null} if no proxy is
     *         used
     */
    public GameBoardProxy getProxy() {
        return proxy;
    }

}
