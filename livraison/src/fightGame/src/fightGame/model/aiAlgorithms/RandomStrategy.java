package fightGame.model.aiAlgorithms;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import fightGame.model.GameBoardProxy;
import fightGame.model.strategy.FightGamePlayerStrategy;
import gamePlayers.util.Action;
import gamePlayers.util.Player;

public class RandomStrategy implements FightGamePlayerStrategy, Serializable{
    

    @Override
    public Action play(Player player, GameBoardProxy proxy) {
        List<Action> actions = proxy.getActions();

        Random random = new Random();
        Action randomAction = actions.get(random.nextInt(actions.size()));
        return randomAction;
    }

}
