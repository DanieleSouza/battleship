package model;

import common.TileType;

public class Ship {
    private Coordinate shipStart;
    private int length;
    private boolean isHorizontal;
    
    public Ship(Coordinate c, int length, boolean isHorizontal) {
        shipStart = c;
        this.length = length;
        this.isHorizontal = isHorizontal;
    }
    
    public boolean isHorizontal() { return isHorizontal; }
    
    public Coordinate getShipStart() { return shipStart; }
    
    public int getLength() { return length; }
    
    public boolean hasSunk(Field field) {
        if(isHorizontal) {
            for(int i=shipStart.x; i<shipStart.x+length; i++) {
                if(field.getTile(i, shipStart.y) != TileType.HIT) return false;
            }
        }
        else {
            for(int j=shipStart.y; j<shipStart.y+length; j++) {
                if(field.getTile(shipStart.x, j) != TileType.HIT) return false;
            }
        }
        return true;
    }

    public void destroySurroundingArea(Field field) {
        if (isHorizontal) {
            for(int i = shipStart.x-1; i < shipStart.x+length+1; i++) {
                //Verificacao de boundaries de X
                if(i >= 0 && i < field.getLength())
                {
                    for(int j = shipStart.y-1; j < shipStart.y+2; j++) {
                        //Verificacao de boundaries de Y
                        if(j >= 0 && j < field.getLength())
                        {
                            if(!isShipInCoordinate(new Coordinate(i,j))) {
                                field.setTile(new Coordinate(i,j), TileType.MISS);
                            }
                        }
                    }
                }                
            }
        } 
        else {
            //Se ele nao ultrapassa os limites
            //Verificar a area que o navio ocuparia
            //E a area de um quadrado em volta dele
            for(int i = shipStart.x-1; i < shipStart.x+2; i++) {
                //Verificacao de boundaries de X
                if(i >= 0 && i < field.getLength())
                {
                    for(int j = shipStart.y-1; j < shipStart.y+length+1; j++) {
                        //Verificacao de boundaries de Y
                        if(j >= 0 && j < field.getLength())
                        {
                            if(!isShipInCoordinate(new Coordinate(i,j))) {
                                field.setTile(new Coordinate(i,j), TileType.MISS);
                            }
                        }
                    }
                }              
            }
        }
    }
    
    public boolean isShipInCoordinate(Coordinate c) {
        if(isHorizontal) {
            for(int i=shipStart.x; i<shipStart.x+length; i++) {
                if(c.x == i && c.y == shipStart.y) return true;
            }
        }
        else {
            for(int j=shipStart.y; j<shipStart.y+length; j++) {
                if(c.y == j && c.x == shipStart.x) return true;
            }
        }
        return false;
    }
    
    public String toString() {
        String msg = "";
        msg += "(" + shipStart.x + "," + shipStart.y + "): \n";
        msg += "Length: " + length + "\n";
        msg += "Is Horizontal: " + isHorizontal; 
        return msg;
    }
}
