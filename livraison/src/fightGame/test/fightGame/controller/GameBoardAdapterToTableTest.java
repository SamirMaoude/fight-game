package fightGame.controller;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import fightGame.model.FightGamePlayer;
import fightGame.model.GameBoard;
import fightGame.model.GameBoardProxy;
import fightGame.model.objects.Wall;
import gamePlayers.util.*;
import gamePlayers.objects.*;
import junit.framework.TestCase;

public class GameBoardAdapterToTableTest extends TestCase {

    private GameBoard gameBoard1;
    private GameBoard gameBoard2;
    private GameBoardProxy proxy1;
    private GameBoardProxy proxy2;
    private FightGamePlayer player1;
    private FightGamePlayer player2;
    private GameBoardAdapterToTable adapter1;
    private GameBoardAdapterToTable adapter2;
    private String Unit2FilePath = "src/fightGame/src/fightGame/view/img/mainUnit.png";
    private String Unit1FilePath = "src/fightGame/src/fightGame/view/img/unit.png";
    private String PelletFilePath = "src/fightGame/src/fightGame/view/img/pellet.jpg";
    private String BlancFilePath ="src/fightGame/src/fightGame/view/img/blanc.png";
    private String BombFilePath = "src/fightGame/src/fightGame/view/img/bombicon.png";
    private String WallFilePath = "src/fightGame/src/fightGame/view/img/wall.png";
    private String MineFilePath = "src/fightGame/src/fightGame/view/img/mines.jpg";

    Position p1 = new Position(0,0);
    Position p2 = new Position(1,1);
    Position p3 = new Position(2,2);
    Position p4 = new Position(3,3);
    Position p5 = new Position(3,0);
    Position p6 = new Position(1,2);


    Wall wall = new Wall(null);
    Pellet pellet = new Pellet(null, 40);
    Bomb bomb ;
    Mine mine;

    @Before
    public void setUp() {
        
        gameBoard1 = new GameBoard(4, 4);
        FightGamePlayer player1 = new FightGamePlayer(gameBoard1, "TestUnit1", 0);
        FightGamePlayer player2 = new FightGamePlayer(gameBoard1, "TestUnit2", 1);

        bomb = new Bomb(p4, 50, player1, 4);
        mine = new Mine(p6, 0, player1);

        gameBoard1.addEntity(player1.getUnit(), p1);
        gameBoard1.addEntity(player2.getUnit(), p2);
        gameBoard1.addEntity(pellet, p3);
        gameBoard1.addEntity(bomb, p4);
        gameBoard1.addEntity(wall, p5);
        gameBoard1.addEntity(mine, p6);

        proxy1 = new GameBoardProxy(gameBoard1, player1);
        proxy2 = new GameBoardProxy(gameBoard1, player2);

        adapter1 = new GameBoardAdapterToTable(gameBoard1,proxy1);
        adapter2 = new GameBoardAdapterToTable(gameBoard1, null);


    }

    @Test
    public void testConstructor(){
        assertTrue("Le constructeur n'associe par l'argument gameBoard à l'attribut correspondant", adapter1.getGameBoard().equals(gameBoard1));
        assertTrue("Le constructeur n'associe par l'argument proxy à l'attribut correspondant", adapter1.getProxy().equals(proxy1));
        
        assertTrue("Le constructeur n'associe par l'argument gameBoard à l'attribut correspondant", adapter2.getGameBoard().equals(gameBoard1));
        assertTrue("Le constructeur n'associe par l'argument proxy à l'attribut correspondant", adapter2.getProxy() == null);
        

    }

    @Test
    public void testGetColumnCount(){
        assertTrue("", adapter1.getColumnCount() == gameBoard1.getCols());
        assertTrue("", adapter2.getColumnCount() == gameBoard1.getCols());

    }

    @Test
    public void testGetRowCount(){
        assertTrue("",adapter1.getRowCount() == gameBoard1.getRows());
        assertTrue("",adapter2.getRowCount() == gameBoard1.getRows());

    }
    //icons.add(new ImageIcon());

