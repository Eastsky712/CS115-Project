import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * The GamePanel class represents the main game panel where the game logic and rendering are handled.
 */
public class GamePanel extends JPanel implements Runnable {
    // Player instance
    Player p = new Player();

    // Frames per second for the game
    int FPS = 60;

    // Game Over Panel to display when the game ends
    private GameOverPanel gameOverPanel;

    // Player lives and current level
    private int playerLives;
    private int currentLevel = 1;
    private int points = 0;
    private int finalLevel;
    private int finalPoints;

    // Flag to determine if the game is over
    private boolean gameOver = false;

    // Lists to store bullets, enemy bullets, and enemies
    ArrayList<Bullet> bulls = new ArrayList<>();
    ArrayList<Bullet> enemyBullets = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();

    // Random generator for enemy spawn positions
    Random random = new Random();

    // KeyHandler for handling user input
    KeyHandler keyH = new KeyHandler();

    // Thread for running the game loop
    Thread gameThread;

    /**
     * Constructor for the GamePanel class.
     */

    public GamePanel(){
        this.setPreferredSize(new Dimension(900,1000));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        // Initialize and add the GameOverPanel
        this.gameOverPanel = new GameOverPanel();
        this.add(gameOverPanel);
    }

    /**
     * Thread starter method to begin the game loop.
     */
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Method to handle game over logic.
     */
    private void gameOver() {
        finalLevel = currentLevel;
        finalPoints = points;

        gameOverPanel.updateGameOverPanel(finalLevel, finalPoints);

        gameOver = true;
        repaint();
    }

