import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.Timer;

public class Enemy extends JComponent{
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
    public Enemy(Player player){
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
    public Enemy(Player player, int initialX, int initialY, int lvl) {
        Random rand = new Random();
        level = lvl;
        enemyHealth = rand.nextInt(1 +(lvl/3))+1;
        enemyVelocityX = rand.nextInt(1 + (lvl/2))+1;
        enemyVelocityY = rand.nextInt(lvl)+1;
        enemyX = initialX;
        enemyY = initialY;
        movementState = rand.nextInt(2);
        bullets = new ArrayList<>();
        this.targetPlayerX = player.getPlayerX();
        this.targetPlayerY = player.getPlayerY();
        scheduleShooting();
        shootingTimer.start();
    }
    public void reduceHealth(int amount){
        enemyHealth = enemyHealth - amount;
    }
    public void updateBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();

            // Remove bullets that have reached the bottom if needed
            // if (bullet.getBulletY() > getHeight()) {
            //     iterator.remove();
            // }
        }
    }
    public int getHealth(){
        return enemyHealth;
    }
    private void scheduleShooting() {
        int delay = 2000 + (int) (Math.random() * 5000); // Delay between 2 and 7 seconds

        shootingTimer.setDelay(delay);
        shootingTimer.start();
    }
    private void shoot() {
        Bullet bullet = new Bullet(enemyX, enemyY, targetPlayerX, targetPlayerY, false);
        bullets.add(bullet);
    }
    public List<Bullet> getEnemyBullets() {
        return bullets;
    }
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
    public List<Bullet> getBullets() {
        return bullets;
    }
    public void setBullets(int i, int eY){
        bullets.get(i).setBulletY(eY);
    }
    public void handleEnemyCollision() {
        // Adjust position slightly and change direction
        enemyY += 10*enemyVelocityY; // Move down by a fixed value
        enemyVelocityX = -enemyVelocityX; // Change direction
    }
    public int getEnemyX(){
        return enemyX;
    }
    public int getEnemyY(){
        return enemyY;
    }
    public void setEnemyX(int nX){
        enemyX = nX;
    }
    public void setEnemyY(int nY){
        enemyY = nY;
    }
    public int getEnemyVelocityX(){
        return enemyVelocityX;
    }
    public int getEnemyVelocityY(){
        return enemyVelocityY;
    }
    public void draw(Graphics g){
        ImageIcon image1 = new ImageIcon(("enemy.png"));
        badGuy = image1.getImage();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(badGuy,enemyX,enemyY,this);
    }
}
