package scott.game.battleship;

import org.junit.Assert;
import org.junit.Test;
import scott.game.battleship.Ship;

public class ShipTest {

    @Test
    public void testShip() {
        Ship testAircraft = new Ship(Ship.ShipType.AIRCRAFT_CARRIER);

        Assert.assertEquals(5, testAircraft.getSize());
        Assert.assertEquals("Aircraft Carrier", testAircraft.getDisplay());
    }

    @Test
    public void testSinkShip() {
        Ship testVictim = new Ship(Ship.ShipType.PATROL);

        Assert.assertFalse(testVictim.hit());
        Assert.assertTrue(testVictim.hit());

        Ship testBigVictim = new Ship(Ship.ShipType.AIRCRAFT_CARRIER);
        Assert.assertFalse(testBigVictim.hit());
        Assert.assertFalse(testBigVictim.hit());
        Assert.assertFalse(testBigVictim.hit());
        Assert.assertFalse(testBigVictim.hit());
        Assert.assertTrue(testBigVictim.hit());
    }

}
