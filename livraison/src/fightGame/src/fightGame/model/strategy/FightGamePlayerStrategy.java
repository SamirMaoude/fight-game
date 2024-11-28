package fightGame.model.strategy;

import fightGame.model.GameBoardProxy;
import gamePlayers.util.Action;
import gamePlayers.util.Player;

public interface FightGamePlayerStrategy {
    public Action play(Player player, GameBoardProxy proxy);
}
