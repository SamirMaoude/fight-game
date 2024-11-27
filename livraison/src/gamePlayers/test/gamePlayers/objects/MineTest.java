package gamePlayers.objects;
import gamePlayers.util.Position;
import gamePlayers.util.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Mine.
 */
public class MineTest {

    private Mine mine1;
    private Position position1;
    private Player player1;

    @BeforeEach
    public void setUp() {
        position1 = new Position(3, 4); 
        player1 = null;        
        
        
        mine1 = new Mine(position1, 150, player1); 
    }

    @Test
    public void testConstructor() {
        assertNotNull(mine1, "La mine ne devrait pas être nulle.");
        assertEquals(150, mine1.getDamage(), 
                     "La valeur des dégâts de la mine devrait être de 150. Résultat obtenu: " + mine1.getDamage());
        assertEquals(position1, mine1.getPosition(),
                     "La position de la mine devrait être " + position1 + ". Résultat obtenu: " + mine1.getPosition());
    }

    @Test
    public void testUse() {
        assertDoesNotThrow(() -> mine1.use(),
                           "La méthode 'use' ne devrait pas lever d'exception. Résultat attendu: aucune exception.");
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        Mine clonedMine = mine1.clone();
        assertNotSame(mine1, clonedMine, 
                      "La mine clonée devrait être un objet différent de l'originale.résultat obtenu: " + clonedMine);
        assertEquals(mine1.getDamage(), clonedMine.getDamage(),
                     "Les dégâts de la mine clonée devraient être égaux à ceux de la mine originale. Résultat attendu: "
                     + mine1.getDamage() + ", résultat obtenu: " + clonedMine.getDamage());
        assertEquals(mine1.getPosition(), clonedMine.getPosition(),
                     "La position de la mine clonée devrait être égale à celle de la mine originale. Résultat attendu: "
                     + mine1.getPosition() + ", résultat obtenu: " + clonedMine.getPosition());
        assertNull(clonedMine.getOwner(), 
                   "Le joueur de la mine clonée devrait être null. Résultat attendu: null, résultat obtenu: " + clonedMine.getOwner());
    }

    @Test
    public void testSetDamage() {
        mine1.setDamage(200);
        assertEquals(200, mine1.getDamage(),
                     "Les dégâts de la mine devraient être de 200 après modification. Résultat obtenu: " + mine1.getDamage());
    }
}
