package fightGame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fightGame.UnchangeableSettings;
import gamePlayers.AbstractGameEntity;
import gamePlayers.fighters.Unit;
import gamePlayers.objects.Bomb;
import gamePlayers.objects.Mine;
import gamePlayers.util.AbtractListenableModel;
import gamePlayers.util.Action;
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
    public boolean performAction(FightGameAction action, FightGamePlayer player) {

        Unit unit = player.getUnit();
        Position unitPosition = unit.getPosition();

        switch (action.TYPE) {
            case MOVE_UNIT_TO_RIGHT:
                if(!this.moveEntity(unitPosition, Direction.RIGHT)) return false;
                break;

            case MOVE_UNIT_TO_LEFT:
                if(!this.moveEntity(unitPosition, Direction.LEFT)) return false;
                break;

            case MOVE_UNIT_TO_BOTTOM:
                if(!this.moveEntity(unitPosition, Direction.BOTTOM)) return false;
                break;

            case MOVE_UNIT_TO_TOP:
                if(!this.moveEntity(unitPosition, Direction.TOP)) return false;
                break;

            case USE_MINE_AT_LEFT:{
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveLeft();
                this.addEntity(mine, position);
                break;
            }
                

            case USE_MINE_AT_RIGHT:{
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveRight();
                this.addEntity(mine, position);
                break;
            }
                
            case USE_MINE_AT_BOTTOM:{
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveBottom();
                this.addEntity(mine, position);
                break;
            }

            case USE_MINE_AT_TOP:{
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveTop();
                this.addEntity(mine, position);
                break;
            }


            case USE_MINE_AT_TOP_RIGHT:{
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveTop();
                position.moveRight();
                this.addEntity(mine, position);
                break;
            }

            case USE_MINE_AT_TOP_LEFT:{
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveTop();
                position.moveLeft();
                this.addEntity(mine, position);
                break;
            }

            case USE_MINE_AT_BOTTOM_LEFT:{
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveBottom();
                position.moveLeft();
                this.addEntity(mine, position);
                break;
            }

            case USE_MINE_AT_BOTTOM_RIGHT:{
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveBottom();
                position.moveRight();
                this.addEntity(mine, position);
                break;
            }

            case USE_BOMB_AT_LEFT:{
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveLeft();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_RIGHT:{
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveRight();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_BOTTOM:{
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveBottom();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_TOP:{
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveTop();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_TOP_RIGHT:{
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveTop();
                position.moveRight();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_TOP_LEFT:{
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveTop();
                position.moveLeft();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_BOTTOM_LEFT:{
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveBottom();
                position.moveLeft();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_BOTTOM_RIGHT:{
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveBottom();
                position.moveRight();
                this.addEntity(bomb, position);
                break;
            }

            case USE_PROJECTILE_AT_RIGHT:{
                int row = unitPosition.getRow();
                int col = unitPosition.getCol();
                int maxDistance = col + UnchangeableSettings.PROJECTILE_SCOPE;

                col++;
                while(col < Math.min(maxDistance, this.getCols())){
                    Position position = new Position(row, col);

                    AbstractGameEntity entity = this.getEntityAt(position);

                    if(entity.getType()==EntityType.WALL) break;

                    if(entity.getType()==EntityType.UNIT){
                        Unit x = (Unit)entity;

                        x.receiveDamage(UnchangeableSettings.PROJECTILE_DAMAGE);
                    }

                    col++;
                    
                }
                
            }

            case USE_PROJECTILE_AT_LEFT:{
                int row = unitPosition.getRow();
                int col = unitPosition.getCol();
                int maxDistance = col - UnchangeableSettings.PROJECTILE_SCOPE;

                col--;
                while(col > Math.max(maxDistance, -1)){
                    Position position = new Position(row, col);

                    AbstractGameEntity entity = this.getEntityAt(position);

                    if(entity.getType()==EntityType.WALL) break;

                    if(entity.getType()==EntityType.UNIT){
                        Unit x = (Unit)entity;

                        x.receiveDamage(UnchangeableSettings.PROJECTILE_DAMAGE);
                    }

                    col--;
                    
                }
                
            }

            case USE_PROJECTILE_AT_TOP:{
                int row = unitPosition.getRow();
                int col = unitPosition.getCol();
                int maxDistance = row - UnchangeableSettings.PROJECTILE_SCOPE;

                row--;
                while(col > Math.max(maxDistance, -1)){
                    Position position = new Position(row, col);

                    AbstractGameEntity entity = this.getEntityAt(position);

                    if(entity.getType()==EntityType.WALL) break;

                    if(entity.getType()==EntityType.UNIT){
                        Unit x = (Unit)entity;

                        x.receiveDamage(UnchangeableSettings.PROJECTILE_DAMAGE);
                    }

                    row--;
                    
                }
                
            }

            case USE_PROJECTILE_AT_BOTTOM:{
                int row = unitPosition.getRow();
                int col = unitPosition.getCol();
                int maxDistance = row + UnchangeableSettings.PROJECTILE_SCOPE;

                row++;
                while(row > Math.max(maxDistance, this.getRows())){
                    Position position = new Position(row, col);

                    AbstractGameEntity entity = this.getEntityAt(position);

                    if(entity.getType()==EntityType.WALL) break;

                    if(entity.getType()==EntityType.UNIT){
                        Unit x = (Unit)entity;

                        x.receiveDamage(UnchangeableSettings.PROJECTILE_DAMAGE);
                    }

                    row++;
                    
                }
                
            }

            case NOTHING:

                break;
            
            case ACTIVATE_SHIELD:

                break;

            

        
            default:
                break;
        }

        this.updateEntities();
        getNextPlayer();

        return true;
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

    public void updateEntities(){
        
    }
    
}
