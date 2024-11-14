package fightGame.controller;

import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import fightGame.model.GameBoard;
import gamePlayers.*;
import gamePlayers.fighters.Unit;
import gamePlayers.util.*;
public class GameBoardAdapterToTable extends AbstractTableModel implements ModelListener{
    private GameBoard gameBoard;

    public GameBoardAdapterToTable(GameBoard gameBoard){
        this.gameBoard = gameBoard;
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
        Set<AbstractGameEntity> entities = gameBoard.getEntitiesAt(new Position(rowIndex,columnIndex));
        String chaine = "";
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
                    return new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/unit.jpg");
                case PELLET:
                    return new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/pellet.png");
                case PROJECTILE:
                    return new ImageIcon("livraison/src/fightGame/src/fightGame/view/img/projectile.png");
                default:
                    break;
            }
        }else{
            for (AbstractGameEntity abstractGameEntity : entities) {
                if(abstractGameEntity.getType().equals(EntityType.UNIT)){
                    chaine += ((Unit)abstractGameEntity).getName()+"_";

                }else{
                    chaine += abstractGameEntity.getType().toString()+"_";

                }
            }
        }
        return chaine;
       
    }
    @Override
    public void update(ListenableModel source) {
        super.fireTableDataChanged();
    }
    
}
