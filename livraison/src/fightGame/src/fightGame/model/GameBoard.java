package fightGame.model;

import java.util.HashSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fightGame.UnchangeableSettings;
import fightGame.model.strategy.GameBordInitFillStrategy;
import gamePlayers.AbstractGameEntity;
import gamePlayers.fighters.Unit;
import gamePlayers.objects.*;
import gamePlayers.util.*;

/**
 * Represents the game board for a fight game. It manages the grid of entities,
 * players, and actions
 * performed on the game board. It also supports cloning the game board and
 * initializing it with different strategies.
 * Extends AbstractListenableModel to support listeners and implements
 * GameBoardInterface for game board operations.
 */
public class GameBoard extends AbtractListenableModel implements GameBoardInterface, Cloneable, Serializable {
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
    private int nbBoringMove = 0;

    /**
     * Constructs a GameBoard with the specified number of rows and columns.
     * 
     * @param rows the number of rows in the game board
     * @param cols the number of columns in the game board
     */
    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Constructs a GameBoard with the specified number of rows, columns, and
     * initial fill strategy.
     * 
     * @param rows     the number of rows in the game board
     * @param cols     the number of columns in the game board
     * @param strategy the strategy for filling the game board
     */
    public GameBoard(int rows, int cols, GameBordInitFillStrategy strategy) {
        this(rows, cols);
        this.fillStrategy = strategy;
    }

    /**
     * Constructs a GameBoard with the specified number of rows, columns, and a list
     * of players.
     * 
     * @param rows    the number of rows in the game board
     * @param cols    the number of columns in the game board
     * @param players the list of players in the game
     */
    public GameBoard(int rows, int cols, List<FightGamePlayer> players) {
        this(rows, cols);
        this.players = players;
    }

    /**
     * Sets the players for the game board.
     * 
     * @param players the list of players to set
     */
    public void setPlayers(List<FightGamePlayer> players) {
        this.players = players;
    }

    /**
     * Constructs a new GameBoard by copying the properties from another game board
     * and a list of players.
     * 
     * @param gameBoard the game board to copy
     * @param players   the list of players to initialize
     */
    public GameBoard(GameBoard gameBoard, List<FightGamePlayer> players) {
        this(gameBoard.getRows(), gameBoard.getCols(), players);
    }

    /**
     * Constructs a new GameBoard by copying the properties from another game board,
     * a list of players,
     * and the current entities on the game board.
     * 
     * @param gameBoard the game board to copy
     * @param players   the list of players to initialize
     * @param entities  the current entities on the game board to copy
     */
    public GameBoard(GameBoard gameBoard, List<FightGamePlayer> players,
            Map<Position, Set<AbstractGameEntity>> entities) {
        this(gameBoard, players);
        this.entities = entities;
    }

