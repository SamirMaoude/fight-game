package fightGame.model.strategy;

import fightGame.model.FightGameAction;
import fightGame.model.FightGameActionType;
import fightGame.model.GameBoardProxy;
import fightGame.view.widgets.ActionView;
import gamePlayers.util.Action;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;
import gamePlayers.util.Player;

public class HumainStrategy implements FightGamePlayerStrategy, ModelListener {
    private volatile ActionView  actionView;
    private Action action = null;

    @Override
    public Action play(Player player, GameBoardProxy proxy) {
        return null;
    }

    @Override
    public void update(ListenableModel source) {
        this.action = this.actionView.getAction(); 
    }
}
