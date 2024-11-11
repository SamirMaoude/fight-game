package fightGame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamePlayers.AbstractGameEntity;
import gamePlayers.util.AbtractListenableModel;
import gamePlayers.util.Direction;
import gamePlayers.util.EntityType;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;
import gamePlayers.util.Position;

public class GameBoard extends AbtractListenableModel implements GameBoardInterface, ModelListener {

    private int rows;
    private int cols;
    private int nextPlayerIndex = 0;

    private Map<Position, AbstractGameEntity> entities = new HashMap<>();
    private List<FightGamePlayer> players = new ArrayList<>();

    public GameBoard(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
    }

    public GameBoard(int rows, int cols, List<FightGamePlayer> players){
        this(rows, cols);
        this.players = players;
    }

    public GameBoard(GameBoard gameBoard, List<FightGamePlayer> players){
        this(gameBoard.getRows(), gameBoard.getCols(), players);
    }

    public GameBoard(GameBoard gameBoard, List<FightGamePlayer> players, Map<Position, AbstractGameEntity> entities){
        this(gameBoard, players);
        this.entities = entities;
    }
    
    public Map<Position, AbstractGameEntity> getEntities() {
        return entities;
    }

    public boolean addEntity(AbstractGameEntity entity, Position position){

        if(!isValidPosition(position)){
            return false;
        }
        
        entities.put(position, entity);

        return true;
    }

    @Override
    public void update(ListenableModel source) {

        notifyModelListeners();
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public AbstractGameEntity getEntityAt(Position position) {
        return entities.get(position);
    }

    @Override
    public boolean moveEntity(Position oldPosition, Direction direction) {
        
        AbstractGameEntity entity = entities.get(oldPosition);

        if(entity == null) return false;

        Position newPosition = new Position(oldPosition);

        switch (direction) {
            case LEFT:
                newPosition.moveLeft();
                break;
            
            case RIGHT:
                newPosition.moveRight();
                break;

            case BOTTOM:
                newPosition.moveBottom();
                break;

            case TOP:
                newPosition.moveTop();
                break;
        
            default:
                return false;
        }

        if(!isValidPosition(newPosition)){
            return false;
        }

        entities.put(oldPosition, null);

        entities.put(newPosition, entity);

        entity.setPosition(newPosition);

        return true;


    }

    public FightGamePlayer getNextPlayer() {
        return players.get(nextPlayerIndex % players.size());
    }

    @Override
    public List<Action> getActions(FightGamePlayer player) {

        List<Action> actions = new ArrayList<>();

        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'getActions'");

        return actions;
    }

    @Override
    public boolean performAction(Action action, FightGamePlayer player) {

        nextPlayerIndex = (nextPlayerIndex + 1) % players.size();

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performAction'");
    }


    private boolean isValidPosition(Position position){
        int r = position.getRow();
        int c = position.getCol();

        if(!((0 <= r &&  r < rows)  && (0 < c && c < cols))) return false;

        // Position occupée;
        if(entities.get(position) != null){
            AbstractGameEntity entity = entities.get(position);

            if(entity.getType() == EntityType.UNIT) return false;
        }

        return true;

       
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    @Override
    public int getNextPlayerIndex() {

        while(!players.get(nextPlayerIndex).getUnit().isAlive()){
            nextPlayerIndex = (nextPlayerIndex + 1) % players.size();
        }

        return nextPlayerIndex;
    }
    
}
