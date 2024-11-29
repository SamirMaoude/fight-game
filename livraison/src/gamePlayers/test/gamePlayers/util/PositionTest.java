package gamePlayers.util;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class PositionTest extends TestCase{

    private Position position1;
    private Position position2;
    private Position position3;
    private Position position4;

    @Before
    public void setUp() {
        position1 = new Position(1, 2);
        position2 = new Position(1, 2);
        position3 = new Position(3, 4);
        position4 = new Position(0, 0);
    }

    @Test
public void testConstructor() {
    
    assertNotNull("Le constructeur n'a pas bien initialisé la position. La position devrait être différente de null.", position1);
    assertEquals("La ligne de la position n'est pas correcte. Attendu : 1, obtenu : " + position1.getRow(), 1, position1.getRow());
    assertEquals("La colonne de la position n'est pas correcte. Attendu : 2, obtenu : " + position1.getCol(), 2, position1.getCol());
    try {
        new Position(-1, 2);
        fail("Le constructeur devrait lancer une exception IllegalArgumentException pour des coordonnées négatives. Aucun exception n'a été lancée.");
    } catch (IllegalArgumentException e) {
        assertEquals("Le message d'exception ne correspond pas. Attendu : 'Position coordinates can't be negative', obtenu : '" + e.getMessage() + "'",
                "Position coordinates can't be negative", e.getMessage());
    }

    try {
        new Position(1, -2);
        fail("Le constructeur devrait lancer une exception IllegalArgumentException pour des coordonnées négatives. Aucun exception n'a été lancée.");
    } catch (IllegalArgumentException e) {
        assertEquals("Le message d'exception ne correspond pas. Attendu : 'Position coordinates can't be negative', obtenu : '" + e.getMessage() + "'",
                "Position coordinates can't be negative", e.getMessage());
    }
}

@Test
public void testGetRow() {
    assertEquals("La méthode getRow ne renvoie pas la ligne correcte. Attendu : 1, obtenu : " + position1.getRow(), 1, position1.getRow());
}

@Test
public void testGetCol() {
    assertEquals("La méthode getCol ne renvoie pas la colonne correcte. Attendu : 2, obtenu : " + position1.getCol(), 2, position1.getCol());
}

@Test
public void testMoveLeft() {
    position1.moveLeft();
    assertEquals("Après le déplacement à gauche, la ligne devrait rester inchangée. Attendu : 1, obtenu : " + position1.getRow(), 1, position1.getRow());
    assertEquals("Après le déplacement à gauche, la colonne devrait être décrémentée de 1. Attendu : 1, obtenu : " + position1.getCol(), 1, position1.getCol());
}

@Test
public void testMoveRight() {
    position1.moveRight();
    assertEquals("Après le déplacement à droite, la ligne devrait rester inchangée. Attendu : 1, obtenu : " + position1.getRow(), 1, position1.getRow());
    assertEquals("Après le déplacement à droite, la colonne devrait être incrémentée de 1. Attendu : 3, obtenu : " + position1.getCol(), 3, position1.getCol());
}


@Test
public void testMoveTop() {
    position1.moveTop();
    assertEquals("Après le déplacement vers le haut, la ligne devrait être décrémentée de 1. Attendu : 0, obtenu : " + position1.getRow(), 0, position1.getRow());
    assertEquals("Après le déplacement vers le haut, la colonne devrait rester inchangée. Attendu : 2, obtenu : " + position1.getCol(), 2, position1.getCol());
}

@Test
public void testMoveBottom() {
    position1.moveBottom();
    assertEquals("Après le déplacement vers le bas, la ligne devrait être incrémentée de 1. Attendu : 2, obtenu : " + position1.getRow(), 2, position1.getRow());
    assertEquals("Après le déplacement vers le bas, la colonne devrait rester inchangée. Attendu : 2, obtenu : " + position1.getCol(), 2, position1.getCol());
}

@Test
public void testEquals() {
    assertTrue("La méthode equals devrait retourner true pour le même objet. Attendu : true, obtenu : " + position1.equals(position1), position1.equals(position1));
    assertTrue("La méthode equals devrait retourner true pour des positions égales. Attendu : true, obtenu : " + position1.equals(position2), position1.equals(position2));
    assertFalse("La méthode equals devrait retourner false pour des positions différentes. Attendu : false, obtenu : " + position1.equals(position4), position1.equals(position4));
    assertFalse("La méthode equals devrait retourner false pour un objet d'une classe différente. Attendu : false, obtenu : " + position1.equals("not a position"), position1.equals("not a position"));
}


    @Test
    public void testHashCode() {
        assertTrue("Le hashCode doit être identique pour des positions égales", position1.hashCode() == position2.hashCode());
        assertFalse("Le hashCode ne doit pas être identique pour des positions différentes", position1.hashCode() == position3.hashCode());
     }

     @Test
     public void testClone() throws CloneNotSupportedException {
         Position clonedPosition = position1.clone();
         assertNotNull("La méthode clone devrait retourner un objet non nul. Attendu : non nul, obtenu : " + clonedPosition, clonedPosition);
         assertTrue("L'objet cloné ne doit pas être égal à l'objet original. Attendu : différent, obtenu : " + (position1 == clonedPosition), position1 != clonedPosition);
         assertTrue("L'objet cloné doit être égal à l'objet original. Attendu : true, obtenu : " + position1.equals(clonedPosition), position1.equals(clonedPosition));
     }
     

    @Test
    public void testToString() {
        String expectedString = "Ligne 1 Colonne 2";
        assertEquals("La méthode toString ne renvoie pas la chaîne attendue", expectedString, position1.toString());
    }

    @Test
    public void testDistanceTo() {
        double distance = position1.distanceTo(position3);
        double expectedDistance = Math.sqrt(Math.pow(1 - 3, 2) + Math.pow(2 - 4, 2)); // Calcul direct
        assertEquals("La méthode distanceTo ne renvoie pas la distance attendue", expectedDistance, distance, 0.0001);
    }
}