    /**
     * The main game loop.
     */
    @Override
    public void run() {
        // Calculate the time interval between frames
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null){
            // Update game logic
            update();

            // Repaint the screen
            repaint();

            // Update enemy bullets
            updateEnemyBullets();
            try {
                // Sleep to control the frame rate
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                // Set the time for the next frame
                nextDrawTime += drawInterval;

            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to update the game logic.
     */
    public void update() {
        // Handle player movement
        if (keyH.rightPressed) {
            if (p.getPlayerX() < 838) {
                p.setPlayerX(p.getPlayerX() + p.getPlayerVelocity());
            }
        } else if (keyH.leftPressed) {
            if (p.getPlayerX() > 0) {
                p.setPlayerX(p.getPlayerX() - p.getPlayerVelocity());
            }
        }

        // Handle player shooting
        if (keyH.bullets == 1) {
            bulls.add(new Bullet(p.getPlayerX(), 840,true));
            keyH.bullets = 0;
        }

        // Move and update player bullets
        for (Bullet bull : bulls) {
            bull.setBulletY(bull.getBulletY() - bull.getBulletVelocity());
        }

        // Check if all enemies are defeated
        if (enemies.isEmpty()) {
            // Skip to a higher level with cheat
            if(keyH.cheatLevel){
                currentLevel = 10;
                KeyHandler.bulletCooldown = 50;
                p.setHealth(100);
                spawnEnemies(10 + (4 * currentLevel));
            }else {
                // Move to the next level
                currentLevel++;
                KeyHandler.bulletCooldown -= 30;
                p.setHealth(p.getHealth() + 1);
                spawnEnemies(10 + (4 * currentLevel));
            }
        }

        // Update player lives and handle game over conditions
        playerLives = p.getHealth();
        if (p.getHealth() <= 0) {
            playerLives--;

            if (playerLives > 0) {
                // Player still has lives, reset player and respawn enemies
                p = new Player();
                spawnEnemies(10 + (4 * currentLevel)); // Respawn enemies for the current level
            } else {
                // No more lives, game over
                gameOver();
            }
        }
        // Update bullets and enemies

        // Update and remove player bullets that reach the top
        Iterator<Bullet> iterator = bulls.iterator();
        while (iterator.hasNext()) {
            Bullet bull = iterator.next();
            bull.update();

            // Remove bullets that have reached the top
            if (bull.getBulletY() < 0) {
                iterator.remove();
            }
        }

        // Update and remove off-screen enemies
        Iterator<Enemy> iterator1 = new ArrayList<>(enemies).iterator();
        while (iterator1.hasNext()) {
            Enemy enemy = iterator1.next();
            enemy.update();

            // Remove enemies that go off-screen
            if (enemy.getEnemyY() > getHeight()) {
                iterator1.remove();
            }
        }

        // Check for collisions
        checkBulletPlayerCollisions();
        checkBulletEnemyCollisions();
        checkPlayerEnemyCollisions();
    }

    /**
     * Method to handle rendering of the game components.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameOver) {
            // Show game over panel
            gameOverPanel.setVisible(true);
        } else {
            // Game is still active, render game components

            // Set background and draw player
            gameOverPanel.setVisible(false);
            super.setBackground(Color.black);
            p.draw(g);

            // Display cheat indicator if enabled
            if(keyH.cheatLevel){
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.setColor(Color.RED);
                g.drawString("Level Accelerator", getWidth()-200, getHeight() - 20);
            }

            // Display current level and player lives
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            g.drawString("Level: " + currentLevel, (getWidth() / 2) - 35, 20);

            // Update and draw bullets, enemy bullets, and enemies
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            g.drawString("Lives: " + playerLives, 10, getHeight() - 20);

            // Display points
            g.setColor(Color.WHITE);
            g.drawString("Points: " + points, 10, 20);

            // Update and draw bullets
            Iterator<Bullet> bulletIterator = bulls.iterator();
            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                bullet.update();
                bullet.draw(g);

                // Remove bullets that have reached the top
                if (bullet.getBulletY() < 0) {
                    bulletIterator.remove();
                }
            }

            // Update and draw enemy bullets
            for (Bullet enemyBullet : enemyBullets) {
                enemyBullet.update();
                enemyBullet.draw(g);
            }

            // Update and draw enemies
            for (Enemy enemy : enemies) {
                enemy.update();
                enemy.draw(g);
            }
        }
    }

    /**
     * Method to update enemy bullets.
     */
    public void updateEnemyBullets() {
        enemyBullets.clear(); // Clear the list before adding enemy bullets
        for (Enemy enemy : enemies) {
            enemy.updateBullets();
            enemyBullets.addAll(enemy.getEnemyBullets());
        }
    }

    /**
     * Method to spawn enemies at random positions.
     *
     * @param numEnemies The number of enemies to spawn.
     */
    public void spawnEnemies(int numEnemies) {
        for (int i = 0; i < numEnemies; i++) {
            int randomX;
            int randomY;
            int randomStrength = currentLevel + random.nextInt(3);

            // Generate random spawn positions while avoiding initial overlap
            do {
                randomX = random.nextInt(getWidth() - 55);
                randomY = random.nextInt(getHeight() - 500);
            } while (checkInitialOverlap(randomX, randomY));

            // Pass the player instance to the Enemy constructor
            enemies.add(new Enemy(p, randomX, randomY, currentLevel, randomStrength));
        }
    }

    /**
     * Method to check for collision between a bullet and the player.
     *
     * @param bullet The bullet to check for collision.
     * @param player The player to check for collision.
     * @return True if a collision is detected, false otherwise.
     */
    private boolean checkCollision(Bullet bullet, Player player) {
        Rectangle bulletBounds = new Rectangle(bullet.getBulletX(), bullet.getBulletY(), 20, 40);
        Rectangle playerBounds = new Rectangle(player.getPlayerX(), player.getPlayerY(), 35, 45);
        return bulletBounds.intersects(playerBounds);
    }

    /**
     * Method to check for collision between a bullet and an enemy.
     *
     * @param bullet The bullet to check for collision.
     * @param enemy The enemy to check for collision.
     * @return True if a collision is detected, false otherwise.
     */
    private boolean checkCollision(Bullet bullet, Enemy enemy) {
        Rectangle bulletBounds = new Rectangle(bullet.getBulletX(), bullet.getBulletY(), 20, 40);
        Rectangle enemyBounds = new Rectangle(enemy.getEnemyX(), enemy.getEnemyY(), 35, 25);
        return bulletBounds.intersects(enemyBounds);
    }

    /**
     * Method to check for collision between the player and an enemy.
     *
     * @param player The player to check for collision.
     * @param enemy The enemy to check for collision.
     * @return True if a collision is detected, false otherwise.
     */
    private boolean checkCollision(Player player, Enemy enemy) {
        Rectangle playerBounds = new Rectangle(player.getPlayerX(), player.getPlayerY(), 35, 45);
        Rectangle enemyBounds = new Rectangle(enemy.getEnemyX(), enemy.getEnemyY(), 35, 25);
        return enemyBounds.intersects(playerBounds);
    }

    /**
     * Method to check for collisions between player bullets and enemies.
     */
    private void checkBulletPlayerCollisions() {
        for (Enemy enemy : enemies) {
            for(int i = 0;i < enemy.getBullets().size();i++){
                if(checkCollision(enemy.getBullets().get(i),p)){
                    p.reduceHealth(1);
                    enemy.setBullets(i,1000);
                    // No need to check further collisions for this enemy bullet
                    break;
                }
            }
        }
    }

    /**
     * Method to check for collisions between the player and enemies.
     */
    private void checkPlayerEnemyCollisions(){
        for (Enemy enemy : enemies) {
            if(checkCollision(p,enemy)){
                p.reduceHealth(100);
                break;
            }
        }
    }

    /**
     * Method to check for collisions between player bullets and enemies.
     * If a collision is detected, update enemy health and remove the bullet.
     */
    private void checkBulletEnemyCollisions(){
        Iterator<Bullet> bulletIterator = bulls.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();

            for (Enemy enemy : enemies) {
                if (checkCollision(bullet, enemy)) {
                    enemy.reduceHealth(1);

                    if (enemy.getHealth() <= 0) {
                        // Handle enemy defeat
                        enemies.remove(enemy);
                        points += enemy.getStrength();
                    }

                    // Remove the bullet if it hits an enemy
                    bulletIterator.remove();

                    // No need to check further collisions for this bullet
                    break;
                }
            }
        }
    }

    /**
     * Method to check for initial overlap between spawn positions of enemies.
     *
     * @param x The x-coordinate of the potential spawn position.
     * @param y The y-coordinate of the potential spawn position.
     * @return True if overlap is detected, false otherwise.
     */
    private boolean checkInitialOverlap(int x, int y) {
        for (Enemy enemy : enemies) {
            if (Math.abs(x - enemy.getEnemyX()) < 55 &&
                    Math.abs(y - enemy.getEnemyY()) < 45) {
                return true; // Overlaps with an existing enemy
            }
        }
        return false; // No overlap
    }
}