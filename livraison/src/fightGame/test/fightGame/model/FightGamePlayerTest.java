package fightGame.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fightGame.model.strategy.FightGamePlayerStrategy;
import fightGame.model.strategy.HumainStrategy;
import fightGame.model.strategy.aiAlgorithms.*;
import gamePlayers.util.Position;
import gamePlayers.fighters.Unit;
import junit.framework.TestCase;

public class FightGamePlayerTest  extends TestCase{
    private GameBoard gameBoard;
    private FightGamePlayerStrategy strategy;
    private FightGamePlayer player1;
    private FightGamePlayer player2;
    private FightGamePlayer player3;
    private FightGamePlayer player4;

    @Before
    public void setUp(){
        gameBoard = new GameBoard(4, 4);
        strategy = new RandomStrategy();
        player1 = new FightGamePlayer(gameBoard, "Unit", 0, strategy);
        player2 = new FightGamePlayer(gameBoard, "Unit", 0);
        player3 = new FightGamePlayer(gameBoard,2);
        player4 = new FightGamePlayer(player1);
    }

    @Test
    public void testConstructor(){
        Assert.assertNotNull(player1.getUnit());
        Assert.assertEquals(player1.getName(), player1.getUnit().getName());
        Assert.assertEquals(player1.getStrategy(), strategy);
        Assert.assertNotNull(player1.getGameBoardProxy());
        Assert.assertEquals(0, player1.getPlayerIndex());
        Assert.assertNull(player2.getStrategy());
        Assert.assertEquals(2, player3.getPlayerIndex());
        Assert.assertEquals("2", player3.getName());
        Assert.assertNotNull(player3.getGameBoardProxy());
        Assert.assertEquals(0, player4.getPlayerIndex());
        Assert.assertNotNull(player4.getUnit());
        Assert.assertEquals("Unit", player4.getUnit().getName());
    }

    @Test
    public void testGetGameBoardProxy(){
        Assert.assertNotNull(player1.getGameBoardProxy());
        GameBoardProxy proxy = new GameBoardProxy(gameBoard, player1);
        Assert.assertNotEquals(proxy, player1.getGameBoardProxy());
        player1.setGameBoardProxy(proxy);
        Assert.assertEquals(proxy, player1.getGameBoardProxy());
    }

    @Test
    public void testSetGameBoardProxy(){
        GameBoardProxy proxy = new GameBoardProxy(gameBoard, null);
        Assert.assertNotEquals(proxy, player1.getGameBoardProxy());
        player1.setGameBoardProxy(proxy);
        Assert.assertEquals(proxy, player1.getGameBoardProxy());
    }

    @Test
    public void testClone() throws CloneNotSupportedException{
        FightGamePlayer p = (FightGamePlayer)player1.clone();
        Assert.assertEquals(player1.getName(), p.getName());
        Assert.assertEquals(player1.getPlayerIndex(), p.getPlayerIndex());
        Assert.assertEquals(player1.getStrategy(), p.getStrategy());
        Assert.assertEquals(player1.getUnit(), p.getUnit());
        Assert.assertEquals(player1.getGameBoardProxy(), p.getGameBoardProxy());
    }

    @Test
    public void testGetUnit(){
        Unit u = new Unit(null, "Test", player1, 100, null, null, null, 0);
        Assert.assertNotEquals(u, player1.getUnit());
        player1.setUnit(u);
        Assert.assertEquals(u, player1.getUnit());
        player1.setUnit(null);
        Assert.assertNull(player1.getUnit());
    }

    @Test 
    public void testToString(){
        Assert.assertEquals("Unit", player1.toString());
        Assert.assertEquals("Unit", player2.toString());
    }

    @Test
    public void testPlay(){
        player1.getUnit().setPosition(new Position(0,0));
        player2.getUnit().setPosition(new Position(3,3));
        Assert.assertEquals(FightGameAction.class, player1.play().getClass());
        Assert.assertEquals(FightGameAction.class, player2.play().getClass());  
    }

    @Test
    public void testSetStrategy(){
        MinimaxStrategy s = new MinimaxStrategy();
        HumainStrategy h = new HumainStrategy();
        Assert.assertNotEquals(null, player1.getStrategy());
        Assert.assertEquals(RandomStrategy.class, player1.getStrategy().getClass());
        Assert.assertNotEquals(MinimaxStrategy.class, player1.getStrategy().getClass());
        player1.setStrategy(s);
        Assert.assertNotEquals(null, player1.getStrategy());
        Assert.assertNotEquals(RandomStrategy.class, player1.getStrategy().getClass());
        Assert.assertEquals(MinimaxStrategy.class, player1.getStrategy().getClass());
        player1.setStrategy(h);
        Assert.assertNotEquals(null, player1.getStrategy());
        Assert.assertNotEquals(RandomStrategy.class, player1.getStrategy().getClass());
        Assert.assertNotEquals(MinimaxStrategy.class, player1.getStrategy().getClass());
        Assert.assertEquals(HumainStrategy.class, player1.getStrategy().getClass());
    }

    @Test
    public void testGetPlayerIndex(){
        Assert.assertEquals(0, player1.getPlayerIndex());
        Assert.assertEquals(0, player2.getPlayerIndex());
        Assert.assertEquals(2, player3.getPlayerIndex());
        Assert.assertEquals(0, player4.getPlayerIndex());
    }

    @Test
    public void testGetStrategy(){
        Assert.assertNull(player2.getStrategy());
        Assert.assertEquals(player1.getStrategy(), strategy);
        Assert.assertEquals(player3.getStrategy(), null);

    }

    @Test
    public void testEquals(){
        Assert.assertEquals(player1, player4);
        Assert.assertEquals(player1, player2);
    }

    @Test
    public void testSetUnit(){
        Unit u = new Unit(null, "Test", player1, 100, null, null, null, 10);
        player1.setUnit(u);
        Assert.assertEquals(u, player1.getUnit());
        Assert.assertNotEquals(u, player2.getUnit());
        player2.setUnit(u);
        Assert.assertEquals(u, player2.getUnit());
    }
    
}
