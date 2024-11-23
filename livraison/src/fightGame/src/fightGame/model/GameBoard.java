package fightGame.model;

import java.util.HashSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fightGame.UnchangeableSettings;
import fightGame.model.strategy.GameBordInitFillStrategy;
import gamePlayers.AbstractGameEntity;
import gamePlayers.fighters.Unit;
import gamePlayers.objects.Bomb;
import gamePlayers.objects.Mine;
import gamePlayers.objects.Pellet;
import gamePlayers.util.AbtractListenableModel;
import gamePlayers.util.Action;
import gamePlayers.util.Direction;
import gamePlayers.util.EntityType;
import gamePlayers.util.Player;
import gamePlayers.util.Position;

public class GameBoard extends AbtractListenableModel implements GameBoardInterface, Cloneable, Serializable{
    private static final long serialVersionUID = 1L; 
    private int rows;
    private int cols;
    private int nextPlayerIndex = 0;

    private GameBordInitFillStrategy fillStrategy;
    private Map<Position, Set<AbstractGameEntity>> entities = new HashMap<>();
    private List<FightGamePlayer> players = new ArrayList<>();
    private List<Position> impactedPositionsByBomb = new ArrayList<>();
    private List<Position> impactedPositionsByMine = new ArrayList<>();
    private List<Position> impactedPositionsByProjectile = new ArrayList<>();
    private List<Position> lastMove = new ArrayList<>();
    private FightGameAction lastActionPlayed = null;
    private FightGamePlayer precedentPlayer = null;

    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public GameBoard (int rows, int cols, GameBordInitFillStrategy strategy){
        this(rows, cols);
        this.fillStrategy = strategy;
    }

    public GameBoard(int rows, int cols, List<FightGamePlayer> players) {
        this(rows, cols);
        this.players = players;
    }

    public void setPlayers(List<FightGamePlayer> players) {
        this.players = players;
    }

    public GameBoard(GameBoard gameBoard, List<FightGamePlayer> players) {
        this(gameBoard.getRows(), gameBoard.getCols(), players);
    }

    public GameBoard(GameBoard gameBoard, List<FightGamePlayer> players,
            Map<Position, Set<AbstractGameEntity>> entities) {
        this(gameBoard, players);
        this.entities = entities;
    }

