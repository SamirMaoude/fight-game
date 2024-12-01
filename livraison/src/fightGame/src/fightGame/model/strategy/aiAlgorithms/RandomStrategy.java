package fightGame.model.strategy.aiAlgorithms;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import fightGame.model.GameBoardProxy;
import fightGame.model.strategy.FightGamePlayerStrategy;
import gamePlayers.util.Action;
import gamePlayers.util.Player;

/**
 * A strategy that selects a random action from the available actions.
 */
public class RandomStrategy implements FightGamePlayerStrategy, Serializable {

    /**
     * Chooses a random action for the player from the list of valid actions.
     *
     * @param player the player whose action is being decided
     * @param proxy  the game board proxy
     * @return a randomly chosen action
     */
    @Override
    public Action play(Player player, GameBoardProxy proxy) {
        List<Action> actions = proxy.getActions(); // Get all valid actions
        Random random = new Random();
        return actions.get(random.nextInt(actions.size())); // Select a random action
    }
}
