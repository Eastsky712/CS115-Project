import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Player extends JComponent{
    private int playerHealth;
    private int playerVelocity;
    private int playerX;
    private int playerY;
    private Timer t;
    private static final int DEFAULT_PLAYER_HEALTH = 3;
    private static final int DEFAULT_PLAYER_VELOCITY = 5;
    private static final int DEFAULT_PLAYER_X = 50;
    private static final int DEFAULT_PLAYER_Y = 875;
    private Image play;
    public Player(){
        playerHealth = DEFAULT_PLAYER_HEALTH;
        playerVelocity = DEFAULT_PLAYER_VELOCITY;
        playerX = DEFAULT_PLAYER_X;
        playerY = DEFAULT_PLAYER_Y;

    }
    public int getPlayerX(){
        return playerX;
    }
    public int getPlayerY(){
        return playerY;
    }
    public void setPlayerX(int nX){
        playerX = nX;
    }
    public void setPlayerY(int nY){
        playerY = nY;
    }
    public int getPlayerVelocity(){
        return playerVelocity;
    }
    public void draw(Graphics g){
        ImageIcon image1 = new ImageIcon(("cannon.png"));
        play = image1.getImage();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(play,playerX,playerY,this);
    }
}