    public GameBoard(GameBoard gameBoard){
        List<FightGamePlayer> players = new ArrayList<>();
        for(int playerIndex = 0; playerIndex < gameBoard.getNbPlayers(); playerIndex++){
            players.add(new FightGamePlayer(null, playerIndex));
        }

        Map<Position, Set<AbstractGameEntity>> entities = new HashMap<>();
        for(Position position: gameBoard.getEntities().keySet()){
            Set<AbstractGameEntity> positionEntities = gameBoard.getEntitiesAt(position);
            Set<AbstractGameEntity> copy = new HashSet<>();
            for(AbstractGameEntity entity: positionEntities){
                try {
                    AbstractGameEntity clone = entity.clone();
                    copy.add(clone);
                    if(entity.getType()==EntityType.UNIT){
                        Unit unit = (Unit)clone;
                        int playerIndex = unit.getOwner().getPlayerIndex();
                        players.get(playerIndex).setUnit(unit);
                        unit.setOwner(players.get(playerIndex));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                
                
            }
            entities.put(position, copy);
        }


        for(int playerIndex = 0; playerIndex < gameBoard.getNbPlayers(); playerIndex++){
            FightGamePlayer player =  players.get(playerIndex);
            player.setGameBoardProxy(new GameBoardProxy(this, player));;
        }

        this.rows = gameBoard.getRows();
        this.cols = gameBoard.getCols();
        this.entities = entities;
        this.players = players;
        this.nextPlayerIndex = gameBoard.getNextPlayer().getPlayerIndex();

    }

    public Map<Position, Set<AbstractGameEntity>> getEntities() {
        return entities;
    }
    public void setStrategy(GameBordInitFillStrategy strategy){
        this.fillStrategy = strategy;
    }
    public void fillGameBoard(){
        this.fillStrategy.fillGrid(this);
    }

    public void addPlayer(FightGamePlayer player){
        this.players.add(player);
    }
    public boolean addEntity(AbstractGameEntity entity, Position position) {

        if (!isValidMove(position)) {
            return false;
        }

        Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);
        positionEntities.add(entity);
        entities.put(position, positionEntities);

        return true;
    }

    @Override
    public Set<AbstractGameEntity> getEntitiesAt(Position position) {
        return entities.getOrDefault(position, new HashSet<>());
    }

    @Override
    public boolean moveUnit(Position oldPosition, Direction direction) {

        Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(oldPosition);

        if (positionEntities.isEmpty())
            return false;

        Position newPosition = new Position(oldPosition);

        Unit unit = null;

        for (AbstractGameEntity entity : positionEntities) {
            if (entity.getType() == EntityType.UNIT) {
                unit = (Unit) entity;
                break;
            }
        }

        if (unit == null)
            return false;

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

        if (!isValidMove(newPosition)) {
            return false;
        }

        this.getEntitiesAt(oldPosition).remove(unit);

        Set<AbstractGameEntity> newPositionEntities = this.getEntitiesAt(newPosition);
        newPositionEntities.add(unit);
        entities.put(newPosition, newPositionEntities);

        unit.setPosition(newPosition);

        for (AbstractGameEntity entity : new HashSet<>(newPositionEntities)) {

            switch (entity.getType()) {
                case MINE:
                    detonateMineAt(newPosition, (Mine) entity);
                    break;
                case PELLET:
                    unit.takePellet((Pellet) entity);
                    newPositionEntities.remove(entity);
                    break;
                default:
                    break;
            }

        }
        lastMove.add(oldPosition);
        lastMove.add(newPosition);

        return true;
    }

    public FightGamePlayer getNextPlayer() {
        return players.get(nextPlayerIndex % players.size());
    }

    public List<Action> getActions(FightGamePlayer player) {

        List<Action> actions = new ArrayList<>();
        

        Unit unit = player.getUnit();
        Position position = unit.getPosition();

        Position copyPosition = new Position(position);
        copyPosition.moveRight();
        if (isValidPosition(copyPosition)) {
            // MOVE_UNIT_TO_RIGHT,
            if (isValidMove(copyPosition))
                actions.add(new FightGameAction(FightGameActionType.MOVE_UNIT_TO_RIGHT));

            // USE_MINE_AT_RIGHT,
            if (unit.hasMines())
                actions.add(new FightGameAction(FightGameActionType.USE_MINE_AT_RIGHT));

            // USE_BOMB_AT_RIGHT,
            if (unit.hasBombs())
                actions.add(new FightGameAction(FightGameActionType.USE_BOMB_AT_RIGHT));

            // USE_PROJECTILE_AT_RIGHT,
            if (unit.hasProjectiles())
                actions.add(new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_RIGHT));

        }

        // MOVE_UNIT_TO_LEFT,
        copyPosition = new Position(position);
        copyPosition.moveLeft();
        if (isValidPosition(copyPosition)) {
            // MOVE_UNIT_TO_LEFT,
            if (isValidMove(copyPosition))
                actions.add(new FightGameAction(FightGameActionType.MOVE_UNIT_TO_LEFT));

            // USE_MINE_AT_LEFT,
            if (unit.hasMines())
                actions.add(new FightGameAction(FightGameActionType.USE_MINE_AT_LEFT));

            // USE_BOMB_AT_LEFT,
            if (unit.hasBombs())
                actions.add(new FightGameAction(FightGameActionType.USE_BOMB_AT_LEFT));

            // USE_PROJECTILE_AT_LEFT,
            if (unit.hasProjectiles())
                actions.add(new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_LEFT));
        }

