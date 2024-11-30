package fightGame.model.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gamePlayers.util.EntityType;
import gamePlayers.util.Position;
import junit.framework.TestCase;

public class WallTest extends TestCase {

    private Wall wall;
    private Position position;

    @Before
    public void setUp(){
        position = new Position(1,1);
        wall = new Wall(position);
    }

    @Test
    public void testConstructor(){
        Assert.assertEquals(position, wall.getPosition());
        Assert.assertEquals(EntityType.WALL, wall.getType());
        Assert.assertNull(wall.getOwner());
    }

    @Test
    public void testClone() throws CloneNotSupportedException{
        Wall clone = wall.clone();
        Assert.assertEquals(clone.getPosition(), wall.getPosition());
        Assert.assertEquals(clone.getType(), wall.getType());
        Assert.assertNull(clone.getOwner());
    }

}
