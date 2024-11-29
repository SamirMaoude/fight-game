package gamePlayers.objects;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import gamePlayers.util.Player;
import gamePlayers.util.Position;
import junit.framework.TestCase;


/**
 * Classe de test pour la classe Bomb.
 */
public class BombTest extends TestCase{

    private Bomb bomb1;
    private Bomb bomb2;
    private Bomb bomb3;
    private Position position1;
    private Player player1;

    @Before
    public void setUp() {
        position1 = new Position(1, 2);
        player1 = null;

        bomb1 = new Bomb(position1, 50, player1, 5);
        bomb2 = new Bomb(position1, 50, player1, 3);
        bomb3 = new Bomb(position1, 30, player1, 0);
    }

    @Test
    public void testConstructor() {
        Assert.assertNotNull("La bombe ne devrait pas être null. Résultat attendu: un objet Bomb.",bomb1);
        Assert.assertEquals(5, bomb1.getTimeBeforeExplosion());

        

        IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
            new Bomb(position1, 50, player1, -1);
        });

        assertEquals("Le message d'exception est incorrect. Résultat attendu: 'You cannot provide a value under 0 as bomb time.'","You cannot provide a value under 0 as bomb time.", exception.getMessage());
               

    }

    @Test
    public void testGetTimeBeforeExplosion() {
        Assert.assertEquals(5, bomb1.getTimeBeforeExplosion());
        Assert.assertEquals(3, bomb2.getTimeBeforeExplosion());
    }

    @Test
    public void testDecreaseTime() {
        bomb1.decreaseTime();
        Assert.assertEquals(4, bomb1.getTimeBeforeExplosion());

        bomb2.decreaseTime();
        Assert.assertEquals(2, bomb2.getTimeBeforeExplosion());
    }

    

    @Test
    public void testClone() throws CloneNotSupportedException {
        Bomb clonedBomb = bomb1.clone();
        Assert.assertEquals(bomb1.getDamage(), clonedBomb.getDamage());
        Assert.assertEquals(bomb1.getPosition(), clonedBomb.getPosition());
        Assert.assertEquals(bomb1.getTimeBeforeExplosion(), clonedBomb.getTimeBeforeExplosion());
        Assert.assertNull("Le joueur de la bombe clonée devrait être null. Résultat attendu: null, résultat obtenu: ", clonedBomb.getOwner());
    }

    @Test
    public void testSetTimeBeforeExplosion() {
        bomb1.setTimeBeforeExplosion(10);
        assertEquals(10, bomb1.getTimeBeforeExplosion());
    }

}
