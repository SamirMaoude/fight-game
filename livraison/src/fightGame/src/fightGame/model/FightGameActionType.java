package fightGame.model;

/**
 * Enum representing the various action types available in the Fight Game.
 */
public enum FightGameActionType {
    /**
     * Move the unit to the right.
     */
    MOVE_UNIT_TO_RIGHT,

    /**
     * Move the unit to the left.
     */
    MOVE_UNIT_TO_LEFT,

    /**
     * Move the unit downward.
     */
    MOVE_UNIT_TO_BOTTOM,

    /**
     * Move the unit upward.
     */
    MOVE_UNIT_TO_TOP,

    /**
     * Place a mine to the left of the unit.
     */
    USE_MINE_AT_LEFT,

    /**
     * Place a mine to the right of the unit.
     */
    USE_MINE_AT_RIGHT,

    /**
     * Place a mine below the unit.
     */
    USE_MINE_AT_BOTTOM,

    /**
     * Place a mine above the unit.
     */
    USE_MINE_AT_TOP,

    /**
     * Place a mine at the top-left corner relative to the unit.
     */
    USE_MINE_AT_TOP_LEFT,

    /**
     * Place a mine at the top-right corner relative to the unit.
     */
    USE_MINE_AT_TOP_RIGHT,

    /**
     * Place a mine at the bottom-left corner relative to the unit.
     */
    USE_MINE_AT_BOTTOM_LEFT,

    /**
     * Place a mine at the bottom-right corner relative to the unit.
     */
    USE_MINE_AT_BOTTOM_RIGHT,

    /**
     * Drop a bomb to the left of the unit.
     */
    USE_BOMB_AT_LEFT,

    /**
     * Drop a bomb to the right of the unit.
     */
    USE_BOMB_AT_RIGHT,

    /**
     * Drop a bomb below the unit.
     */
    USE_BOMB_AT_BOTTOM,

    /**
     * Drop a bomb above the unit.
     */
    USE_BOMB_AT_TOP,

    /**
     * Drop a bomb at the top-left corner relative to the unit.
     */
    USE_BOMB_AT_TOP_LEFT,

    /**
     * Drop a bomb at the top-right corner relative to the unit.
     */
    USE_BOMB_AT_TOP_RIGHT,

    /**
     * Drop a bomb at the bottom-left corner relative to the unit.
     */
    USE_BOMB_AT_BOTTOM_LEFT,

    /**
     * Drop a bomb at the bottom-right corner relative to the unit.
     */
    USE_BOMB_AT_BOTTOM_RIGHT,

    /**
     * Fire a projectile to the right of the unit.
     */
    USE_PROJECTILE_AT_RIGHT,

    /**
     * Fire a projectile to the left of the unit.
     */
    USE_PROJECTILE_AT_LEFT,

    /**
     * Fire a projectile above the unit.
     */
    USE_PROJECTILE_AT_TOP,

    /**
     * Fire a projectile below the unit.
     */
    USE_PROJECTILE_AT_BOTTOM,

    /**
     * Perform no action.
     */
    NOTHING,

    /**
     * Activate the unit's shield.
     */
    ACTIVATE_SHIELD
}
