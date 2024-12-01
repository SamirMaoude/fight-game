package fightGame.model.strategy;

import fightGame.model.GameBoardProxy;
import gamePlayers.util.Action;
import gamePlayers.util.Player;

/**
 * Interface defining the strategy for a fight game player.
 * Strategies determine how a player selects actions during the game.
 */
public interface FightGamePlayerStrategy {

    /**
     * Determines the action a player should take based on the current game state.
     *
     * @param player the player executing the strategy
     * @param proxy  the game board proxy providing access to game state information
     * @return the action chosen by the strategy
     */
    public Action play(Player player, GameBoardProxy proxy);
}