    /**
     * Creates a new GameBoard by cloning the properties and entities from another
     * game board.
     * The players and entities are also cloned and reassigned.
     * 
     * @param gameBoard the game board to clone
     */
    public GameBoard(GameBoard gameBoard) {
        List<FightGamePlayer> players = new ArrayList<>();
        for (int playerIndex = 0; playerIndex < gameBoard.getNbPlayers(); playerIndex++) {
            players.add(new FightGamePlayer(null, playerIndex));
        }

        Map<Position, Set<AbstractGameEntity>> entities = new HashMap<>();
        for (Position position : gameBoard.getEntities().keySet()) {
            Set<AbstractGameEntity> positionEntities = gameBoard.getEntitiesAt(position);
            Set<AbstractGameEntity> copy = new HashSet<>();
            for (AbstractGameEntity entity : positionEntities) {
                try {
                    AbstractGameEntity clone = entity.clone();
                    copy.add(clone);
                    if (entity.getType() == EntityType.UNIT) {
                        Unit unit = (Unit) clone;
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

        for (int playerIndex = 0; playerIndex < gameBoard.getNbPlayers(); playerIndex++) {
            FightGamePlayer player = players.get(playerIndex);
            player.setGameBoardProxy(new GameBoardProxy(this, player));
        }

        this.rows = gameBoard.getRows();
        this.cols = gameBoard.getCols();
        this.entities = entities;
        this.players = players;
        this.nextPlayerIndex = gameBoard.getNextPlayer().getPlayerIndex();
    }

    /**
     * Returns the entities present on the game board, represented as a map of
     * positions to sets of entities.
     * 
     * @return a map of positions to sets of entities
     */
    public Map<Position, Set<AbstractGameEntity>> getEntities() {
        return entities;
    }

    /**
     * Sets the strategy for filling the game board during initialization.
     * 
     * @param strategy the strategy for filling the board
     */
    public void setStrategy(GameBordInitFillStrategy strategy) {
        this.fillStrategy = strategy;
    }

    /**
     * Fill the grid based on strategy
     */
    public void fillGameBoard() {
        this.fillStrategy.fillGrid(this);
    }

    /**
     * Add a player in the game
     * 
     * @param player
     */

    public void addPlayer(FightGamePlayer player) {
        this.players.add(player);
    }

    /**
     * Add entity at the given position
     * 
     * @param entity
     * @param position
     * @return true if entity is added; else false
     */
    public boolean addEntity(AbstractGameEntity entity, Position position) {

        if (entity.getType() != EntityType.BOMB && entity.getType() != EntityType.MINE) {
            if (!isValidMove(position)) {
                return false;
            }
        } else {
            if (!isValidPosition(position))
                return false;
        }

        Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);
        positionEntities.add(entity);
        entity.setPosition(position);
        entities.put(position, positionEntities);

        // Explode mine after adding in unit cell
        if(entity.getType()==EntityType.MINE){
            for(AbstractGameEntity posEntity: positionEntities){
                if(posEntity.getType()==EntityType.UNIT){
                    detonateMineAt(position, (Mine)entity);
                    break;
                }
            }
        }

        return true;
    }

    /**
     * Return a set of entities at the given position
     */
    @Override
    public Set<AbstractGameEntity> getEntitiesAt(Position position) {
        return entities.getOrDefault(position, new HashSet<>());
    }

    /**
     * Move unit from one position to another
     * 
     * @return true if one entity has been moved, else false
     */
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

        unit.receiveDamage(UnchangeableSettings.MOVE_COST);

        this.getEntitiesAt(oldPosition).remove(unit);

        Set<AbstractGameEntity> newPositionEntities = this.getEntitiesAt(newPosition);

        if (unit.isAlive())
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

    /**
     * 
     * @return The next player
     */
    public FightGamePlayer getNextPlayer() {
        return players.get(nextPlayerIndex % players.size());
    }

    /**
     * Return possible actions that the next player can do
     * 
     * @param player
     * @return
     */
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

        Collections.sort(actions);

        return actions;
    }

    /**
     * Execute en action given by the next player
     * 
     * @param action
     * @param player
     * @return true if action is executed else false
     */
    public boolean performAction(FightGameAction action, FightGamePlayer player) {
        Unit unit = player.getUnit();
        Position unitPosition = unit.getPosition();
        reinitializeFromLastAction();

        switch (action.getTYPE()) {
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
                while (col < Math.min(maxDistance + 1, this.getCols())) {
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
                while (row < Math.min(maxDistance + 1, this.getRows())) {
                    Position position = new Position(row, col);

                    if (!this.projectileEffect(position))
                        break;

                    row++;
                }

                break;

            }

            case NOTHING:
                this.nbBoringMove += 1;
                break;

            case ACTIVATE_SHIELD:
                unit.receiveDamage(UnchangeableSettings.SHIELD_COST);
                unit.setShieldActivated(true);
                unit.setShieldTimer(UnchangeableSettings.SHIELD_TIMER);
                break;

            default:
                break;
        }

        if (action.getTYPE() != FightGameActionType.NOTHING)
            this.nbBoringMove += 0;

        this.updateEntities();

        getNextPlayerIndex();

        lastActionPlayed = action;
        precedentPlayer = player;

        this.notifyModelListeners();

        return true;
    }

    /**
     * @param position
     * @return true if one unit can move to given position; else false
     */
    private boolean isValidMove(Position position) {
        if (!isValidPosition(position))
            return false;

        // Position occupied by another unit
        if (!this.getEntitiesAt(position).isEmpty()) {
            Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);

            for (AbstractGameEntity entity : positionEntities) {
                if (entity.getType() == EntityType.UNIT)
                    return false;
            }

        }

        return true;
    }

    /**
     * 
     * @param position
     * @return true if a position coordinates are valid and there is no a wall; else
     *         false
     */
    public boolean isValidPosition(Position position) {
        int r = position.getRow();
        int c = position.getCol();

        if (!((0 <= r && r < rows) && (0 <= c && c < cols)))
            return false;

        // Position occupied by a wall;
        if (!this.getEntitiesAt(position).isEmpty()) {
            Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);

            for (AbstractGameEntity entity : positionEntities) {
                if (entity.getType() == EntityType.WALL)
                    return false;
            }

        }

        return true;
    }

