package fightGame.model.strategy.aiAlgorithms;

import java.io.Serializable;

import fightGame.model.GameBoardProxy;
import fightGame.model.strategy.FightGamePlayerStrategy;
import gamePlayers.util.Action;
import gamePlayers.util.Player;

/**
 * A composite strategy that dynamically selects a strategy based on the
 * player's energy level.
 */
public class MultiStrategy implements FightGamePlayerStrategy, Serializable {

    /**
     * Decides the player's action based on their current energy level.
     * - High energy (> 70): Uses the RandomStrategy .
     * - Medium energy (21-70): Uses the MinimaxStrategy.
     * - Low energy (≤ 20): Uses the AttackStrategy.
     *
     * @param player the player whose action is being decided
     * @param proxy  the game board proxy
     * @return the action chosen by the selected strategy
     */
    @Override
    public Action play(Player player, GameBoardProxy proxy) {
        int energy = player.getUnit().getEnergy();
        if (energy > 70) {
            return new RandomStrategy().play(player, proxy);
        }
        if (energy > 20) {
            return new MinimaxStrategy().play(player, proxy);
        }
        return new AttackStrategy().play(player, proxy);
    }
}
