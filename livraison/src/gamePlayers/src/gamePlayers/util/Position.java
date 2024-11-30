package gamePlayers.util;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a position in a 2D grid with row and column coordinates.
 */
public class Position implements Cloneable, Serializable {

    private int row;
    private int col;

    /**
     * Constructs a new position with the specified row and column.
     *
     * @param row the row coordinate, must be non-negative
     * @param col the column coordinate, must be non-negative
     * @throws IllegalArgumentException if row or column is negative
     */
    public Position(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Position coordinates can't be negative");
        }
        this.row = row;
        this.col = col;
    }

    /**
     * Constructs a new position as a copy of another position.
     *
     * @param position the position to copy
     */
    public Position(Position position) {
        this(position.getRow(), position.getCol());
    }

    /**
     * Gets the row coordinate.
     *
     * @return the row coordinate
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row coordinate.
     *
     * @param row the new row coordinate
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets the column coordinate.
     *
     * @return the column coordinate
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the column coordinate.
     *
     * @param col the new column coordinate
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Moves the position one step to the left.
     */
    public void moveLeft() {
        this.col -= 1;
    }

    /**
     * Moves the position one step to the right.
     */
    public void moveRight() {
        this.col += 1;
    }

    /**
     * Moves the position one step upward.
     */
    public void moveTop() {
        this.row -= 1;
    }

    /**
     * Moves the position one step downward.
     */
    public void moveBottom() {
        this.row += 1;
    }

    /**
     * Checks if this position is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the object is a position with the same coordinates, false
     *         otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Position p = (Position) obj;
        return this.row == p.getRow() && this.col == p.getCol();
    }

    /**
     * Returns the hash code for this position.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.col);
    }

    /**
     * Creates and returns a copy of this position.
     *
     * @return a cloned position
     * @throws CloneNotSupportedException if cloning is not supported
     */
    @Override
    public Position clone() throws CloneNotSupportedException {
        Position clone = (Position) super.clone();
        clone.row = this.row;
        clone.col = this.col;
        return clone;
    }

    /**
     * Returns a string representation of the position.
     *
     * @return a string in the format "Ligne {row} Colonne {col}"
     */
    @Override
    public String toString() {
        return "Ligne " + this.row + " Colonne " + this.col;
    }

    /**
     * Calculates the Euclidean distance to another position.
     *
     * @param position the target position
     * @return the distance between this position and the target position
     */
    public double distanceTo(Position position) {
        int dx = this.row - position.getRow();
        int dy = this.col - position.getCol();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
