package gamePlayers.util;

/**
 * Enum representing the types of entities in the game.
 */
public enum EntityType {
    /**
     * A unit, typically representing a player or character in the game.
     */
    UNIT,

    /**
     * A bomb entity that can be used to damage opponents.
     */
    BOMB,

    /**
     * A mine entity that can be triggered to cause damage.
     */
    MINE,

    /**
     * A projectile entity used for ranged attacks.
     */
    PROJECTILE,

    /**
     * A wall entity representing an obstacle on the game field.
     */
    WALL,

    /**
     * A pellet entity, often used for energy or health restoration.
     */
    PELLET
}