        // MOVE_UNIT_TO_BOTTOM,
        copyPosition = new Position(position);
        copyPosition.moveBottom();
        if (isValidPosition(copyPosition)) {
            // MOVE_UNIT_TO_BOTTOM,
            if (isValidMove(copyPosition))
                actions.add(new FightGameAction(FightGameActionType.MOVE_UNIT_TO_BOTTOM));

            // USE_MINE_AT_BOTTOM,
            if (unit.hasMines())
                actions.add(new FightGameAction(FightGameActionType.USE_MINE_AT_BOTTOM));

            // USE_BOMB_AT_BOTTOM,
            if (unit.hasBombs())
                actions.add(new FightGameAction(FightGameActionType.USE_BOMB_AT_BOTTOM));

            // USE_PROJECTILE_AT_BOTTOM,
            if (unit.hasProjectiles())
                actions.add(new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_BOTTOM));
        }

        // MOVE_UNIT_TO_TOP,
        copyPosition = new Position(position);
        copyPosition.moveTop();
        if (isValidPosition(copyPosition)) {
            // MOVE_UNIT_TO_TOP,
            if (isValidMove(copyPosition))
                actions.add(new FightGameAction(FightGameActionType.MOVE_UNIT_TO_TOP));

            // USE_MINE_AT_TOP,
            if (unit.hasMines())
                actions.add(new FightGameAction(FightGameActionType.USE_MINE_AT_TOP));

            // USE_BOMB_AT_TOP,
            if (unit.hasBombs())
                actions.add(new FightGameAction(FightGameActionType.USE_BOMB_AT_TOP));

            // USE_PROJECTILE_AT_TOP,
            if (unit.hasProjectiles())
                actions.add(new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_TOP));
        }

        if (unit.getEnergy() > UnchangeableSettings.SHIELD_COST && !unit.getShieldActivated())
            actions.add(new FightGameAction(FightGameActionType.ACTIVATE_SHIELD));

        actions.add(new FightGameAction(FightGameActionType.NOTHING));
        return actions;
    }

    public boolean performAction(FightGameAction action, FightGamePlayer player) {

        Unit unit = player.getUnit();
        Position unitPosition = unit.getPosition();
        reinitializeFromLastAction();

        switch (action.TYPE) {
            case MOVE_UNIT_TO_RIGHT:            
                if (!this.moveUnit(unitPosition, Direction.RIGHT))
                    return false;
                break;

            case MOVE_UNIT_TO_LEFT:
                if (!this.moveUnit(unitPosition, Direction.LEFT))
                    return false;
                break;

            case MOVE_UNIT_TO_BOTTOM:
                if (!this.moveUnit(unitPosition, Direction.BOTTOM))
                    return false;
                break;

            case MOVE_UNIT_TO_TOP:
                if (!this.moveUnit(unitPosition, Direction.TOP))
                    return false;
                break;

            case USE_MINE_AT_LEFT: {
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveLeft();
                this.addEntity(mine, position);
                break;
            }

            case USE_MINE_AT_RIGHT: {
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveRight();
                this.addEntity(mine, position);
                break;
            }

            case USE_MINE_AT_BOTTOM: {
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveBottom();
                this.addEntity(mine, position);
                break;
            }

            case USE_MINE_AT_TOP: {
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveTop();
                this.addEntity(mine, position);
                break;
            }

            case USE_MINE_AT_TOP_RIGHT: {
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveTop();
                position.moveRight();
                this.addEntity(mine, position);
                break;
            }

            case USE_MINE_AT_TOP_LEFT: {
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveTop();
                position.moveLeft();
                this.addEntity(mine, position);
                break;
            }

            case USE_MINE_AT_BOTTOM_LEFT: {
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveBottom();
                position.moveLeft();
                this.addEntity(mine, position);
                break;
            }

            case USE_MINE_AT_BOTTOM_RIGHT: {
                Mine mine = unit.useMine();
                Position position = new Position(unitPosition);
                position.moveBottom();
                position.moveRight();
                this.addEntity(mine, position);
                break;
            }

            case USE_BOMB_AT_LEFT: {
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveLeft();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_RIGHT: {
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveRight();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_BOTTOM: {
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveBottom();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_TOP: {
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveTop();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_TOP_RIGHT: {
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveTop();
                position.moveRight();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_TOP_LEFT: {
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveTop();
                position.moveLeft();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_BOTTOM_LEFT: {
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveBottom();
                position.moveLeft();
                this.addEntity(bomb, position);
                break;
            }

            case USE_BOMB_AT_BOTTOM_RIGHT: {
                Bomb bomb = unit.useBomb();
                Position position = new Position(unitPosition);
                position.moveBottom();
                position.moveRight();
                this.addEntity(bomb, position);
                break;
            }

            case USE_PROJECTILE_AT_RIGHT: {
                unit.useProjectile();
                int row = unitPosition.getRow();
                int col = unitPosition.getCol();
                int maxDistance = col + UnchangeableSettings.PROJECTILE_SCOPE;

                col++;
                while (col < Math.min(maxDistance+1, this.getCols())) {
                    Position position = new Position(row, col);

                    if (!this.projectileEffect(position))
                        break;

                    col++;

                }
                break;

            }

            case USE_PROJECTILE_AT_LEFT: {
                unit.useProjectile();
                int row = unitPosition.getRow();
                int col = unitPosition.getCol();
                int maxDistance = col - UnchangeableSettings.PROJECTILE_SCOPE;

                col--;
                while (col >= Math.max(maxDistance, 0)) {
                    Position position = new Position(row, col);

                    if (!this.projectileEffect(position))
                        break;

                    col--;

                }
                break;
            }

            case USE_PROJECTILE_AT_TOP: {
                unit.useProjectile();
                int row = unitPosition.getRow();
                int col = unitPosition.getCol();
                int maxDistance = row - UnchangeableSettings.PROJECTILE_SCOPE;

                row--;
                while (row >= Math.max(maxDistance, 0)) {
                    Position position = new Position(row, col);

                    if (!this.projectileEffect(position))
                        break;

                    row--;

                }
                break;
            }

            case USE_PROJECTILE_AT_BOTTOM: {
                unit.useProjectile();
                int row = unitPosition.getRow();
                int col = unitPosition.getCol();
                int maxDistance = row + UnchangeableSettings.PROJECTILE_SCOPE;

                row++;
                while (row < Math.min(maxDistance+1, this.getRows())) {
                    Position position = new Position(row, col);

                    if (!this.projectileEffect(position))
                        break;

                    row++;
                }

                break;

            }

            case NOTHING:

                break;

            case ACTIVATE_SHIELD:
                unit.receiveDamage(UnchangeableSettings.SHIELD_COST);
                unit.setShieldActivated(true);
                unit.setShieldTimer(UnchangeableSettings.SHIELD_TIMER);
                break;

            default:
                break;
        }

        this.updateEntities();
        
        getNextPlayerIndex();

        lastActionPlayed = action;
        precedentPlayer = player;

        this.notifyModelListeners();

        return true;
    }

    private boolean isValidMove(Position position) {
        if (!isValidPosition(position))
            return false;

        // Position occupée par une unité
        if (!this.getEntitiesAt(position).isEmpty()) {
            Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);

            for (AbstractGameEntity entity : positionEntities) {
                if (entity.getType() == EntityType.UNIT)
                    return false;
            }

        }

        return true;
    }

    private boolean isValidPosition(Position position) {
        int r = position.getRow();
        int c = position.getCol();

        if (!((0 <= r && r < rows) && (0 <= c && c < cols)))
            return false;

        // Position occupée par un mur;
        if (!this.getEntitiesAt(position).isEmpty()) {
            Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);

            for (AbstractGameEntity entity : positionEntities) {
                if (entity.getType() == EntityType.WALL)
                    return false;
            }

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
        nextPlayerIndex = (nextPlayerIndex + 1) % players.size();
        if(playersRemaining()==0) return nextPlayerIndex;
        boolean ok = false;
        while (!ok) {
            Unit nextUnit = players.get(nextPlayerIndex).getUnit();
            if(nextUnit == null){
                nextPlayerIndex = (nextPlayerIndex + 1) % players.size();
                continue;
            }
            else if(!nextUnit.isAlive()){
                nextPlayerIndex = (nextPlayerIndex + 1) % players.size();
                continue;
            }

            ok = true;
            
        }
        
        return nextPlayerIndex;
    }

    public void updateEntities() {

        for (Position position : this.entities.keySet()) {

            Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);

            for (AbstractGameEntity entity : new HashSet<>(positionEntities)) {

                switch (entity.getType()) {
                    case BOMB: {
                        Bomb bomb = (Bomb) entity;
                        if (bomb.getTimeBeforeExplosion() == 0) {
                            detonateBombAt(position, bomb);
                        } else {
                            bomb.decreaseTime();
                        }

                        break;
                    }
                    case UNIT: {
                        Unit unit = (Unit) entity;
                        if (unit.getShieldActivated()) {
                            int shieldTimer = unit.getShieldTimer();
                            if (shieldTimer > 0) {
                                unit.setShieldTimer(shieldTimer - 1);
                            } else {
                                unit.setShieldActivated(false);
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }

            }

        }
        
    }

    public void detonateBombAt(Position position, Bomb currentBomb) {

        Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);
        positionEntities.remove(currentBomb);

        int[][] DELTAS_DIRECTIONS = {
                { 0, 1 },
                { 0, -1 },
                { 1, 0 },
                { -1, 0 },
                { -1, -1 },
                { -1, 1 },
                { 1, -1 },
                { 1, 1 },
                { 0, 0 }
        };

        int row = position.getRow();
        int col = position.getCol();
        for (int i = 0; i < 9; i++) {

            int x = row + DELTAS_DIRECTIONS[i][0];
            int y = col + DELTAS_DIRECTIONS[i][1];

            if ((x >= 0 && x < this.getRows()) && (y >= 0 && y < this.getCols())) {
                Position impactedPosition = new Position(x, y);

                Set<AbstractGameEntity> impactedPositionEntities = this.getEntitiesAt(impactedPosition);

                Set<AbstractGameEntity> copy = new HashSet<>(impactedPositionEntities);

                for (AbstractGameEntity entity : copy) {

                    switch (entity.getType()) {
                        case BOMB:
                            Bomb bomb = (Bomb) entity;
                            detonateBombAt(impactedPosition, bomb);
                            break;
                        case MINE:
                            detonateMineAt(impactedPosition, (Mine) entity);
                            break;
                        case UNIT:
                            Unit unit = (Unit) entity;
                            unit.receiveDamage(UnchangeableSettings.BOMB_DAMAGE);
                            if (!unit.isAlive())
                                impactedPositionEntities.remove(entity);
                            break;

                        default:
                            break;
                    }

                }

                impactedPositionsByBomb.add(impactedPosition);

            }
        }

    }

    public void detonateMineAt(Position position, Mine currentMine) {

        Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);
        positionEntities.remove(currentMine);

        Set<AbstractGameEntity> copy = new HashSet<>(positionEntities);

        for (AbstractGameEntity entity : copy) {

            switch (entity.getType()) {
                case BOMB:
                    detonateBombAt(position, (Bomb) entity);
                    break;
                case MINE:
                    Mine mine = (Mine) entity;
                    detonateMineAt(position, mine);
                    break;
                case UNIT:
                    Unit unit = (Unit) entity;
                    unit.receiveDamage(UnchangeableSettings.BOMB_DAMAGE);
                    break;

                default:
                    break;
            }

        }

        impactedPositionsByMine.add(position);

    }

    public boolean projectileEffect(Position position) {

        

        Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);

        for (AbstractGameEntity entity : new HashSet<>(positionEntities)) {
            if (entity.getType() == EntityType.WALL) {
                return false;
            }

            if (entity.getType() == EntityType.UNIT) {
                Unit x = (Unit) entity;

                x.receiveDamage(UnchangeableSettings.PROJECTILE_DAMAGE);
                if (!x.isAlive())
                    positionEntities.remove(entity);
            }
        }
        impactedPositionsByProjectile.add(position);
        return true;

    }

    public void run() {
        while (!isGameOver()) {
            try {
                FightGamePlayer player = this.getNextPlayer();

                Thread.sleep(5000);
                Action action = player.play();
                System.out.println(player + " played " + action);
                performAction((FightGameAction) action, player);
                for(Player p: players){
                    if(p.getUnit().isAlive())System.out.println(p+" energy remaining is "+p.getUnit().getEnergy());
                    else System.out.println(p+" is dead");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        
    }

    public int playersRemaining() {

        int nb = 0;
        for (FightGamePlayer player : players) {
            if(player.getUnit() == null) continue;
            if (player.getUnit().isAlive()) {
                nb++;
            }
        }
        return nb;
    }

    public List<FightGamePlayer> getPlayers() {
        return players;
    }

    public void reinitializeFromLastAction(){
        impactedPositionsByProjectile.clear();
        impactedPositionsByBomb.clear();
        impactedPositionsByMine.clear();
        lastMove.clear();
    }
    
    public List<Position> getImpactedPositionsByBomb() {
        return impactedPositionsByBomb;
    }

    public List<Position> getImpactedPositionsByMine() {
        return impactedPositionsByMine;
    }

    public List<Position> getLastMove() {
        return lastMove;
    }

    public List<Position> getImpactedPositionsByProjectile() {
        return impactedPositionsByProjectile;
    }

    public boolean isGameOver(){
        return playersRemaining() <= 1;
    }

    public int getNbPlayers(){
        return this.players.size();
    }

    public FightGameAction getLastActionPlayed() {
        return lastActionPlayed;
    }

    public FightGamePlayer getPrecedentPlayer() {
        return precedentPlayer;
    }

    

}
