package model;

import java.util.ArrayList;
import java.util.Random;
import model.Coordinate;
import model.Field;
import model.Player;
import common.TileType;

public class AI extends Player {
    
    private enum ShipWay {
        UNKNOWN, HORIZONTAL, VERTICAL
    }
    
    private enum Direction {
            UP, DOWN, LEFT, RIGHT;
        }
    
    private ArrayList<Coordinate> lastHits;
    private ShipWay way;
    
    private Random rng;
    
    //Stores the previous coordinates to avoid double random shots
    private ArrayList<Coordinate> shots;
        
    public AI () {
        lastHits = new ArrayList<>();
        way = ShipWay.UNKNOWN;
        
        rng = new Random();
        shots = new ArrayList<>();
    }
    
    public void updateLastHits(Coordinate c) {
        lastHits.add(c);
        //Se temos mais de uma coordenada valida,
        //Ja podemos descobrir o sentido do navio
        if(lastHits.size() > 1)
        {
            //Se os valores de X diferem entre si
            if(lastHits.get(0).x != lastHits.get(1).x) {
                //O navio esta na horizontal
                way = ShipWay.HORIZONTAL;
            }
            //Se os valores de Y diferem entre si
            else if(lastHits.get(0).y != lastHits.get(1).y) {
                //O navio esta na vertical
                way = ShipWay.VERTICAL;
            }
        }
    }
    
    public void clearLastHits() {
        //Limpar a lista de ataques bem-sucedidos
        lastHits.clear();
        //Remover a informacao de sentido de navio
        way = ShipWay.UNKNOWN;
    }
    
    public Coordinate randomShot (Field field) {
        boolean isValidShot = false;
        boolean hasFoundEqual = false;
        Coordinate c = new Coordinate(-1,-1);
        
        while(!isValidShot) {
            c = new Coordinate(rng.nextInt(10), rng.nextInt(10));
            for(Coordinate shot : shots) {
                if(shot.x == c.x && shot.y == c.y) {
                    hasFoundEqual = true;
                    break;
                }
            }
            if(!hasFoundEqual) {
                isValidShot = true;
            }
            else {
                System.out.println("Invalid shot");
                hasFoundEqual = false;
            }
        }
        System.out.println("Shooting randomly at: " + c);
        return c;
    }
    
    public Coordinate destroyShip(Field field) {
        boolean hasPickedDirection = false;
        
        TileType tile;
        ArrayList<Direction> possibleDirs = new ArrayList<>();
        Direction dir;
        
        //Para cada uma das coordenadas de ataques bem-sucedidos
        for(Coordinate c : lastHits)
        {
            //Selecionar as direcoes possiveis, com base no
            //conhecimento previo da orientacao do navio
            switch(way)
            {
                case UNKNOWN:
                    possibleDirs.add(Direction.UP);
                    possibleDirs.add(Direction.DOWN);
                    possibleDirs.add(Direction.LEFT);
                    possibleDirs.add(Direction.RIGHT);
                    break;
                case HORIZONTAL:
                    possibleDirs.add(Direction.LEFT);
                    possibleDirs.add(Direction.RIGHT);
                    break;
                case VERTICAL:
                    possibleDirs.add(Direction.UP);
                    possibleDirs.add(Direction.DOWN);
                    break;
            }
            
            //Enquanto ainda houverem direcoes possiveis
            while(!possibleDirs.isEmpty())
            {
                //Selecionar uma direcao aleatoria
                dir = possibleDirs.get(rng.nextInt(possibleDirs.size()));
                
                //Pegar o tile correspondente
                switch(dir)
                {
                    case LEFT:
                        tile = field.getTile(c.x - 1, c.y);
                        //Se o tile for agua,
                        //retornar a coordenada correspondente
                        if(tile == TileType.WATER)
                        {
                            System.out.println("Shooting at: " + (c.x-1) + ", " + c.y);
                            return new Coordinate(c.x-1, c.y);
                        }
                        //Se a direcao for invalida
                        //Remover esta direcao da lista de direcoes possiveis
                        else { possibleDirs.remove(dir); }
                        break;
                    case RIGHT:
                        tile = field.getTile(c.x + 1, c.y);
                        if(tile == TileType.WATER)
                        {
                            System.out.println("Shooting at: " + (c.x+1) + ", " + c.y);
                            return new Coordinate(c.x+1, c.y);
                        }
                        else { possibleDirs.remove(dir); }
                        break;
                    case UP:
                        tile = field.getTile(c.x, c.y - 1);
                        if(tile == TileType.WATER)
                        {
                            System.out.println("Shooting at: " + c.x + ", " + (c.y-1));
                            return new Coordinate(c.x, c.y-1);
                        }
                        else { possibleDirs.remove(dir); }
                        break;
                    case DOWN:
                        tile = field.getTile(c.x, c.y + 1);
                        if(tile == TileType.WATER)
                        {
                            System.out.println("Shooting at: " + c.x + ", " + (c.y+1));
                            return new Coordinate(c.x, c.y+1);
                        }
                        else { possibleDirs.remove(dir); }
                        break;
                }
            }
        }
        //Se ele nao encontrar alguma direcao, tem algo errado em outro lugar...
        return null;
    } 
  
    //Player implementation
    @Override
    public void initializeField() {
        placeShipsRandomly();
    }

    @Override
    public Coordinate play() {
        Coordinate aux;
        
        //Se a lista de ataques recentes bem-sucedidos nao estiver vaiza
        if (!lastHits.isEmpty()) {
            //Tentar atacar nas redondezas dos ataques bem-sucedidos
            aux = destroyShip(enemyField);
        }
        else {
            //Se nao, atacar aleatoriamente
            aux = randomShot(enemyField);
        }
        
        shots.add(aux);
        return aux;
    }

    @Override
    public void onMiss() {
        System.out.println("AI Missed.");
    }

    @Override
    public void onHit(Coordinate c) {
        System.out.println("AI Hit.");
        updateLastHits(c);
    }

    @Override
    public void onSink(Coordinate c, Ship s) {
        System.out.println("AI Sink.");
        clearLastHits();
        
        //Invalidade area near ship
        Coordinate aux;
        
        if (s.isHorizontal()) {
            for(int i = s.getShipStart().x-1; i < s.getShipStart().x+s.getLength()+1; i++) {
                //Verificacao de boundaries de X
                if(i >= 0 && i < enemyField.getLength())
                {
                    for(int j = s.getShipStart().y-1; j < s.getShipStart().y+2; j++) {
                        //Verificacao de boundaries de Y
                        if(j >= 0 && j < enemyField.getLength())
                        {
                            aux = new Coordinate(i,j);
                            if(!shots.contains(aux)) { shots.add(aux); }
                        }
                    }
                }                
            }
        } 
        else {
            //Se ele nao ultrapassa os limites
            //Verificar a area que o navio ocuparia
            //E a area de um quadrado em volta dele
            for(int i = s.getShipStart().x-1; i < s.getShipStart().x+2; i++) {
                //Verificacao de boundaries de X
                if(i >= 0 && i < s.getLength())
                {
                    for(int j = s.getShipStart().y-1; j < s.getShipStart().y+s.getLength()+1; j++) {
                        //Verificacao de boundaries de Y
                        if(j >= 0 && j < s.getLength())
                        {
                            aux = new Coordinate(i,j);
                            if(!shots.contains(aux)) { shots.add(aux); }
                        }
                    }
                }              
            }
        }
    }

}
