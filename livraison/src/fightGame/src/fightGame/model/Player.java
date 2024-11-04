package fightGame.model;

import gamePlayers.fighters.Unit;

public interface Player {
    public Unit getUnit();
    public Action play();
}
