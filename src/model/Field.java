package model;

import common.TileType;

public class Field {
    
    private TileType[][] field;
    
    public Field () {
        field = new TileType[10][10];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = TileType.WATER;
            }
        }
    }
    
    public TileType[][] getTiles() {
        return field;
    }
    
    public int getLength() {
        return 10;
    }
    
    public TileType getTile(Coordinate c) {
        return getTile(c.x, c.y);
    }
    public TileType getTile(int x, int y) {
        if(x >= 0 && x < field.length &&
           y >= 0 && y < field.length) {
            return field[y][x];
        }
        return null;
    }
    
    public void setTile(Coordinate c, TileType tileType) {
        field[c.y][c.x] = tileType;
    }
    
    public boolean canPlaceShip (int shipSize, Coordinate c, boolean horizontal) {
        if (horizontal) {
            //Se o navio ultrapassa os limites da direita do mapa
            if(c.x + shipSize > field.length) {
                //Nao podemos colocar o navio aqui
                return false;
            }
            //Se ele nao ultrapassa os limites
            //Verificar a area que o navio ocuparia
            //E a area de um quadrado em volta dele
            for(int i = c.x-1; i < c.x+shipSize+1; i++) {
                //Verificacao de boundaries de X
                if(i >= 0 && i < field.length)
                {
                    for(int j = c.y-1; j < c.y+2; j++) {
                        //Verificacao de boundaries de Y
                        if(j >= 0 && j < field.length)
                        {
                            if(field[j][i] != TileType.WATER) return false;
                        }
                    }
                }                
            }
        } 
        else {
            //Se o navio ultrapassa os limites de baixo do mapa
            if(c.y + shipSize > field.length) {
                return false;
            }
            //Se ele nao ultrapassa os limites
            //Verificar a area que o navio ocuparia
            //E a area de um quadrado em volta dele
            for(int i = c.x-1; i < c.x+2; i++) {
                //Verificacao de boundaries de X
                if(i >= 0 && i < field.length)
                {
                    for(int j = c.y-1; j < c.y+shipSize+1; j++) {
                        //Verificacao de boundaries de Y
                        if(j >= 0 && j < field.length)
                        {
                            if(field[j][i] != TileType.WATER) return false;
                        }
                    }
                }              
            }
        }     
        return true;
    }
    
    public boolean placeShip (TileType shipType, Coordinate c, boolean horizontal) {
        int shipSize = shipType.asInt();
        
        if(canPlaceShip(shipSize, c, horizontal))
        {
            if (horizontal) {
                for (int i = c.x; i < c.x + shipSize; i++) {
                    field[c.y][i] = shipType;
                }
            } 
            else {
                for (int i = c.y; i < c.y + shipSize; i++) {
                    field[i][c.x] = shipType;                
                }
            }     
            return true;
        }
        return false;
    }
    
    public TileType shoot (Coordinate c) {
        TileType aux = TileType.WATER;
        
        if (field[c.y][c.x] == TileType.WATER) {
            field[c.y][c.x] = TileType.MISS;
            aux = field[c.y][c.x];
        }
        else if (field[c.y][c.x] == TileType.SHIP1
                    || field[c.y][c.x] == TileType.SHIP2
                    || field[c.y][c.x] == TileType.SHIP3
                    || field[c.y][c.x] == TileType.SHIP4) {
            field[c.y][c.x] = TileType.HIT;
            aux = field[c.y][c.x];
        }   
        return aux;
    }
    
    @Override
    public String toString() {
        boolean firstColumn = true;
        int currentColumn = 0;
        String msg = "   A B C D E F G H I J \n";
        for(int i=0; i<field.length; i++) {
            msg += currentColumn++ + "  ";
            for(int j=0; j<field.length; j++) {
                msg += field[i][j].asInt() + " ";
            }
            msg += "\n";
        }
        return msg;
    }

}
