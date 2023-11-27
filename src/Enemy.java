import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.Timer;

/**
 * The Enemy class represents the enemy entities in the game.
 */
public class Enemy extends JComponent {
    private int enemyHealth;
    private Timer shootingTimer = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            shoot();
        }
    });
    private int enemyVelocityX;
    private int enemyVelocityY;
    private int enemyX;
    private int enemyY;
    private Player player;
    private int targetPlayerX;
    private int targetPlayerY;
    private int level;
    private static final int DEFAULT_ENEMY_HEALTH = 1;
    private static final int DEFAULT_ENEMY_VELOCITY_X = 1;
    private static final int DEFAULT_ENEMY_VELOCITY_Y = 1;
    private static final int DEFAULT_ENEMY_X = 100;
    private static final int DEFAULT_ENEMY_Y = 100;
    private static final int DEFAULT_LEVEL = 1;
    private Image badGuy;
    private int movementState;

    private List<Bullet> bullets;

    /**
     * Default constructor for the Enemy class.
     *
     * @param player The player instance in the game.
     */
    public Enemy(Player player) {
        enemyHealth = DEFAULT_ENEMY_HEALTH;
        enemyVelocityX = DEFAULT_ENEMY_VELOCITY_X;
        enemyVelocityY = DEFAULT_ENEMY_VELOCITY_Y;
        enemyX = DEFAULT_ENEMY_X;
        enemyY = DEFAULT_ENEMY_Y;
        level = DEFAULT_LEVEL;
        movementState = 0;
        bullets = new ArrayList<>();
        scheduleShooting();
    }

    /**
     * Constructor for the Enemy class with specified initial position and level.
     *
     * @param player    The player instance in the game.
     * @param initialX  The initial x-coordinate of the enemy.
     * @param initialY  The initial y-coordinate of the enemy.
     * @param lvl       The level of the enemy.
     */
    public Enemy(Player player, int initialX, int initialY, int lvl) {
        Random rand = new Random();
        level = lvl;
        enemyHealth = rand.nextInt(1 + (lvl / 3)) + 1;
        enemyVelocityX = rand.nextInt(1 + (lvl / 2)) + 1;
        enemyVelocityY = rand.nextInt(lvl) + 1;
        enemyX = initialX;
        enemyY = initialY;
        movementState = rand.nextInt(2);
        bullets = new ArrayList<>();
        this.targetPlayerX = player.getPlayerX();
        this.targetPlayerY = player.getPlayerY();
        scheduleShooting();
        shootingTimer.start();
    }

    /**
     * Method to reduce the enemy's health by a specified amount.
     *
     * @param amount The amount to reduce the health by.
     */
    public void reduceHealth(int amount) {
        enemyHealth = enemyHealth - amount;
    }

    /**
     * Method to update the position of enemy bullets.
     */
    public void updateBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
        }
    }

    /**
     * Method to get the current health of the enemy.
     *
     * @return The current health of the enemy.
     */
    public int getHealth() {
        return enemyHealth;
    }

    /**
     * Method to schedule shooting at regular intervals.
     */
    private void scheduleShooting() {
        int delay = 2000 + (int) (Math.random() * 5000); // Delay between 2 and 7 seconds
        shootingTimer.setDelay(delay);
        shootingTimer.start();
    }

    /**
     * Method to perform shooting by creating a new bullet.
     */
    private void shoot() {
        Bullet bullet = new Bullet(enemyX, enemyY, targetPlayerX, targetPlayerY, false);
        bullets.add(bullet);
    }

    /**
     * Method to get the list of enemy bullets.
     *
     * @return The list of enemy bullets.
     */
    public List<Bullet> getEnemyBullets() {
        return bullets;
    }

    /**
     * Method to update the enemy's position based on its movement state.
     */
    public void update() {
        switch (movementState) {
            case 0: // Left-right movement
                enemyX += enemyVelocityX;
                if (enemyX <= 0 || enemyX >= 835) {
                    // Hit left or right edge, go down slightly and change direction
                    enemyX = Math.max(0, Math.min(enemyX, 835)); // Ensure enemyX is within bounds
                    handleEnemyCollision();
                }
                break;
            case 1: // Right-left movement
                enemyX -= enemyVelocityX;
                if (enemyX <= 0 || enemyX >= 835) {
                    // Hit left or right edge, go down slightly and change direction
                    enemyX = Math.max(0, Math.min(enemyX, 835)); // Ensure enemyX is within bounds
                    handleEnemyCollision();
                }
                break;
        }
    }

    /**
     * Method to get the list of bullets.
     *
     * @return The list of bullets.
     */
    public List<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Method to set the position of a specific bullet.
     *
     * @param i  Index of the bullet in the list.
     * @param eY The new y-coordinate for the bullet.
     */
    public void setBullets(int i, int eY) {
        bullets.get(i).setBulletY(eY);
    }

    /**
     * Method to handle collision with the game window edges.
     * Adjusts the position slightly and changes the direction of movement.
     */
    public void handleEnemyCollision() {
        // Adjust position slightly and change direction
        enemyY += 10 * enemyVelocityY; // Move down by a fixed value
        enemyVelocityX = -enemyVelocityX; // Change direction
    }

    /**
     * Method to get the x-coordinate of the enemy.
     *
     * @return The x-coordinate of the enemy.
     */
    public int getEnemyX() {
        return enemyX;
    }

    /**
     * Method to get the y-coordinate of the enemy.
     *
     * @return The y-coordinate of the enemy.
     */
    public int getEnemyY() {
        return enemyY;
    }

    /**
     * Method to set the x-coordinate of the enemy.
     *
     * @param nX The new x-coordinate for the enemy.
     */
    public void setEnemyX(int nX) {
        enemyX = nX;
    }

    /**
     * Method to set the y-coordinate of the enemy.
     *
     * @param nY The new y-coordinate for the enemy.
     */
    public void setEnemyY(int nY) {
        enemyY = nY;
    }

    /**
     * Method to get the x-velocity of the enemy.
     *
     * @return The x-velocity of the enemy.
     */
    public int getEnemyVelocityX() {
        return enemyVelocityX;
    }

    /**
     * Method to get the y-velocity of the enemy.
     *
     * @return The y-velocity of the enemy.
     */
    public int getEnemyVelocityY() {
        return enemyVelocityY;
    }

    /**
     * Method to draw the enemy on the screen.
     *
     * @param g The graphics object for rendering.
     */
    public void draw(Graphics g) {
        ImageIcon image1 = new ImageIcon(("enemy.png"));
        badGuy = image1.getImage();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(badGuy, enemyX, enemyY, this);
    }
}
