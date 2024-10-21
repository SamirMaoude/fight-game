package gamePlayers.fighters;

/**
 * Unit class for defining the base of any unit type.
 */
public class Unit {
    /**
     * Name of the unit instance.
     */
    private String name;

    /**
     * Constructor of the class.
     * @param name name of the unit.
     */
    public Unit(String name) {
        this.name = name;
    }

    /**
     * Retrieves the current name of the unit.
     * @return unit name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Updates the name of the unit.
     * 
     * It verifies the the name is not empty. Additionally, it capitalizes the string.
     * 
     * @param name new name of the unit.
     */
    public void setName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("You cannot provide an empty string as unit name!");
        }

        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        this.name = name;
    }
}
