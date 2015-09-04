package model;

import common.TileType;
import java.util.Scanner;

public class Match {
    
    public enum PlayerTurn {
        PLAYER1, PLAYER2;
    }
    
    public Player player1;
    public Player player2;
    
    //Qual jogador esta jogando
    private PlayerTurn playerTurn;
    
    private Scanner scanner;
    
    public Match(Player p1, Player p2) {
        player1 = p1;
        player2 = p2;
        
        //Sorteia inicio do jogo
        /*Random rng = new Random();
        boolean player1Start = rng.nextBoolean();
        if(player1Start) playerTurn = PlayerTurn.PLAYER1;
        else playerTurn = PlayerTurn.PLAYER2;*/
        playerTurn = PlayerTurn.PLAYER1;
        
        scanner = new Scanner(System.in);
    }
    
    public void play() {
        setup();
        while(!gameEnded()) turn();
        endGame();
    }
    
    public void setup() {
        //player1.initializeField();
        player1.placeShipsRandomly();
        player2.initializeField();
    }
    
    public void turn() {
        printFields();
        Coordinate c;
        switch(playerTurn)
        {
            case PLAYER1:
                System.out.println("PLAYER 1 TURN");
                c = player1.play();
                sendFeedback(c);
                break;
            case PLAYER2:
                System.out.println("PLAYER 2 TURN");
                c = player2.play();
                sendFeedback(c);
                break;
        }
    }
    
    public TileType sendFeedback(Coordinate c) {
        TileType t = null;
        Ship shipToRemove = null;
        boolean hasSunk = false;
        boolean shouldSwitchTurns = false;
        
        switch(playerTurn) {
            case PLAYER1:
                t = player2.getMyField().shoot(c);
                player1.getEnemyField().setTile(c, player2.getMyField().getTile(c));
                for(Ship s : player2.getShips()) {
                    if(s.hasSunk(player2.getMyField())) {
                        System.out.println("SUNK SHIP:");
                        System.out.println(s);
                        s.destroySurroundingArea(player2.getMyField());
                        s.destroySurroundingArea(player1.getEnemyField());
                        shipToRemove = s;
                        player1.onSink(c, s);
                        hasSunk = true;
                        break;
                    }
                }
                if(shipToRemove != null) player2.getShips().remove(shipToRemove);
                
                if(hasSunk == false && t == TileType.HIT) {
                    player1.onHit(c);
                }
                else if(t == TileType.MISS) {
                    player1.onMiss();
                    shouldSwitchTurns = true;
                }
                break;
            case PLAYER2:
                t = player1.getMyField().shoot(c);
                player2.getEnemyField().setTile(c, player1.getMyField().getTile(c));
                for(Ship s : player1.getShips()) {
                    if(s.hasSunk(player1.getMyField())) {
                        s.destroySurroundingArea(player1.getMyField());
                        s.destroySurroundingArea(player2.getEnemyField());
                        shipToRemove = s;
                        player2.onSink(c, s);
                        hasSunk = true;
                    }
                }
                if(shipToRemove != null) player1.getShips().remove(shipToRemove);
                if(hasSunk == false && t == TileType.HIT) {
                    player2.onHit(c);
                }
                else if(t == TileType.MISS) { 
                    player2.onMiss();
                    shouldSwitchTurns = true;
                }
                break;
        }
        
        if(shouldSwitchTurns) switchTurns();
        
        return t;
    }
    
    public PlayerTurn getPlayerTurn() { return playerTurn; }
    
    public void switchTurns() {
        if(playerTurn == PlayerTurn.PLAYER1) playerTurn = PlayerTurn.PLAYER2;
        else playerTurn = PlayerTurn.PLAYER1;
    }
    
    public boolean gameEnded() {
        if(player1.getShips().isEmpty() || player2.getShips().isEmpty()) return true;
        return false;
    }
    
    public void endGame() {
        if(player2.getShips().isEmpty()) {
            System.out.println("PLAYER 1 WON");
        }
        else if(player1.getShips().isEmpty()) {
            System.out.println("PLAYER 2 WON");
        }
    }
    
    public void printFields() {
        System.out.println("PLAYER 1");
        System.out.println("Meu campo: \n" + player1.getMyField());
        System.out.println("Campo do inimigo: \n" + player1.getEnemyField());
        System.out.println("PLAYER 2");
        System.out.println("Meu campo: \n" + player2.getMyField());
        System.out.println("Campo do inimigo: \n" + player2.getEnemyField());
    }
}
