package fightGame.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fightGame.UnchangeableSettings;
import gamePlayers.AbstractGameEntity;
import gamePlayers.objects.Weapon;
import gamePlayers.util.Direction;
import gamePlayers.util.EntityType;
import gamePlayers.util.Position;

public class GameBoardProxy implements GameBoardInterface{

    private GameBoard gameBoard;
    private FightGamePlayer player;

    public GameBoardProxy(GameBoard gameBoard, FightGamePlayer player){
        this.gameBoard = gameBoard;
        this.player = player;
    }

    @Override
    public AbstractGameEntity getEntityAt(Position position) {
        AbstractGameEntity entity = gameBoard.getEntityAt(position);


        if(entity == null) return null;

        switch (entity.getType()) {
            case BOMB:
                if(UnchangeableSettings.BOMB_VISIBILITY == false){
                    if(player.equals(entity.getOwner())) return entity;

                    return null;
                }
                
                break;

            case MINE:
            
                if(UnchangeableSettings.MINE_VISIBILITY == false){
                    if(player.equals(entity.getOwner())) return entity;

                    return null;
                }
                
                break;
        
            default:
                break;
        }
        
        return entity;
        
    }

    @Override
    public boolean moveEntity(Position oldPosition, Direction direction) {

        AbstractGameEntity entity = this.getEntityAt(oldPosition);

        if(entity == null) return false;

        return gameBoard.moveEntity(oldPosition, direction);
    }

    public FightGamePlayer getNextPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNextPlayer'");
    }

    @Override
    public List<Action> getActions(FightGamePlayer player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getActions'");
    }

    @Override
    public boolean performAction(Action action, FightGamePlayer player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performAction'");
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public FightGamePlayer getPlayer() {
        return player;
    }

    @Override
    public int getNextPlayerIndex() {
        return gameBoard.getNextPlayerIndex();
    }

    public GameBoard getGameBoard(List<FightGamePlayer> players){

        Map<Position, AbstractGameEntity> entities = new HashMap<>();
        for(Position position: gameBoard.getEntities().keySet()){
            entities.put(position, this.getEntityAt(position));
        }

        return new GameBoard(gameBoard, players, entities);
    }

    

}
