package fightGame.model.strategy.aiAlgorithms;

import java.io.Serializable;
import java.util.List;
import fightGame.model.*;
import fightGame.model.strategy.FightGamePlayerStrategy;
import gamePlayers.AbstractGameEntity;
import gamePlayers.util.*;

/**
 * AI strategy focused on attacking opponents in the Fight Game.
 * The strategy alternates between using projectiles, moving toward enemies,
 * and using bombs or mines based on the current state of the game.
 */
public class AttackStrategy implements FightGamePlayerStrategy, Serializable {
    private int moveCounter = 0;
    private boolean usedMine = false;
    private boolean usedProjectile = false;

    /**
     * Determines the next action for the player based on the strategy.
     *
     * @param player the player executing the strategy
     * @param proxy  the game board proxy providing access to the game state
     * @return the next action for the player
     */

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

    /**
     * Moves the player toward the closest enemy.
     *
     * @param player the player
     * @param proxy  the game board proxy
     * @return the move action or a "do nothing" action if no move is possible
     */

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

    /**
     * Uses a projectile against an opponent, if possible.
     *
     * @param player the player
     * @param proxy  the game board proxy
     * @return the action to use a projectile or a "do nothing" action if
     *         unavailable
     */

    private FightGameAction useProjectile(Player player, GameBoardProxy proxy) {
        Position myPosition = player.getUnit().getPosition();
        List<FightGamePlayer> players = proxy.getPlayers();

        if (!player.getUnit().getProjectiles().isEmpty()) {
            for (FightGamePlayer target : players) {
                Position targetPosition = target.getUnit().getPosition();
                if (isSameColumn(myPosition, targetPosition)) {
                    return myPosition.getRow() < targetPosition.getRow()
                            ? new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_BOTTOM)
                            : new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_TOP);
                }
                if (isSameRow(myPosition, targetPosition)) {
                    return myPosition.getCol() < targetPosition.getCol()
                            ? new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_RIGHT)
                            : new FightGameAction(FightGameActionType.USE_PROJECTILE_AT_LEFT);
                }
            }
        }

        return new FightGameAction(FightGameActionType.NOTHING);
    }

    /**
     * Uses a bomb around the player's position.
     *
     * @param player the player
     * @param proxy  the game board proxy
     * @return the bomb action or "do nothing" action if unavailable
     */
    private FightGameAction useBomb(Player player, GameBoardProxy proxy) {
        return useItemAround(player, proxy, FightGameActionType.USE_BOMB_AT_LEFT,
                FightGameActionType.USE_BOMB_AT_RIGHT,
                FightGameActionType.USE_BOMB_AT_TOP,
                FightGameActionType.USE_BOMB_AT_BOTTOM);
    }

    /**
     * Uses a mine around the player's position.
     *
     * @param player the player
     * @param proxy  the game board proxy
     * @return the mine action or "do nothing" action if unavailable
     */
    private FightGameAction useMine(Player player, GameBoardProxy proxy) {
        if (!player.getUnit().getMines().isEmpty()) {
            return useItemAround(player, proxy, FightGameActionType.USE_MINE_AT_LEFT,
                    FightGameActionType.USE_MINE_AT_RIGHT,
                    FightGameActionType.USE_MINE_AT_TOP,
                    FightGameActionType.USE_MINE_AT_BOTTOM);
        }
        return new FightGameAction(FightGameActionType.NOTHING);
    }

    /**
     * Attempts to use an item (e.g., bomb or mine) around the player's current
     * position.
     * 
     * @param player the player attempting to use the item
     * @param proxy  the game board proxy
     * @param left   the action for placing the item to the left
     * @param right  the action for placing the item to the right
     * @param top    the action for placing the item above
     * @param bottom the action for placing the item below
     * @return the action for using the item or a "do nothing" action if all
     *         positions are unsafe
     */
    private FightGameAction useItemAround(Player player, GameBoardProxy proxy,
            FightGameActionType left, FightGameActionType right,
            FightGameActionType top, FightGameActionType bottom) {
        Position myPosition = player.getUnit().getPosition();
        Position p = new Position(myPosition);
        p.moveLeft();
        if (isSafePosition(p, proxy))
            return new FightGameAction(left);
        p.moveRight();
        p.moveRight();
        if (isSafePosition(p, proxy))
            return new FightGameAction(right);
        p.moveLeft();
        p.moveTop();
        if (isSafePosition(p, proxy))
            return new FightGameAction(top);
        p.moveBottom();
        p.moveBottom();
        if (isSafePosition(p, proxy))
            return new FightGameAction(bottom);

        return new FightGameAction(FightGameActionType.NOTHING);
    }

    /**
     * Checks if a given position is safe for the player to move or place items.
     * 
     * @param position the position to check
     * @param proxy    the game board proxy
     * @return true if the position is valid and safe, false otherwise
     */
    private boolean isSafePosition(Position position, GameBoardProxy proxy) {
        if (!proxy.isValidPosition(position))
            return false;
        for (AbstractGameEntity entity : proxy.getEntitiesAt(position)) {
            if (entity.getType() == EntityType.BOMB || entity.getType() == EntityType.MINE) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the closest enemy's position to the player.
     * 
     * @param player the player whose enemy is being searched
     * @param proxy  the game board proxy
     * @return the position of the closest enemy, or null if no enemies are found
     */
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

    /**
     * Finds the next step to move toward a target position.
     * 
     * @param from  the starting position
     * @param to    the target position
     * @param proxy the game board proxy
     * @return the next position toward the target, or null if no valid step exists
     */
    private Position getNextStepTowards(Position from, Position to, GameBoardProxy proxy) {
        Position top = new Position(from);
        top.moveTop();

        Position bottom = new Position(from);
        bottom.moveBottom();

        Position left = new Position(from);
        left.moveLeft();

        Position right = new Position(from);
        right.moveRight();

        Position[] directions = { left, right, top, bottom };
        for (Position direction : directions) {
            if (proxy.isValidPosition(direction) && from.distanceTo(to) > direction.distanceTo(to)) {
                return direction;
            }
        }
        return null;
    }

    /**
     * Creates a move action for the player based on the target position.
     * 
     * @param from the current position
     * @param to   the target position
     * @return the appropriate move action or a "do nothing" action
     */
    private FightGameAction createMoveAction(Position from, Position to) {
        Position top = new Position(from);
        top.moveTop();

        Position bottom = new Position(from);
        bottom.moveBottom();

        Position left = new Position(from);
        left.moveLeft();

        Position right = new Position(from);
        right.moveRight();

        if (to.equals(left))
            return new FightGameAction(FightGameActionType.MOVE_UNIT_TO_LEFT);
        if (to.equals(right))
            return new FightGameAction(FightGameActionType.MOVE_UNIT_TO_RIGHT);
        if (to.equals(top))
            return new FightGameAction(FightGameActionType.MOVE_UNIT_TO_TOP);
        if (to.equals(bottom))
            return new FightGameAction(FightGameActionType.MOVE_UNIT_TO_BOTTOM);
        return new FightGameAction(FightGameActionType.NOTHING);
    }

    /**
     * Checks if two positions are in the same column.
     * 
     * @param p1 the first position
     * @param p2 the second position
     * @return true if both positions share the same column, false otherwise
     */
    private boolean isSameColumn(Position p1, Position p2) {
        return p1.getCol() == p2.getCol();
    }

    /**
     * Checks if two positions are in the same row.
     * 
     * @param p1 the first position
     * @param p2 the second position
     * @return true if both positions share the same row, false otherwise
     */
    private boolean isSameRow(Position p1, Position p2) {
        return p1.getRow() == p2.getRow();
    }
}
