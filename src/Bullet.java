import javax.swing.*;
import java.awt.*;

/**
 * The Bullet class represents a projectile in the game.
 */
public class Bullet extends JComponent {
    private int bulletVelocity;
    private int bulletX;
    private int bulletY;
    private int targetX;
    private int targetY;
    private static final int DEFAULT_BULLET_VELOCITY = 3;
    private Image bullet;
    private boolean isPlayerBullet;

    /**
     * Default constructor for the Bullet class.
     */
    public Bullet() {
        bulletVelocity = DEFAULT_BULLET_VELOCITY;
        bulletX = 0;
        bulletY = 0;
        this.targetX = 0;
        this.targetY = 0;
    }

    /**
     * Constructor for the Bullet class with specified parameters.
     *
     * @param startX          The starting x-coordinate of the bullet.
     * @param startY          The starting y-coordinate of the bullet.
     * @param targetX         The target x-coordinate for the bullet.
     * @param targetY         The target y-coordinate for the bullet.
     * @param isPlayerBullet  A boolean indicating whether the bullet is from the player.
     */
    public Bullet(int startX, int startY, int targetX, int targetY, boolean isPlayerBullet) {
        this.isPlayerBullet = isPlayerBullet;
        bulletVelocity = DEFAULT_BULLET_VELOCITY;
        bulletX = startX;
        bulletY = startY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    /**
     * Method to set the x-coordinate of the bullet.
     *
     * @param nX The new x-coordinate for the bullet.
     */
    public void setBulletX(int nX) {
        bulletX = nX;
    }

    /**
     * Method to set the y-coordinate of the bullet.
     *
     * @param nY The new y-coordinate for the bullet.
     */
    public void setBulletY(int nY) {
        bulletY = nY;
    }

    /**
     * Method to get the x-coordinate of the bullet.
     *
     * @return The x-coordinate of the bullet.
     */
    public int getBulletX() {
        return bulletX;
    }

    /**
     * Method to get the y-coordinate of the bullet.
     *
     * @return The y-coordinate of the bullet.
     */
    public int getBulletY() {
        return bulletY;
    }

    /**
     * Method to get the velocity of the bullet.
     *
     * @return The velocity of the bullet.
     */
    public int getBulletVelocity() {
        return bulletVelocity;
    }

    /**
     * Method to update the position of the bullet based on its direction.
     */
    public void update() {
        if (isPlayerBullet) {
            // Player bullet goes up
            bulletY -= bulletVelocity;
        } else {
            // Enemy bullet goes down
            bulletY += bulletVelocity;
        }
    }

    /**
     * Method to check if the bullet is from the player.
     *
     * @return True if the bullet is from the player, false otherwise.
     */
    public boolean isPlayerBullet() {
        return isPlayerBullet;
    }

    /**
     * Method to draw the bullet on the screen.
     *
     * @param g The graphics object for rendering.
     */
    public void draw(Graphics g) {
        ImageIcon image1 = new ImageIcon("bullet.png");
        bullet = image1.getImage();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bullet, bulletX, bulletY, this);
    }
}
