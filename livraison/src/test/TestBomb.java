package gamePlayers.fighters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

public class TestBomb {
    private Bomb bomb;

    @BeforeEach
    void setUp(){
        bomb = new Bomb(20);
    }

    @Test
    void testGetTimeBeforeExplosion(){
        assertEquals(20,bomb.getTimeBeforeExplosion());
    }

    @Test
    void testUpdateTimeBeforeExplosion(){
        bomb.updateTimeBeforeExplosion();
        assertEquals(19,bomb.getTimeBeforeExplosion());

        bomb.updateTimeBeforeExplosion();
        assertEquals(18,bomb.getTimeBeforeExplosion());
    }

    @Test
    void testDetonate(){
        for(int i = 0; i < 20; i++){
            bomb.updateTimeBeforeExplosion();
        }
        bomb.testDetonate();
        assertEquals(0,bomb.getTimeBeforeExplosion());
    }
    
}
