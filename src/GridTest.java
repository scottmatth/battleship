import javafx.geometry.Orientation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GridTest {

    private Grid testGrid;
    private Ship testShip;

    @Before
    @Test
    public void setUp() {
        testGrid = new Grid();
        testShip = new Ship(Ship.ShipType.DESTROYER);
    }

    @Test
    public void testFireMiss() throws SpotTakenException, OutOfBoundsException {

        testGrid.placeShip("B", "2", Orientation.HORIZONTAL, testShip);
        Grid.FireResult result = testGrid.fire("A", "1");

        Assert.assertEquals(Grid.FireResult.MISS, result);
    }

    @Test
    public void testAddShip() throws SpotTakenException, OutOfBoundsException {

        testGrid.placeShip("B", "2", Orientation.HORIZONTAL, testShip);

    }


    @Test
    public void testFireHit() throws SpotTakenException, OutOfBoundsException {

        testGrid.placeShip("B", "2", Orientation.HORIZONTAL, testShip);
        Grid.FireResult hit = testGrid.fire("B", "2");

        Assert.assertEquals(Grid.FireResult.HIT, hit);

    }

    @Test
    public void testKillShip() throws SpotTakenException, OutOfBoundsException {
        testGrid.placeShip("B", "2", Orientation.HORIZONTAL, testShip);

        testGrid.placeShip("D", "3", Orientation.HORIZONTAL, new Ship(Ship.ShipType.AIRCRAFT_CARRIER));
        Grid.FireResult hit1 = testGrid.fire("B", "2");

        Assert.assertEquals(Grid.FireResult.HIT, hit1);
        Grid.FireResult hit2 = testGrid.fire("B", "3");

        Assert.assertEquals(Grid.FireResult.HIT, hit2);
        Grid.FireResult hit3 = testGrid.fire("B", "4");

        Assert.assertEquals(Grid.FireResult.SHIP_DESTROYED, hit3);

    }

    @Test(expected = SpotTakenException.class)
    public void testOverlap() throws SpotTakenException, OutOfBoundsException {
        testGrid.placeShip("B", "2", Orientation.HORIZONTAL, testShip);

        Ship testShip2 = new Ship(Ship.ShipType.PATROL);

        testGrid.placeShip("A", "3", Orientation.VERTICAL, testShip2);

    }


    @Test
    public void testOutOfBounds() throws SpotTakenException, OutOfBoundsException {
        testGrid.placeShip("A", "1", Orientation.HORIZONTAL, testShip);
        try {
            testGrid.placeShip("A", "11", Orientation.HORIZONTAL, testShip);
            Assert.fail();
        } catch (SpotTakenException e) {

        } catch (OutOfBoundsException e) {

        }
        testGrid.placeShip("J", "1", Orientation.HORIZONTAL, testShip);
        try {
            testGrid.placeShip("J", "11", Orientation.HORIZONTAL, testShip);
            Assert.fail();
        } catch (SpotTakenException e) {
        } catch (OutOfBoundsException e) {
        }
        try {
            testGrid.placeShip("K", "1", Orientation.HORIZONTAL, testShip);
            Assert.fail();
        } catch (SpotTakenException e) {

        } catch (OutOfBoundsException e) {
        }
        try {
            testGrid.placeShip("A", "0", Orientation.HORIZONTAL, testShip);
            Assert.fail();
        } catch (SpotTakenException e) {
        } catch (OutOfBoundsException e) {
        }
        testGrid.placeShip("A", "8", Orientation.HORIZONTAL, testShip);
        try {
            testGrid.placeShip("B", "9", Orientation.HORIZONTAL, testShip);
            Assert.fail();
        } catch (SpotTakenException e) {
        } catch (OutOfBoundsException e) {
        }

    }


    @Test
    public void testAllDestroyed() throws SpotTakenException, OutOfBoundsException {
        // 3 Square Ship
        testGrid.placeShip("B", "2", Orientation.HORIZONTAL, testShip);

        // 2 square ship
        Ship testShip2 = new Ship(Ship.ShipType.PATROL);

        testGrid.placeShip("A", "5", Orientation.VERTICAL, testShip2);

        // 4 square ship
        Ship testShip3 = new Ship(Ship.ShipType.BATTLESHIP);

        testGrid.placeShip("B", "6", Orientation.VERTICAL, testShip3);


        Grid.FireResult ship1Fire1 = testGrid.fire("B", "3");
        Grid.FireResult ship1Fire2 = testGrid.fire("B", "2");
        Grid.FireResult ship1Fire3 = testGrid.fire("B", "4");

        Grid.FireResult ship2Fire1 = testGrid.fire("B", "5");
        Grid.FireResult ship2Fire2 = testGrid.fire("A", "5");

        Grid.FireResult ship3Fire1 = testGrid.fire("E", "6");
        Grid.FireResult ship3Fire2 = testGrid.fire("B", "6");
        Grid.FireResult ship3Fire3 = testGrid.fire("D", "6");
        Grid.FireResult ship3Fire4 = testGrid.fire("C", "6");


        Assert.assertEquals(Grid.FireResult.HIT, ship1Fire1);
        Assert.assertEquals(Grid.FireResult.HIT, ship3Fire1);
        Assert.assertEquals(Grid.FireResult.HIT, ship1Fire2);

        Assert.assertEquals(Grid.FireResult.HIT, ship3Fire2);
        Assert.assertEquals(Grid.FireResult.HIT, ship2Fire1);

        Assert.assertEquals(Grid.FireResult.SHIP_DESTROYED, ship1Fire3);
        Assert.assertEquals(Grid.FireResult.HIT, ship3Fire3);
        Assert.assertEquals(Grid.FireResult.SHIP_DESTROYED, ship2Fire2);
        Assert.assertEquals(Grid.FireResult.ALL_DESTROYED, ship3Fire4);
    }
}