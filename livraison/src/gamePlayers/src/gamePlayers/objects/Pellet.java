package gamePlayers.objects;

import gamePlayers.AbstractGameEntity;
import gamePlayers.util.EntityType;
import gamePlayers.util.Position;

/**
 * Represents a pellet object in the game.
 * Pellets provide energy to units when they are collected.
 */
public class Pellet extends AbstractGameEntity {

    private int energy;

    /**
     * Constructor for creating a new Pellet instance.
     * 
     * @param position The position of the pellet on the game field.
     * @param energy   The energy value that the pellet provides.
     */
    public Pellet(Position position, int energy) {
        super(EntityType.PELLET, position, null);
        this.energy = energy;
    }

    /**
     * Retrieves the energy provided by the pellet.
     * 
     * @return The energy value of the pellet.
     */
    public int getEnergy() {
        return this.energy;
    }

    /**
     * Destroys the pellet, removing it from the game field by setting its position
     * to null.
     */
    @Override
    public void destroy() {
        this.position = null;
    }

    /**
     * Creates a clone of the current pellet.
     * 
     * @return A new instance of Pellet, identical to the current one.
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Pellet clone() throws CloneNotSupportedException {
        Position clonedPosition = this.getPosition() != null ? new Position(this.getPosition()) : null;

        return new Pellet(clonedPosition, this.getEnergy());
    }
}