    @Test
    public void testGetValueAt(){
        ArrayList l1 = (ArrayList)adapter1.getValueAt(0, 0);
        ImageIcon value1 = (ImageIcon)l1.get(0);

        ArrayList l2 = (ArrayList)adapter1.getValueAt(1, 1);
        ImageIcon value2 = (ImageIcon)l2.get(0);

        ArrayList l3 = (ArrayList)adapter1.getValueAt(2, 2);
        ImageIcon value3 = (ImageIcon)l3.get(0);

        ArrayList l4 = (ArrayList)adapter1.getValueAt(3, 3);
        ImageIcon value4 = (ImageIcon)l4.get(0);

        ArrayList l5 = (ArrayList)adapter1.getValueAt(3, 0);
        ImageIcon value5 = (ImageIcon)l5.get(0);

        ArrayList l6 = (ArrayList)adapter1.getValueAt(1, 2);
        ImageIcon value6 = (ImageIcon)l6.get(0);

        assertEquals(l1.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value1.getDescription().equals(Unit2FilePath));


        l1 = (ArrayList)adapter2.getValueAt(0, 0);
        value1 = (ImageIcon)l1.get(0);

        assertEquals(l1.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value1.getDescription().equals(Unit1FilePath));


        assertEquals(l2.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value2.getDescription().equals(Unit1FilePath));
        
        l2 = (ArrayList)adapter2.getValueAt(1, 1);
        value2 = (ImageIcon)l2.get(0);

        assertEquals(l2.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value2.getDescription().equals(Unit1FilePath));

        assertEquals(l3.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value3.getDescription().equals(PelletFilePath));

        l3 = (ArrayList)adapter2.getValueAt(2, 2);
        value3 = (ImageIcon)l3.get(0);

        assertEquals(l3.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value3.getDescription().equals(PelletFilePath));


        assertEquals(l4.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value4.getDescription().equals(BombFilePath));

        l4 = (ArrayList)adapter2.getValueAt(3, 3);
        value4 = (ImageIcon)l4.get(0);

        assertEquals(l4.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value4.getDescription().equals(BombFilePath));


        assertEquals(l5.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value5.getDescription().equals(WallFilePath));

        l5 = (ArrayList)adapter2.getValueAt(3, 0);
        value5 = (ImageIcon)l5.get(0);

        assertEquals(l5.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value5.getDescription().equals(WallFilePath));

        assertEquals(l6.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value6.getDescription().equals(MineFilePath));

        l6 = (ArrayList)adapter2.getValueAt(1, 2);
        value6 = (ImageIcon)l6.get(0);

        assertEquals(l6.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value6.getDescription().equals(MineFilePath));


        Wall wall1 = new Wall(null);
        gameBoard1.addEntity(wall1, p4);

        ArrayList l7 = (ArrayList)adapter1.getValueAt(3, 3);
        ImageIcon value7 = (ImageIcon)l7.get(0);
        ImageIcon value8 = (ImageIcon)l7.get(1);
        assertEquals(l7.size(), 2);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value7.getDescription().equals(WallFilePath));
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value8.getDescription().equals(BombFilePath));

        ArrayList l8 = (ArrayList)adapter1.getValueAt(1, 3);
        ImageIcon value9 = (ImageIcon)l8.get(0);
        assertEquals(l8.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value9.getDescription().equals(BlancFilePath));

        Bomb bomb2 = new Bomb(null, 10, player2, 10);
        gameBoard1.addEntity(bomb2, p3);

        ArrayList l9 = (ArrayList)adapter1.getValueAt(2, 2);
        ImageIcon value10 = (ImageIcon)l9.get(0);
        //Il a deux choses a cette position mais player1 ne verra qu'un. Cas ou la visibilité de la bomb est sur false bien sûr
        assertEquals(l9.size(), 1);
        assertTrue ("La méthode ne renvoie pas la bonne valeur", value10.getDescription().equals(PelletFilePath));

        //Avec adapter2 on verra les deux 
        ArrayList l10 = (ArrayList)adapter2.getValueAt(2, 2);
        //Il a deux chose a cette position mais player1 ne verra qu'un. Cas ou la visibilité de la bomb est sur false bien sûr
        assertEquals(l10.size(), 2);

        Pellet pellet1 = new Pellet(null, 10);
        gameBoard1.addEntity(pellet1, p3);

        l9 = (ArrayList)adapter1.getValueAt(2, 2);
        value10 = (ImageIcon)l9.get(0);
        //Il a trois choses a cette position mais player1 ne verra que deux. Cas ou la visibilité de la bomb est sur false bien sûr
        assertEquals(l9.size(), 2);

        l9 = (ArrayList)adapter2.getValueAt(2, 2);
        value10 = (ImageIcon)l9.get(0);
        assertEquals(l9.size(), 3);
    }
}
