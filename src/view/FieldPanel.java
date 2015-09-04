package view;

import common.TileType;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FieldPanel extends JPanel {
    
    TileType[][] tiles;
    
    private BufferedImage WATER;
    private BufferedImage SHIP;
    private BufferedImage HIT;
    private BufferedImage MISS;
    
    private int imageSize;
    
    public FieldPanel() {
        tiles = new TileType[10][10];
        for(int i=0; i<tiles.length; i++) {
            for(int j=0; j<tiles.length; j++) {
                tiles[i][j] = TileType.WATER;
            }
        }
        
        try {
            WATER = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));
            SHIP = ImageIO.read(getClass().getResourceAsStream("/res/tiles/ship.png"));
            HIT = ImageIO.read(getClass().getResourceAsStream("/res/tiles/hit.png"));
            MISS = ImageIO.read(getClass().getResourceAsStream("/res/tiles/miss.png"));
        } catch(IOException e) { System.out.println("Não conseguiu carregar as imagens."); }
        
        imageSize = WATER.getWidth();
        //imageSize = 32;
    }
    
    public void updateTiles(TileType[][] tiles) {
        this.tiles = tiles;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for(int i=0; i<tiles.length; i++) {
            for(int j=0; j<tiles.length; j++) {
                switch(tiles[j][i]) {
                    case HIT:
                        
                        g.drawImage(HIT, i*imageSize, j*imageSize, null);
                        break;
                    case MISS:
                        g.drawImage(MISS, i*imageSize, j*imageSize, null);
                        break;
                    case WATER:
                        g.drawImage(WATER, i*imageSize, j*imageSize, null);
                        break;
                    case SHIP1:
                    case SHIP2:
                    case SHIP3:
                    case SHIP4:
                        g.drawImage(SHIP, i*imageSize, j*imageSize, null);
                        break;
                }
            }
        }
        repaint();
    }
    
}
