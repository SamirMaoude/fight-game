package fightGame.model.strategy;

import fightGame.model.GameBoard;

/**
 * Interface representing a strategy for initializing and filling the game board.
 */
public interface GameBordInitFillStrategy {

    /**
     * Fills the grid of the specified game board according to the strategy.
     *
     * @param gameBoard the {@link GameBoard} to be initialized and filled
     */
    void fillGrid(GameBoard gameBoard);
}

