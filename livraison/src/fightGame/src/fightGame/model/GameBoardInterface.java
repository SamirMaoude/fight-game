package fightGame.model;

import java.util.Set;

import gamePlayers.AbstractGameEntity;
import gamePlayers.util.Direction;
import gamePlayers.util.Position;

/**
 * Interface that defines the operations for the game board.
 * The game board is responsible for managing the entities in the game world, 
 * such as units, obstacles, and other entities, and provides methods to manipulate their positions.
 */
public interface GameBoardInterface {

    /**
     * Retrieves all game entities located at a specified position on the game board.
     * The entities could be units, obstacles, or any other type of game entity that is positioned at the given location.
     *
     * @param position the position on the game board
     * @return a set of entities at the given position
     */
    Set<AbstractGameEntity> getEntitiesAt(Position position);

    /**
     * Moves a unit from its current position to a new position in the specified direction.
     * This method updates the position of the unit on the game board.
     *
     * @param oldPosition the unit's current position
     * @param direction the direction in which the unit should move
     * @return true if the move was successful, false otherwise (e.g., if the move is blocked)
     */
    boolean moveUnit(Position oldPosition, Direction direction);

    /**
     * Gets the index of the next player whose turn it is to play.
     * This method is used to determine the order in which players take their turns in the game.
     *
     * @return the index of the next player
     */
    int getNextPlayerIndex();
}
