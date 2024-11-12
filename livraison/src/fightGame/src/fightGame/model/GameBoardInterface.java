package fightGame.model;

import java.util.List;
import java.util.Set;

import gamePlayers.AbstractGameEntity;
import gamePlayers.util.Action;
import gamePlayers.util.Direction;
import gamePlayers.util.Position;

public interface GameBoardInterface {
    Set<AbstractGameEntity> getEntitiesAt(Position position);
    boolean moveUnit(Position oldPosition, Direction direction);
    int getNextPlayerIndex();
}
