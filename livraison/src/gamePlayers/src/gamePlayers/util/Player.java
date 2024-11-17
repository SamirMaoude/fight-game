package gamePlayers.util;

import gamePlayers.fighters.Unit;

public interface Player extends Cloneable{
    public Unit getUnit();
    public Action play();
    @Override
    public boolean equals(Object obj);
    public Player clone() throws CloneNotSupportedException;
    public int getPlayerIndex();
}
