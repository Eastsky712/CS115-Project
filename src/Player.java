import javax.swing.*;
import java.awt.*;

public class Player extends JComponent{
    private int playerHealth;
    private int playerVelocity;
    private int playerX;
    private int playerY;
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
    public void reduceHealth(int amount){
        playerHealth = playerHealth - amount;
    }
    public int getHealth(){
        return playerHealth;
    }
    public void setHealth(int sH){
        playerHealth = sH;
    }
    public void draw(Graphics g){
        ImageIcon image1 = new ImageIcon(("cannon.png"));
        play = image1.getImage();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(play,playerX,playerY,this);
    }
}
