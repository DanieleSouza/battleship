package model;

import common.TileType;
import java.util.ArrayList;
import java.util.Random;

public abstract class Player {
    
    protected Field myField;
    protected Field enemyField;
    
    protected ArrayList<Ship> ships;
    
    public Player() {
        myField = new Field();
        enemyField = new Field();
        
        ships = new ArrayList<>();
    }
    
    public Field getMyField() { return myField; }
    public Field getEnemyField() { return enemyField; }
    public ArrayList<Ship> getShips() { return ships; }
    
    public abstract void initializeField();
    public abstract Coordinate play();
    
    public void placeShipsRandomly() {
        Random rn = new Random();
        Coordinate c;
        boolean isHorizontal = true;
        int ship1 = 0, ship2 = 0, ship3 = 0, ship4 = 0;
        
        //generate 4x SHIP1
        while (ship1 < 4) {
            c = new Coordinate(rn.nextInt(10), rn.nextInt(10));
            isHorizontal = rn.nextBoolean();
            if (myField.placeShip(TileType.SHIP1, c, isHorizontal)) {
                ships.add(new Ship(c, TileType.SHIP1.asInt(), isHorizontal));
                ship1++;
            }
        }
        
        //generate 3x SHIP2
        while (ship2 < 3) {
            c = new Coordinate(rn.nextInt(10), rn.nextInt(10));
            isHorizontal = rn.nextBoolean();
            if (myField.placeShip(TileType.SHIP2, c, isHorizontal)) {
                ships.add(new Ship(c, TileType.SHIP2.asInt(), isHorizontal));
                ship2++;
            }
        }
        
        //generate 2x SHIP3
        while (ship3 < 2) {
            c = new Coordinate(rn.nextInt(10), rn.nextInt(10));
            isHorizontal = rn.nextBoolean();
            if (myField.placeShip(TileType.SHIP3, c, isHorizontal)) {
                ships.add(new Ship(c, TileType.SHIP3.asInt(), isHorizontal));
                ship3++;
            }
        }
        
        //generate 1x SHIP4
        while (ship4 < 1) {
            c = new Coordinate(rn.nextInt(10), rn.nextInt(10));
            isHorizontal = rn.nextBoolean();
            if (myField.placeShip(TileType.SHIP4, c, isHorizontal)) {
                ships.add(new Ship(c, TileType.SHIP4.asInt(), isHorizontal));
                ship4++;
            }
        }
    }
    
    public abstract void onMiss();
    public abstract void onHit(Coordinate c);
    public abstract void onSink(Coordinate c, Ship s);
}
