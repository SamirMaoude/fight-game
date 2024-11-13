package fightGame.model;

import gamePlayers.util.Action;

public class FightGameAction implements Action {

    public FightGameActionType TYPE;
    public FightGameAction(FightGameActionType type){
        this.TYPE = type;
    }
    @Override
    public String toString() {
        return "Action [TYPE=" + TYPE + "]";
    }

    

}
