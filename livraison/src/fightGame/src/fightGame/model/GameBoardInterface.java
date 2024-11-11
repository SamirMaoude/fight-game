package fightGame.model;

import java.util.List;

import gamePlayers.AbstractGameEntity;
import gamePlayers.util.Action;
import gamePlayers.util.Direction;
import gamePlayers.util.Player;
import gamePlayers.util.Position;

public interface GameBoardInterface {
    AbstractGameEntity getEntityAt(Position position);
    boolean moveEntity(Position oldPosition, Direction direction);
    int getNextPlayerIndex();
    List<Action> getActions(FightGamePlayer player);
    boolean performAction(Action action, FightGamePlayer player);
}
