package fightGame.model.aiAlgorithms;


import java.io.Serializable;
import java.util.List;
import fightGame.model.*;
import fightGame.model.strategy.FightGamePlayerStrategy;
import gamePlayers.AbstractGameEntity;
import gamePlayers.util.*;

public class AttackStrategy implements FightGamePlayerStrategy, Serializable{
    private int moveCounter = 0;
    private boolean usedMine = false;
    private boolean usedProjectile = false;

    @Override
    public Action play(Player player, GameBoardProxy proxy) {
        if (!usedProjectile) {
            usedProjectile = true;
            return useProjectile(player, proxy);
        }

        if (moveCounter < 1) {
            moveCounter++;
            return moveTowardsEnemy(player, proxy);
        } else {
            moveCounter = 0;
            usedProjectile = false;
            if (usedMine) {
                usedMine = false;
                return useBomb(player, proxy);
            } else {
                usedMine = true;
                return useMine(player, proxy);
            }
        }
    }

    private FightGameAction moveTowardsEnemy(Player player, GameBoardProxy proxy) {
        Position myPosition = player.getUnit().getPosition();
        Position closestEnemy = getClosestEnemyPosition(player, proxy);

        if (closestEnemy != null) {
            Position nextStep = getNextStepTowards(myPosition, closestEnemy, proxy);
            if (nextStep != null && isSafePosition(nextStep, proxy)) {
                return createMoveAction(myPosition, nextStep);
            }
        }

        return new FightGameAction(FightGameActionType.NOTHING);
    }

    private FightGameAction useProjectile(Player player, GameBoardProxy proxy) {
        Position myPosition = player.getUnit().getPosition();
        List<FightGamePlayer> players = proxy.getPlayers();

        if (!player.getUnit().getProjectiles().isEmpty()) {
            for (FightGamePlayer target : players) {
                Position targetPosition = target.getUnit().getPosition();
                if (isSameColumn(myPosition, targetPosition)) {
                    return myPosition.getRow() < targetPosition.getRow() ?
                            new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_BOTTOM) :
                            new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_TOP);
                }
                if (isSameRow(myPosition, targetPosition)) {
                    return myPosition.getCol() < targetPosition.getCol() ?
                            new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_RIGHT) :
                            new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_LEFT);
                }
            }
        }

        return new FightGameAction(FightGameActionType.NOTHING);
    }

    private FightGameAction useBomb(Player player, GameBoardProxy proxy) {
        return useItemAround(player, proxy, FightGameActionType.USE_BOMB_AT_LEFT,
                FightGameActionType.USE_BOMB_AT_RIGHT,
                FightGameActionType.USE_BOMB_AT_TOP,
                FightGameActionType.USE_BOMB_AT_BOTTOM);
    }

    private FightGameAction useMine(Player player, GameBoardProxy proxy) {
        if (!player.getUnit().getMines().isEmpty()) {
            return useItemAround(player, proxy, FightGameActionType.USE_MINE_AT_LEFT,
                    FightGameActionType.USE_MINE_AT_RIGHT,
                    FightGameActionType.USE_MINE_AT_TOP,
                    FightGameActionType.USE_MINE_AT_BOTTOM);
        }
        return new FightGameAction(FightGameActionType.NOTHING);
    }

    private FightGameAction useItemAround(Player player, GameBoardProxy proxy,
                                          FightGameActionType left, FightGameActionType right,
                                          FightGameActionType top, FightGameActionType bottom) {
        Position myPosition = player.getUnit().getPosition();
        Position p = new Position(myPosition);
        p.moveLeft();
        if (isSafePosition(p, proxy)) return new FightGameAction(left);
        p.moveRight();
        p.moveRight();
        if (isSafePosition(p, proxy)) return new FightGameAction(right);
        p.moveLeft();
        p.moveTop();
        if (isSafePosition(p, proxy)) return new FightGameAction(top);
        p.moveBottom();
        p.moveBottom();
        if (isSafePosition(p, proxy)) return new FightGameAction(bottom);

        return new FightGameAction(FightGameActionType.NOTHING);
    }

    // Helper methods
    private boolean isSafePosition(Position position, GameBoardProxy proxy) {
        if (!proxy.isValidPosition(position)) return false;
        for (AbstractGameEntity entity : proxy.getEntitiesAt(position)) {
            if (entity.getType() == EntityType.BOMB || entity.getType() == EntityType.MINE) {
                return false;
            }
        }
        return true;
    }

    private Position getClosestEnemyPosition(Player player, GameBoardProxy proxy) {
        Position myPosition = player.getUnit().getPosition();
        Position closestEnemy = null;
        double minDistance = Double.MAX_VALUE;

        for (FightGamePlayer enemy : proxy.getPlayers()) {
            if (!enemy.equals(player)) {
                double distance = myPosition.distanceTo(enemy.getUnit().getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestEnemy = enemy.getUnit().getPosition();
                }
            }
        }
        return closestEnemy;
    }

    private Position getNextStepTowards(Position from, Position to, GameBoardProxy proxy) {
        Position top = new Position(from);
        top.moveTop();

        Position bottom = new Position(from);
        bottom.moveBottom();

        Position left = new Position(from);
        left.moveLeft();

        Position right = new Position(from);
        right.moveRight();

        Position[] directions = {left, right, top, bottom};
        for (Position direction : directions) {
            if (proxy.isValidPosition(direction) && from.distanceTo(to) > direction.distanceTo(to)) {
                return direction;
            }
        }
        return null;
    }

    private FightGameAction createMoveAction(Position from, Position to) {
        Position top = new Position(from);
        top.moveTop();

        Position bottom = new Position(from);
        bottom.moveBottom();

        Position left = new Position(from);
        left.moveLeft();

        Position right = new Position(from);
        right.moveRight();

        if (to.equals(left)) return new FightGameAction(FightGameActionType.MOVE_UNIT_TO_LEFT);
        if (to.equals(right)) return new FightGameAction(FightGameActionType.MOVE_UNIT_TO_RIGHT);
        if (to.equals(top)) return new FightGameAction(FightGameActionType.MOVE_UNIT_TO_TOP);
        if (to.equals(bottom)) return new FightGameAction(FightGameActionType.MOVE_UNIT_TO_BOTTOM);
        return new FightGameAction(FightGameActionType.NOTHING);
    }

    private boolean isSameColumn(Position p1, Position p2) {
        return p1.getCol() == p2.getCol();
    }

    private boolean isSameRow(Position p1, Position p2) {
        return p1.getRow() == p2.getRow();
    }
}
