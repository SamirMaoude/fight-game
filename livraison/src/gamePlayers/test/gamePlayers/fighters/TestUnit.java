package gamePlayers.fighters;

import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TestUnit extends TestCase {
    @Test
    public void testSetName() {
        Unit unit = new Unit("Toto");
        unit.setName("titi");
        assertEquals(unit.getName(), "Titi");
    }

    @Test
    public void testSetEmptyName() {
        Unit unit = new Unit("Toto");
        assertThrows(IllegalArgumentException.class, () -> {
            unit.setName("");
        });
    }
}
