package controller;

import common.TileType;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import model.AI;
import model.Coordinate;
import model.HumanPlayer;
import model.Match;
import model.Match.PlayerTurn;

public class GameViewController {
    
    private Match match;
    private AudioInputStream bangAIS, splashAIS, queComeceABatalhaAIS, vitoriaAIS, derrotaAIS;
    private Clip bang, splash, queComeceABatalha, vitoria, derrota;
    
    public GameViewController() {
        match = new Match(new HumanPlayer(), new AI());
        //match = new Match(new AI(), new AI());
        
        
        try {
            bangAIS = AudioSystem.getAudioInputStream(getClass().getResource("/res/sounds/bang.wav"));
            bang = AudioSystem.getClip();
            bang.open(bangAIS);
            splashAIS = AudioSystem.getAudioInputStream(getClass().getResource("/res/sounds/splash.wav"));
            splash = AudioSystem.getClip();
            splash.open(splashAIS);
            queComeceABatalhaAIS = AudioSystem.getAudioInputStream(getClass().getResource("/res/sounds/queComeceABatalha.wav"));
            queComeceABatalha = AudioSystem.getClip();
            queComeceABatalha.open(queComeceABatalhaAIS);
            vitoriaAIS = AudioSystem.getAudioInputStream(getClass().getResource("/res/sounds/vitoria.wav"));
            vitoria = AudioSystem.getClip();
            vitoria.open(vitoriaAIS);
            derrotaAIS = AudioSystem.getAudioInputStream(getClass().getResource("/res/sounds/derrota.wav"));
            derrota = AudioSystem.getClip();
            derrota.open(derrotaAIS);
        } catch(Exception e){System.out.println("Failure to load sound");}

        queComeceABatalha.setFramePosition(1);
        queComeceABatalha.start();     
    }
    
    public TileType[][] getPlayer1Field() {
        return match.player1.getMyField().getTiles();
    }
    
    public TileType[][] getPlayer1EnemyField() {
        return match.player1.getEnemyField().getTiles();
    }
    
    public TileType[][] getPlayer2Field() {
        return match.player2.getMyField().getTiles();
    }
    
    public TileType[][] getPlayer2EnemyField() {
        return match.player2.getEnemyField().getTiles();
    }
    
    public void matchSetup() {
        match.setup();
        match.printFields();
    }
    
    public boolean isPlayer1Turn() {
        return match.getPlayerTurn() == PlayerTurn.PLAYER1;
    }
    
    public void player1Move(int x, int y) {
        TileType t = match.sendFeedback(new Coordinate(x,y));
        switch(t) {
            case HIT:
                bang.setFramePosition(1);
                bang.start();   
                break;
            case MISS:
                splash.setFramePosition(1);
                splash.start();   
                break;
        }
    }
    
    public void player1AIMove() {
        match.sendFeedback(match.player1.play());
    }
    
    public void player2Move() {
        //match.sendFeedback(match.player2.play());
    }
    
    public void player2AIMove() {
        //TileType t = 
        match.sendFeedback(match.player2.play());
        /*switch(t) {
            case HIT:
                bang.setFramePosition(1);
                bang.start();   
                break;
            case MISS:
                splash.setFramePosition(1);
                splash.start();  
                break;
        }*/
    }
    
    public boolean isAIMatch() {
        return match.player1 instanceof AI && match.player2 instanceof AI;
    }
    
    public boolean hasMatchEnded() {
        return match.gameEnded();
    }
    
    public boolean hasPlayer1Won() {
        if(hasMatchEnded()) {
            if (match.player2.getShips().isEmpty()) {
                vitoria.setFramePosition(1);
                vitoria.start();  
                return true;
            }
            else {
                derrota.setFramePosition(1);
                derrota.start();  
                return false;
            }
        }
        return false;
    }
}
