package common;

public enum TileType {
    WATER(0),
    SHIP1(1),
    SHIP2(2),
    SHIP3(3),
    SHIP4(4),
    HIT(8),
    MISS(9);
    
    private final int asInt;
    
    private TileType(int asInt) {
        this.asInt = asInt;
    }

    public int asInt() {
        return asInt;
    }
}