/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.GameViewController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author danie_000
 */
public class GameView extends javax.swing.JFrame implements ActionListener {
    
    private ImageIcon logo;
    
    public GameViewController gameViewController;
    
    //Used to control turns
    Timer timer;
    
    //interval between each play from the AI (in ms)
    int intervalAI = 2000;
    
    //This is where the turn is managed at
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(!gameViewController.isPlayer1Turn()) {
            gameViewController.player2AIMove();
        }
        else if(gameViewController.isAIMatch()) {
            gameViewController.player1AIMove();
        }
        updateFieldsAndLabels();
        updateGameState();
    }

    /**
     * Creates new form BatalhaCadaval
     */
    public GameView() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);
        
        gameViewController = new GameViewController();
        BufferedImage imgLogo;
        try {
            imgLogo = ImageIO.read(getClass().getResourceAsStream("/res/logo.png"));
            logo = new ImageIcon(imgLogo);
        } catch (IOException ex) { System.out.println("Could not load logo image!"); }
        //logo = new ImageIcon("res/logo.png");
        jLabelLogo.setIcon(logo);
        
        gameViewController.matchSetup();
        
        updateFieldsAndLabels();
        
        timer = new Timer(intervalAI, this);
        timer.start();
        
    }
    
    public void updateFieldsAndLabels() {
        fieldPanel1.updateTiles(gameViewController.getPlayer1Field());
        if(gameViewController.isAIMatch() || gameViewController.hasMatchEnded()) fieldPanel2.updateTiles(gameViewController.getPlayer2Field());
        else fieldPanel2.updateTiles(gameViewController.getPlayer1EnemyField());
        
        boolean isPlayer1Turn = gameViewController.isPlayer1Turn();
        if(isPlayer1Turn) {
            jLabelYourTurn.setForeground(Color.RED);
            jLabelEnemyTurn.setForeground(Color.BLACK);
        }
        else {
            jLabelYourTurn.setForeground(Color.BLACK);
            jLabelEnemyTurn.setForeground(Color.RED);
        }
    }
    
    public void updateGameState() {
        int dialogResult;
        //If the match is over
        if(gameViewController.hasMatchEnded()) {
            //Stop the AI from trying to play
            timer.stop();
            boolean hasPlayer1Won = gameViewController.hasPlayer1Won();
            //int playerNum;
            //if(hasPlayer1Won) playerNum = 1;
            //else playerNum = 2;
            //System.out.println("PLAYER " + playerNum + "WON!");
            //JOptionPane.showMessageDialog(null, ("PLAYER " + playerNum + "WON!"), "GAME OVER", JOptionPane.PLAIN_MESSAGE);
            if(hasPlayer1Won) {
                dialogResult = JOptionPane.showConfirmDialog(null, ("YOU WIN! PLAY AGAIN?"), "GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
            }
            else {
                dialogResult = JOptionPane.showConfirmDialog(null, ("YOU LOSE. PLAY AGAIN?"), "GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
            }
                        
            if(dialogResult == 0){
                gameViewController = new GameViewController();
                gameViewController.matchSetup();
                updateGameState();
                updateFieldsAndLabels();
                timer.start();
            } 
            else System.exit(1);
         }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fieldPanel1 = new view.FieldPanel();
        fieldPanel2 = new view.FieldPanel();
        jLabelYourTurn = new javax.swing.JLabel();
        jLabelEnemyTurn = new javax.swing.JLabel();
        jLabelLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Batalha Cadaval");

        fieldPanel1.setBackground(new java.awt.Color(255, 255, 255));
        fieldPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fieldPanel1MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout fieldPanel1Layout = new javax.swing.GroupLayout(fieldPanel1);
        fieldPanel1.setLayout(fieldPanel1Layout);
        fieldPanel1Layout.setHorizontalGroup(
            fieldPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        fieldPanel1Layout.setVerticalGroup(
            fieldPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        fieldPanel2.setBackground(new java.awt.Color(255, 255, 255));
        fieldPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fieldPanel2MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout fieldPanel2Layout = new javax.swing.GroupLayout(fieldPanel2);
        fieldPanel2.setLayout(fieldPanel2Layout);
        fieldPanel2Layout.setHorizontalGroup(
            fieldPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        fieldPanel2Layout.setVerticalGroup(
            fieldPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );

        jLabelYourTurn.setFont(new java.awt.Font("AR CHRISTY", 0, 24)); // NOI18N
        jLabelYourTurn.setText("YOUR TURN");

        jLabelEnemyTurn.setFont(new java.awt.Font("AR CHRISTY", 0, 24)); // NOI18N
        jLabelEnemyTurn.setText("ENEMY TURN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fieldPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(fieldPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jLabelEnemyTurn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelYourTurn)
                .addGap(109, 109, 109))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fieldPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fieldPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelYourTurn)
                    .addComponent(jLabelEnemyTurn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fieldPanel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fieldPanel1MouseReleased
        //In case we ever use two human players...
    }//GEN-LAST:event_fieldPanel1MouseReleased

    private void fieldPanel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fieldPanel2MouseReleased
        if(gameViewController.isPlayer1Turn() && !gameViewController.hasMatchEnded()) {
            gameViewController.player1Move(evt.getX()/32, evt.getY()/32);
            timer.restart();
            updateFieldsAndLabels();
            updateGameState();
        }
    }//GEN-LAST:event_fieldPanel2MouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.FieldPanel fieldPanel1;
    private view.FieldPanel fieldPanel2;
    private javax.swing.JLabel jLabelEnemyTurn;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelYourTurn;
    // End of variables declaration//GEN-END:variables
}