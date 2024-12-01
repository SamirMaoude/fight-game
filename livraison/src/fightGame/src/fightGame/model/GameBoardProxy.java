package fightGame.model;

import java.util.ArrayList;
import java.io.Serializable;
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

/**
 * The GameBoardProxy acts as a proxy for a GameBoard, allowing a player to interact with the game board
 * while controlling the visibility and actions of certain entities based on the player's status and game settings.
 * It implements the GameBoardInterface and provides a controlled view of the underlying game board.
 */
public class GameBoardProxy implements GameBoardInterface, Serializable {

    private GameBoard gameBoard;
    private FightGamePlayer player;

    /**
     * Constructs a GameBoardProxy with the specified game board and player.
     * 
     * @param gameBoard the underlying game board to proxy
     * @param player the player for whom the proxy is created
     */
    public GameBoardProxy(GameBoard gameBoard, FightGamePlayer player){
        this.gameBoard = gameBoard;
        this.player = player;
    }

    /**
     * Returns the entities present at a specific position on the game board, with visibility rules applied based on the player's settings.
     * BOMB and MINE entities are only visible to their owners if the corresponding visibility setting is false.
     * 
     * @param position the position on the game board
     * @return a set of entities at the specified position, filtered by visibility rules
     */
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

    /**
     * Moves a unit from its current position to a new position in the specified direction, if the player owns the unit.
     * 
     * @param oldPosition the current position of the unit
     * @param direction the direction to move the unit
     * @return true if the move is successful, false otherwise
     */
    @Override
    public boolean moveUnit(Position oldPosition, Direction direction) {

        Set<AbstractGameEntity> oldPositionEntities = this.getEntitiesAt(oldPosition);

        if(oldPositionEntities.isEmpty()) return false;

        Unit unit = null;
        for(AbstractGameEntity entity: oldPositionEntities){
            if(entity.getType() == EntityType.UNIT){
                unit = (Unit) entity;
                break;
            }
        }

        if(unit == null || !unit.getOwner().equals(player)) return false;

        return gameBoard.moveUnit(oldPosition, direction);
    }

    /**
     * Returns the next player in the game.
     * 
     * @return the next player
     */
    public FightGamePlayer getNextPlayer() {
        return gameBoard.getNextPlayer();
    }

    /**
     * Returns the list of actions available to the player on the game board.
     * 
     * @return the list of actions available to the player
     */
    public List<Action> getActions() {
        return gameBoard.getActions(player);
    }

    /**
     * Performs the specified action on behalf of the player.
     * 
     * @param action the action to be performed
     * @return true if the action is successfully performed, false otherwise
     */
    public boolean performAction(FightGameAction action) {
        return this.gameBoard.performAction(action, player);
    }

    /**
     * Sets the underlying game board for this proxy.
     * 
     * @param gameBoard the new game board to be set
     */
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Returns the player associated with this proxy.
     * 
     * @return the player associated with the proxy
     */
    public FightGamePlayer getPlayer() {
        return player;
    }

    /**
     * Checks if the specified position is valid on the game board.
     * 
     * @param position the position to check
     * @return true if the position is valid, false otherwise
     */
    public boolean isValidPosition(Position position) {
        return gameBoard.isValidPosition(position);
    }

    /**
     * Returns the index of the next player.
     * 
     * @return the index of the next player
     */
    @Override
    public int getNextPlayerIndex() {
        return gameBoard.getNextPlayerIndex();
    }

    /**
     * Returns the underlying game board.
     * 
     * @return the game board associated with the proxy
     */
    public GameBoard getGameBoard() {

        List<FightGamePlayer> players = new ArrayList<>();
        for (int playerIndex = 0; playerIndex < gameBoard.getNbPlayers(); playerIndex++) {
            players.add(new FightGamePlayer(null, playerIndex));
        }

        Map<Position, Set<AbstractGameEntity>> entities = new HashMap<>();
        for (Position position : gameBoard.getEntities().keySet()) {
            Set<AbstractGameEntity> positionEntities = this.getEntitiesAt(position);
            Set<AbstractGameEntity> copy = new HashSet<>();
            for (AbstractGameEntity entity : positionEntities) {
                try {
                    AbstractGameEntity clone = entity.clone();
                    copy.add(clone);
                    if (clone.getType() == EntityType.UNIT) {
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

        GameBoard clone = new GameBoard(gameBoard, players, entities);

        for (int playerIndex = 0; playerIndex < gameBoard.getNbPlayers(); playerIndex++) {
            FightGamePlayer player = players.get(playerIndex);
            player.setGameBoardProxy(new GameBoardProxy(clone, player));
        }

        return clone;
    }

    /**
     * Returns the list of positions impacted by bombs on the game board.
     * 
     * @return a list of positions impacted by bombs
     */
    public List<Position> getImpactedPositionsByBomb() {
        return gameBoard.getImpactedPositionsByBomb();
    }

    /**
     * Returns the list of positions impacted by mines on the game board.
     * 
     * @return a list of positions impacted by mines
     */
    public List<Position> getImpactedPositionsByMine() {
        return gameBoard.getImpactedPositionsByMine();
    }

    /**
     * Returns the list of positions of the last move made on the game board.
     * 
     * @return a list of positions involved in the last move
     */
    public List<Position> getLastMove() {
        return gameBoard.getLastMove();
    }

    /**
     * Returns the list of positions impacted by projectiles on the game board.
     * 
     * @return a list of positions impacted by projectiles
     */
    public List<Position> getImpactedPositionsByProjectile() {
        return gameBoard.getImpactedPositionsByProjectile();
    }

    /**
     * Checks if the game is over.
     * 
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameBoard.isGameOver();
    }

    /**
     * Returns the list of players in the game.
     * 
     * @return the list of players
     */
    public List<FightGamePlayer> getPlayers() {
        return gameBoard.getPlayers();
    }
}
