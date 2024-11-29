package fightGame.model;

import java.io.Serializable;

import gamePlayers.util.Action;

public class FightGameAction implements Action, Serializable {

    public FightGameActionType TYPE;
    public FightGameAction(FightGameActionType type){
        this.TYPE = type;
    }
    @Override
    public String toString() {
        return "Action [TYPE=" + TYPE + "]";
    }
    public FightGameActionType getTYPE() {
        return TYPE;
    }

}
