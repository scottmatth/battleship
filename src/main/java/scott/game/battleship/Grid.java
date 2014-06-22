package scott.game.battleship;

import scott.game.battleship.exceptions.OutOfBoundsException;
import scott.game.battleship.exceptions.SpotTakenException;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grid {


    private Map<String, Ship> shipTracker = new HashMap<String, Ship>();

    public enum GridOrientation {HORIZONTAL, VERTICAL}

    public FireResult fire(String a, String one) {

        FireResult attackResults = FireResult.MISS;

        if(shipTracker.containsKey(a+one)) {
            boolean sunk = shipTracker.get(a + one).hit();
            if(sunk) {

                if(areAllDead()) {
                    attackResults = FireResult.ALL_DESTROYED;
                } else {
                    attackResults = FireResult.SHIP_DESTROYED;
                }
            }else{
                attackResults = FireResult.HIT;
            }
        }
        return attackResults;
    }

    private boolean areAllDead() {
        boolean allShipsDead = true;
        for(Ship currentShip:shipTracker.values()) {
            if(!currentShip.amIDead()) {
                allShipsDead = currentShip.amIDead();
                break;
            }
        }
        return allShipsDead;
    }

    public void placeShip(String row, String column, GridOrientation horizontal, Ship ship)
            throws SpotTakenException, OutOfBoundsException {

        Collection<String> availableSpots = null;
            availableSpots = getAvailableSpots(row, column, horizontal, ship);

        if (availableSpots != null && !availableSpots.isEmpty()) {
            for(String spot:availableSpots ) {
                shipTracker.put(spot, ship);
            }
        }
    }

    private Collection<String> getAvailableSpots(String row, String column, GridOrientation orientation, Ship ship)
            throws SpotTakenException, OutOfBoundsException {

        Set<String> potentialPlacementKeys = new HashSet<String>();

        for(int i=0;i<ship.getSize();i++) {
            String newRow = row;
            String newColumn = column;
            switch (orientation) {
            case HORIZONTAL:
                newColumn = String.valueOf(Integer.valueOf(column)+i);
                break;
            case VERTICAL:
                newRow = String.valueOf((char) (row.charAt(0) + i));
                break;
            }
            if(newRow.charAt(0) > "J".charAt(0) ||
               newRow.charAt(0) < "A".charAt(0) ||
                    Integer.valueOf(newColumn) >10 ||
                    Integer.valueOf(newColumn) <1) {
                throw new OutOfBoundsException();
            }
            if(shipTracker.containsKey(newRow + newColumn)) {
                throw new SpotTakenException();
            }
            potentialPlacementKeys.add(newRow+newColumn);
        }

        return potentialPlacementKeys;
    }

    public static enum FireResult {
        HIT,
        MISS,
        SHIP_DESTROYED,
        ALL_DESTROYED
    }

}
