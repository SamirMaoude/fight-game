package fightGame.model.strategy;

import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import fightGame.model.FightGameAction;
import fightGame.model.FightGameActionType;
import fightGame.model.GameBoardProxy;
import fightGame.view.widgets.ActionView;
import gamePlayers.util.Action;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;
import gamePlayers.util.Player;

public class HumainStrategy implements FightGamePlayerStrategy, ModelListener {
    private ActionView actionView;
    private Action action = null;
    private final CountDownLatch latch = new CountDownLatch(1); // Synchronisation

    @Override
    public Action play(Player player, GameBoardProxy proxy) {
        /*this.actionView = new ActionView(null, proxy);
        actionView.addModelListener(this);
        return this.action; */
        //TO DO
        return new FightGameAction(FightGameActionType.NOTHING);
    }

    @Override
    public void update(ListenableModel source) {
        this.action = this.actionView.getAction(); 
    }
}
