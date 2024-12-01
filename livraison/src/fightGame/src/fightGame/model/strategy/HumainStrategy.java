package fightGame.model.strategy;

import java.io.Serializable;

import fightGame.model.GameBoardProxy;
import fightGame.view.widgets.ActionView;
import gamePlayers.util.Action;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;
import gamePlayers.util.Player;

/**
 * Strategy implementation for human players in the fight game.
 * This class listens for user inputs to determine the player's actions.
 */
public class HumainStrategy implements FightGamePlayerStrategy, Serializable {

    private volatile ActionView actionView;
    private Action action = null;

    /**
     * Defines the action to be performed by the human player. 
     * Currently returns {@code null}, as the action is determined interactively via the GUI.
     *
     * @param player the human player
     * @param proxy  the game board proxy providing access to the game state
     * @return the action chosen by the human player (to be implemented)
     */
    @Override
    public Action play(Player player, GameBoardProxy proxy) {
        return null;
    }
}

