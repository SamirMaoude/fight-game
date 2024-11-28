package fightGame.model.strategy;

import fightGame.model.GameBoardProxy;
import fightGame.model.aiAlgorithms.MinimaxStrategy;
import gamePlayers.util.Action;
import gamePlayers.util.Player;

public class MultiStrategy implements FightGamePlayerStrategy {

    @Override
    public Action play(Player player, GameBoardProxy proxy) {
        int energy = player.getUnit().getEnergy();
        if (energy > 70) {
            return new AttackStrategy().play(player, proxy);
        }
        if (energy > 20) {
            return new MinimaxStrategy().play(player, proxy);
        }
        return new RandomStrategy().play(player, proxy);
    }

}
