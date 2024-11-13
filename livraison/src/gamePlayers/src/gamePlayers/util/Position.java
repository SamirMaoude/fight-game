package gamePlayers.util;

public class Position implements Cloneable {
    private int row;
    private int col;

    public Position(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Position coordonate can not be negative");
        }
        this.row = row;
        this.col = col;
    }

    public Position(Position position) {
        this(position.getRow(), position.getCol());
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void moveLeft() {
        if (this.col - 1 < 0) {
            throw new IllegalArgumentException("Position coordonate can not be negative");
        }
        this.col -= 1;
    }

    public void moveRight() {
        this.col += 1;
    }

    public void moveTop() {
        if (this.row - 1 < 0) {
            throw new IllegalArgumentException("Position coordonate can not be negative");
        }

        this.row -= 1;
    }

    public void moveBottom() {
        this.row += 1;
    }

    @Override
    public boolean equals(Object obj) {
        Position p = (Position) obj;
        return this.row == p.getRow() && this.col == p.getCol();
    }

    @Override
    public Position clone() {
        Position clone = null;
        try {
            clone = (Position) super.clone();
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        clone.row = this.row;
        clone.col = this.col;
        return clone;
    }

    @Override
    public String toString(){
        return "Ligne " + this.row + " Colonne " + this.col;
    }

 
}