    /**
     * 
     * @return grid rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * 
     * @return gride columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Return the next player alived to play
     */
    @Override
    public int getNextPlayerIndex() {
        nextPlayerIndex = (nextPlayerIndex + 1) % players.size();
        if (playersRemaining() == 0)
            return nextPlayerIndex;
        boolean ok = false;
        while (!ok) {
            Unit nextUnit = players.get(nextPlayerIndex).getUnit();
            if (nextUnit == null) {
                nextPlayerIndex = (nextPlayerIndex + 1) % players.size();
                continue;
            } else if (!nextUnit.isAlive()) {
                nextPlayerIndex = (nextPlayerIndex + 1) % players.size();
                continue;
            }

            ok = true;

        }

        return nextPlayerIndex;
    }

    /**
     * update entities after each action
     */
    public void updateEntities() {

        for (Position position : this.entities.keySet()) {

            Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);

            for (AbstractGameEntity entity : new HashSet<>(positionEntities)) {

                switch (entity.getType()) {
                    case BOMB: {
                        Bomb bomb = (Bomb) entity;
                        if (bomb.getTimeBeforeExplosion() == 0 || !bomb.getOwner().getUnit().isAlive()) {
                            detonateBombAt(position, bomb);
                        } else {
                            if(this.getNextPlayer().equals(bomb.getOwner()))
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
                        if (!unit.isAlive())
                            positionEntities.remove(entity);
                        break;
                    }
                    default:
                        break;
                }

            }

        }

    }

    /**
     * Bomb explosion effect
     * 
     * @param position
     * @param currentBomb
     */
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

    /**
     * Mine effect
     * 
     * @param position
     * @param currentMine
     */
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
                    if (!unit.isAlive())
                        positionEntities.remove(entity);
                    break;

                default:
                    break;
            }

        }

        impactedPositionsByMine.add(position);
    }

    /**
     * projectile effect
     * 
     * @param position
     * @return
     */
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

    /**
     * Simulate a game
     */
    public void run() {
        while (!isGameOver()) {
            try {
                FightGamePlayer player = this.getNextPlayer();

                Thread.sleep(5000);
                Action action = player.play();
                System.out.println(player + " played " + action);
                performAction((FightGameAction) action, player);
                for (Player p : players) {
                    if (p.getUnit().isAlive())
                        System.out.println(p + " energy remaining is " + p.getUnit().getEnergy());
                    else
                        System.out.println(p + " is dead");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * count remaining players
     * 
     * @return
     */
    public int playersRemaining() {

        int nb = 0;
        for (FightGamePlayer player : players) {
            if (player.getUnit() == null)
                continue;
            if (player.getUnit().isAlive()) {
                nb++;
            }
        }
        return nb;
    }

    /**
     * Return all players
     * 
     * @return
     */
    public List<FightGamePlayer> getPlayers() {
        return players;
    }

    /**
     * Clear effect of last actions
     */
    public void reinitializeFromLastAction() {
        impactedPositionsByProjectile.clear();
        impactedPositionsByBomb.clear();
        impactedPositionsByMine.clear();
        lastMove.clear();
    }

    /**
     * @return list of position impacted by bomb explosion
     */
    public List<Position> getImpactedPositionsByBomb() {
        return impactedPositionsByBomb;
    }

    /**
     * 
     * @return list of position impacted by mine explosion
     */
    public List<Position> getImpactedPositionsByMine() {
        return impactedPositionsByMine;
    }

    /**
     * Return list of two cols
     * First col: unit old position
     * Second col: unit new position
     */
    public List<Position> getLastMove() {
        return lastMove;
    }

    /**
     * 
     * @return list of position impacted by projectiles bullets
     */
    public List<Position> getImpactedPositionsByProjectile() {
        return impactedPositionsByProjectile;
    }

    /**
     * 
     * @return true if the game is over
     */
    public boolean isGameOver() {

        if (this.nbBoringMove > UnchangeableSettings.BORING_MOVE_LIMIT) {
            this.notifyModelListeners();
            return true;
        }

        if (playersRemaining() <= 1) {
            this.notifyModelListeners();
            return true;
        }

        return false;

    }

    /**
     * 
     * @return number of players
     */
    public int getNbPlayers() {
        return this.players.size();
    }

    /**
     * 
     * @return last action played
     */
    public FightGameAction getLastActionPlayed() {
        return lastActionPlayed;
    }

    /**
     * 
     * @return last player
     */
    public FightGamePlayer getPrecedentPlayer() {
        return precedentPlayer;
    }

}
