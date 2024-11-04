package fightGame.model;

import java.util.List;

import gamePlayers.AbstractGameEntity;
import gamePlayers.objects.Weapon;
import gamePlayers.util.Direction;
import gamePlayers.util.EntityType;
import gamePlayers.util.Position;

public class GameBoardProxy implements GameBoardInterface{

    private GameBoard gameBoard;
    private Player player;

    public GameBoardProxy(GameBoard gameBoard, Player player){
        this.gameBoard = gameBoard;
        this.player = player;
    }

    @Override
    public AbstractGameEntity getEntityAt(Position position) {
        AbstractGameEntity entity = gameBoard.getEntityAt(position);

        if(entity == null) return null;

        if(entity.getType() == EntityType.UNIT){
            if(entity.equals(player.getUnit())) return entity;
        }
        
        // if(entity instanceof Weapon){
        //     entity;
        // }
    }

    @Override
    public boolean moveEntity(Position oldPosition, Direction direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveEntity'");
    }

    @Override
    public Player getNextPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNextPlayer'");
    }

    @Override
    public List<Action> getActions(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getActions'");
    }

    @Override
    public boolean performAction(Action action, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performAction'");
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Player getPlayer() {
        return player;
    }

    

}
