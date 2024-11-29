/*package gamePlayers.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gamePlayers.util.Player;
import gamePlayers.util.Position;

import static org.junit.Assert.*;


 * Classe de test pour la classe Mine.

public class MineTest extends TestCase {

    private Mine mine1;
    private Position position1;
    private Player player1;

    @Before
    public void setUp() {
        position1 = new Position(3, 4); 
        player1 = null;        
        mine1 = new Mine(position1, 150, player1); 
    }

    @Test
    public void testConstructor() {
        assertNotNull("La mine ne devrait pas être nulle.", mine1);
        assertEquals("La valeur des dégâts de la mine devrait être de 150.", 
                     150, mine1.getDamage());
        assertEquals("La position de la mine devrait être correcte.", 
                     position1, mine1.getPosition());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        Mine clonedMine = mine1.clone();
        assertNotSame("La mine clonée devrait être un objet différent de l'originale.", 
                      mine1, clonedMine);
        assertEquals("Les dégâts de la mine clonée devraient être égaux à ceux de la mine originale.", 
                     mine1.getDamage(), clonedMine.getDamage());
        assertEquals("La position de la mine clonée devrait être égale à celle de la mine originale.", 
                     mine1.getPosition(), clonedMine.getPosition());
        assertNull("Le joueur de la mine clonée devrait être null.", 
                   clonedMine.getOwner());
    }

    @Test
    public void testSetDamage() {
        mine1.setDamage(200);
        assertEquals("Les dégâts de la mine devraient être de 200 après modification.", 
                     200, mine1.getDamage());
    }
}
     */

