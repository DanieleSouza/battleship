package model;

import java.util.Scanner;
import model.Coordinate;
import model.Player;
import model.Ship;
import common.TileType;

public class HumanPlayer extends Player {
    
    void placeShipsManually() {
        Scanner s = new Scanner(System.in);
        
        boolean isHorizontal = true;
        Coordinate aux = new Coordinate(-1,-1);
        
        int ship1 = 0;
        int ship2 = 0;
        int ship3 = 0;
        int ship4 = 0;
        
        //generate 4x SHIP1
        System.out.println("COLOCANDO NAVIOS DE TAMANHO 1");
        while (ship1 < 4) {
            System.out.println("Navio horizontal? s/n");
            if("s".equalsIgnoreCase(s.next())) isHorizontal = true;
            else isHorizontal = false;
            System.out.println("Insira uma posicao X:");
            aux.x = s.nextInt();
            System.out.println("Insira uma posicao Y:");
            aux.y = s.nextInt();
            if (myField.placeShip(TileType.SHIP1, aux, isHorizontal)) {
                ships.add(new Ship(aux, TileType.SHIP1.asInt(), isHorizontal));
                ship1++;
            }
        }
        
        //generate 3x SHIP2
        System.out.println("COLOCANDO NAVIOS DE TAMANHO 2");
        while (ship2 < 3) {
            System.out.println("Navio horizontal? s/n");
            if("s".equalsIgnoreCase(s.next())) isHorizontal = true;
            else isHorizontal = false;
            System.out.println("Insira uma posicao X:");
            aux.x = s.nextInt();
            System.out.println("Insira uma posicao Y:");
            aux.y = s.nextInt();
            if (myField.placeShip(TileType.SHIP2, aux, isHorizontal)) {
                ships.add(new Ship(aux, TileType.SHIP2.asInt(), isHorizontal));
                ship2++;
            }
        }
        
        //generate 2x SHIP3
        System.out.println("COLOCANDO NAVIOS DE TAMANHO 3");
        while (ship3 < 2) {
            System.out.println("Navio horizontal? s/n");
            if("s".equalsIgnoreCase(s.next())) isHorizontal = true;
            else isHorizontal = false;
            System.out.println("Insira uma posicao X:");
            aux.x = s.nextInt();
            System.out.println("Insira uma posicao Y:");
            aux.y = s.nextInt();
            if (myField.placeShip(TileType.SHIP3, aux, isHorizontal)) {
                ships.add(new Ship(aux, TileType.SHIP3.asInt(), isHorizontal));
                ship3++;
            }
        }
        
        //generate 1x SHIP4
        System.out.println("COLOCANDO NAVIOS DE TAMANHO 4");
        while (ship4 < 1) {
            System.out.println("Navio horizontal? s/n");
            if("s".equalsIgnoreCase(s.next())) isHorizontal = true;
            else isHorizontal = false;
            System.out.println("Insira uma posicao X:");
            aux.x = s.nextInt();
            System.out.println("Insira uma posicao Y:");
            aux.y = s.nextInt();
            if (myField.placeShip(TileType.SHIP4, aux, isHorizontal)) {
                ships.add(new Ship(aux, TileType.SHIP4.asInt(), isHorizontal));
                ship4++;
            }
        }
    }

    @Override
    public void initializeField() {
        Scanner s = new Scanner(System.in);
        System.out.println("Posicionar navios automaticamente? s/n");
        String answer = s.next();
        if(answer.equals("s")) placeShipsRandomly();
        else {
            placeShipsManually();
        }
    }

    @Override
    public Coordinate play() {
        Coordinate c = new Coordinate(-1,-1);
        Scanner s = new Scanner(System.in);
        System.out.println("Insira uma posicao X:");
        c.x = s.nextInt();
        System.out.println("Insira uma posicao Y:");
        c.y = s.nextInt();
        
        return c;
    }

    @Override
    public void onMiss() {
        System.out.println("Errou!");
    }

    @Override
    public void onHit(Coordinate c) {
        System.out.println("Acertou!");
    }

    @Override
    public void onSink(Coordinate c, Ship s) {
        System.out.println("Afundou um navio!");
    }
    
}
