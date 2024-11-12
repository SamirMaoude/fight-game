package fightGame.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fightGame.UnchangeableSettings;
import gamePlayers.AbstractGameEntity;
import gamePlayers.fighters.Unit;
import gamePlayers.util.Action;
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
    public Set<AbstractGameEntity> getEntitiesAt(Position position) {
        Set<AbstractGameEntity> positionEntities = gameBoard.getEntitiesAt(position);


        if(positionEntities.isEmpty()) return new HashSet<>();

        Set<AbstractGameEntity> copy = new HashSet<>(positionEntities);

        for (AbstractGameEntity entity : positionEntities) {
            switch (entity.getType()) {
                case BOMB:
                    if(UnchangeableSettings.BOMB_VISIBILITY == false){
                        if(!player.equals(entity.getOwner())) copy.remove(entity);
                    }
                    
                    break;
    
                case MINE:
                
                    if(UnchangeableSettings.MINE_VISIBILITY == false){
                        if(!player.equals(entity.getOwner())) copy.remove(entity);
                    }
                    
                    break;
            
                default:
                    break;
            }
            
        }
        
        return copy;
        
    }

    @Override
    public boolean moveUnit(Position oldPosition, Direction direction) {

        Set<AbstractGameEntity> oldPositionEntites = this.getEntitiesAt(oldPosition);

        if(oldPositionEntites.isEmpty()) return false;

        Unit unit = null;
        for(AbstractGameEntity entity: oldPositionEntites){
            if(entity.getType()==EntityType.UNIT){
                unit = (Unit)entity;
                break;
            }
        }

        if(!unit.getOwner().equals(player)) return false;

        return gameBoard.moveUnit(oldPosition, direction);
    }

    public FightGamePlayer getNextPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNextPlayer'");
    }

    public List<Action> getActions() {
        return gameBoard.getActions(player);
    }

    public boolean performAction(FightGameAction action) {
        return this.gameBoard.performAction(action, player);
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

        Map<Position, Set<AbstractGameEntity>> entities = new HashMap<>();
        for(Position position: gameBoard.getEntities().keySet()){
            Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);
            Set<AbstractGameEntity> copy = new HashSet<>();
            for(AbstractGameEntity entity: positionEntities){
                copy.add(entity.clone());
            }
            entities.put(position, copy);
        }

        return new GameBoard(gameBoard, players, entities);
    }

    

}
