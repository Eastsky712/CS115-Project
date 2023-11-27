import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    Player p = new Player();
    int FPS = 60;
    private GameOverPanel gameOverPanel;
    private int playerLives;
    private int currentLevel = 1;
    private boolean gameOver = false;
    ArrayList<Bullet> bulls = new ArrayList<>();
    ArrayList<Bullet> enemyBullets = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    Random random = new Random();

    public GamePanel(){
        this.setPreferredSize(new Dimension(900,1000));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.gameOverPanel = new GameOverPanel();
        this.add(gameOverPanel);
    }
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    private void gameOver() {
        gameOver = true;
        repaint();
    }
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null){

            update();

            repaint();

            updateEnemyBullets();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void update() {
        if (keyH.rightPressed) {
            if (p.getPlayerX() < 838) {
                p.setPlayerX(p.getPlayerX() + p.getPlayerVelocity());
            }
        } else if (keyH.leftPressed) {
            if (p.getPlayerX() > 0) {
                p.setPlayerX(p.getPlayerX() - p.getPlayerVelocity());
            }
        }
        if (keyH.bullets == 1) {
            bulls.add(new Bullet(p.getPlayerX(), 840,p.getPlayerX(),0,true));
            keyH.bullets = 0;
        }
        for (Bullet bull : bulls) {
            bull.setBulletY(bull.getBulletY() - bull.getBulletVelocity());
        }
        if (enemies.isEmpty()) {
            // Proceed to the next level
            KeyHandler kh = new KeyHandler();
            currentLevel++;
            KeyHandler.bulletCooldown -= 30;
            p.setHealth(p.getHealth() + 1);
            spawnEnemies(10 + (4 * currentLevel)); // You can adjust the number of enemies based on the level
        }
        playerLives = p.getHealth();

        if (p.getHealth() <= 0) {
            playerLives--;

            // Reset player health and handle game over logic
            if (playerLives > 0) {
                p = new Player(); // Reset player
                spawnEnemies(10 + (4 * currentLevel)); // Respawn enemies for the current level
            } else {
                // Game over logic (you may want to add more here)
                gameOver();
            }
        }

        Iterator<Bullet> iterator = bulls.iterator();
        while (iterator.hasNext()) {
            Bullet bull = iterator.next();
            bull.update();

            // Remove bullets that have reached the top
            if (bull.getBulletY() < 0) {
                iterator.remove();
            }
        }
        Iterator<Enemy> iterator1 = new ArrayList<>(enemies).iterator();
        while (iterator1.hasNext()) {
            Enemy enemy = iterator1.next();
            enemy.update();

            // Remove enemies that go off-screen
            if (enemy.getEnemyY() > getHeight()) {
                iterator1.remove();
            }
        }
        checkBulletPlayerCollisions();
        checkBulletEnemyCollisions();
        checkPlayerEnemyCollisions();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameOver) {
            // Show game over panel
            gameOverPanel.setVisible(true);
        } else {
            // Show the game panel
            gameOverPanel.setVisible(false);
            super.setBackground(Color.black);
            p.draw(g);

            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            g.drawString("Level: " + currentLevel, getWidth() / 2, 20);

            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            g.drawString("Lives: " + playerLives, 10, getHeight() - 20);
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
    public void updateEnemyBullets() {
        enemyBullets.clear(); // Clear the list before adding enemy bullets
        for (Enemy enemy : enemies) {
            enemy.updateBullets();
            enemyBullets.addAll(enemy.getEnemyBullets());
        }
    }
    public void spawnEnemies(int numEnemies) {
        for (int i = 0; i < numEnemies; i++) {
            int randomX;
            int randomY;

            do {
                randomX = random.nextInt(getWidth() - 55);
                randomY = random.nextInt(getHeight() - 500);
            } while (checkInitialOverlap(randomX, randomY));

            // Pass the player instance to the Enemy constructor
            enemies.add(new Enemy(p, randomX, randomY, currentLevel));
        }
    }
    private boolean checkCollision(Bullet bullet, Player player) {
        Rectangle bulletBounds = new Rectangle(bullet.getBulletX(), bullet.getBulletY(), 20, 40);
        Rectangle playerBounds = new Rectangle(player.getPlayerX(), player.getPlayerY(), 35, 45);
        return bulletBounds.intersects(playerBounds);
    }
    private boolean checkCollision(Bullet bullet, Enemy enemy) {
        Rectangle bulletBounds = new Rectangle(bullet.getBulletX(), bullet.getBulletY(), 20, 40);
        Rectangle enemyBounds = new Rectangle(enemy.getEnemyX(), enemy.getEnemyY(), 35, 25);
        return bulletBounds.intersects(enemyBounds);
    }
    private boolean checkCollision(Player player, Enemy enemy) {
        Rectangle playerBounds = new Rectangle(player.getPlayerX(), player.getPlayerY(), 35, 45);
        Rectangle enemyBounds = new Rectangle(enemy.getEnemyX(), enemy.getEnemyY(), 35, 25);
        return enemyBounds.intersects(playerBounds);
    }
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
    private void checkPlayerEnemyCollisions(){
        for (Enemy enemy : enemies) {
            if(checkCollision(p,enemy)){
                p.reduceHealth(100);
                break;
            }
        }
    }
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
                    }

                    // Remove the bullet if it hits an enemy
                    bulletIterator.remove();

                    // No need to check further collisions for this bullet
                    break;
                }
            }
        }
    }
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
