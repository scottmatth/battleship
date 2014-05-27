public class Ship {
    private ShipType type;
    private  int hitsTaken;

    public Ship(ShipType type) {
        this.type = type;
        this.hitsTaken = 0;
    }

    public int getSize() {
        return type.getSize();
    }

    public String getDisplay() {
        return type.display();
    }

    public boolean hit() {
        hitsTaken++;

        return amIDead();
    }

    public boolean amIDead() {
        return hitsTaken >= type.getSize();
    }


    public static enum ShipType {
        AIRCRAFT_CARRIER("Aircraft Carrier", 5),
        BATTLESHIP("Battleship", 4),
        DESTROYER("Destroyer", 3),
        SUBMARINE("Submarine", 3),
        PATROL("Patrol Boat", 2);

        private int size;
        private String displayName;

        ShipType(String displayName, int size) {
            this.displayName = displayName;
            this.size = size;
        }

        public String display() {
            return displayName;
        }

        public int getSize() {
            return size;
        }
    }
}
