import javax.swing.*;
import java.awt.*;

/**
 * The Player class represents the player entity in the game.
 */
public class Player extends JComponent {
    private int playerHealth;
    private int playerVelocity;
    private int playerX;
    private int playerY;
    private static final int DEFAULT_PLAYER_HEALTH = 3;
    private static final int DEFAULT_PLAYER_VELOCITY = 5;
    private static final int DEFAULT_PLAYER_X = 50;
    private static final int DEFAULT_PLAYER_Y = 875;
    private Image play;

    /**
     * Default constructor for the Player class.
     */
    public Player() {
        playerHealth = DEFAULT_PLAYER_HEALTH;
        playerVelocity = DEFAULT_PLAYER_VELOCITY;
        playerX = DEFAULT_PLAYER_X;
        playerY = DEFAULT_PLAYER_Y;
    }

    /**
     * Method to get the x-coordinate of the player.
     *
     * @return The x-coordinate of the player.
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * Method to get the y-coordinate of the player.
     *
     * @return The y-coordinate of the player.
     */
    public int getPlayerY() {
        return playerY;
    }

    /**
     * Method to set the x-coordinate of the player.
     *
     * @param nX The new x-coordinate for the player.
     */
    public void setPlayerX(int nX) {
        playerX = nX;
    }

    /**
     * Method to set the y-coordinate of the player.
     *
     * @param nY The new y-coordinate for the player.
     */
    public void setPlayerY(int nY) {
        playerY = nY;
    }

    /**
     * Method to get the player's velocity.
     *
     * @return The player's velocity.
     */
    public int getPlayerVelocity() {
        return playerVelocity;
    }

    /**
     * Method to reduce the player's health by a specified amount.
     *
     * @param amount The amount to reduce the health by.
     */
    public void reduceHealth(int amount) {
        playerHealth = playerHealth - amount;
    }

    /**
     * Method to get the current health of the player.
     *
     * @return The current health of the player.
     */
    public int getHealth() {
        return playerHealth;
    }

    /**
     * Method to set the player's health to a specified value.
     *
     * @param sH The new health value for the player.
     */
    public void setHealth(int sH) {
        playerHealth = sH;
    }

    /**
     * Method to draw the player on the screen.
     *
     * @param g The graphics object for rendering.
     */
    public void draw(Graphics g) {
        ImageIcon image1 = new ImageIcon(("cannon.png"));
        play = image1.getImage();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(play, playerX, playerY, this);
    }
}
