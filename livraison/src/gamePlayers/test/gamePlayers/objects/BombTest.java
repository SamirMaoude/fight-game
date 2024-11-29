package gamePlayers.objects;


import static org.junit.Assert.*;

import gamePlayers.util.Position;

/* */

/**
 * Classe de test pour la classe Bomb.
 */
public class BombTest {

    private Bomb bomb1;
    private Bomb bomb2;
    private Bomb bomb3;
    private Position position1;
    private Player player1;

    @BeforeEach
    public void setUp() {
        position1 = new Position(1, 2);
        player1 = null;

        bomb1 = new Bomb(position1, 50, player1, 5);
        bomb2 = new Bomb(position1, 50, player1, 3);
        bomb3 = new Bomb(position1, 30, player1, 0);
    }

    @Test
    public void testConstructor() {
        assertNotNull(bomb1, "La bombe ne devrait pas être null. Résultat attendu: un objet Bomb.");
        assertEquals(5, bomb1.getTimeBeforeExplosion(),
                "Le temps avant l'explosion devrait être de 5. Résultat obtenu: " + bomb1.getTimeBeforeExplosion());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bomb(position1, 50, player1, -1);
        }, "Le constructeur devrait lancer une IllegalArgumentException si le temps avant l'explosion est négatif.");

        assertEquals("You cannot provide a value under 0 as bomb time.", exception.getMessage(),
                "Le message d'exception est incorrect. Résultat attendu: 'You cannot provide a value under 0 as bomb time.'");

    }

    @Test
    public void testGetTimeBeforeExplosion() {
        assertEquals(5, bomb1.getTimeBeforeExplosion(),
                "Le temps avant l'explosion devrait être de 5. Résultat obtenu: " + bomb1.getTimeBeforeExplosion());
        assertEquals(3, bomb2.getTimeBeforeExplosion(),
                "Le temps avant l'explosion devrait être de 3. Résultat obtenu: " + bomb2.getTimeBeforeExplosion());
    }

    @Test
    public void testDecreaseTime() {
        bomb1.decreaseTime();
        assertEquals(4, bomb1.getTimeBeforeExplosion(),
                "Le temps avant l'explosion devrait être réduit à 4. Résultat obtenu: "
                        + bomb1.getTimeBeforeExplosion());

        bomb2.decreaseTime();
        assertEquals(2, bomb2.getTimeBeforeExplosion(),
                "Le temps avant l'explosion devrait être réduit à 2. Résultat obtenu: "
                        + bomb2.getTimeBeforeExplosion());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        Bomb clonedBomb = bomb1.clone();

        assertNotSame(bomb1, clonedBomb,
                "La bombe clonée devrait être un objet différent de l'originale. Résultat attendu: un objet différent, résultat obtenu: "
                        + clonedBomb);

        assertEquals(bomb1.getDamage(), clonedBomb.getDamage(),
                "Les dégâts de la bombe clonée devraient être égaux à ceux de la bombe originale. Résultat attendu: "
                        + bomb1.getDamage() + ", résultat obtenu: " + clonedBomb.getDamage());

        assertEquals(bomb1.getPosition(), clonedBomb.getPosition(),
                "La position de la bombe clonée devrait être égale à celle de la bombe originale. Résultat attendu: "
                        + bomb1.getPosition() + ", résultat obtenu: " + clonedBomb.getPosition());

        assertEquals(bomb1.getTimeBeforeExplosion(), clonedBomb.getTimeBeforeExplosion(),
                "Le temps avant l'explosion de la bombe clonée devrait être égal à celui de la bombe originale. Résultat attendu: "
                        + bomb1.getTimeBeforeExplosion() + ", résultat obtenu: " + clonedBomb.getTimeBeforeExplosion());

        assertNull(clonedBomb.getOwner(),
                "Le joueur de la bombe clonée devrait être null. Résultat attendu: null, résultat obtenu: "
                        + clonedBomb.getOwner());
    }

    @Test
    public void testSetTimeBeforeExplosion() {
        bomb1.setTimeBeforeExplosion(10);
        assertEquals(10, bomb1.getTimeBeforeExplosion(),
                "Le temps avant l'explosion devrait être de 10. Résultat obtenu: " + bomb1.getTimeBeforeExplosion());
    }

}*
