import javax.swing.*;
import java.awt.*;

public class Bullet extends JComponent{
    private int bulletVelocity;
    private int bulletX;
    private int bulletY;
    private int targetX;
    private int targetY;
    private static final int DEFAULT_BULLET_VELOCITY = 3;
    private static final int DEFAULT_BULLET_X = 50;
    private static final int DEFAULT_BULLET_Y = 840;
    private Image bullet;
    private boolean isPlayerBullet;
    public Bullet(){
        bulletVelocity = DEFAULT_BULLET_VELOCITY;
        bulletX = DEFAULT_BULLET_X;
        bulletY = DEFAULT_BULLET_Y;
        this.targetX = targetX;
        this.targetY = targetY;
    }
    public Bullet(int startX, int startY, int targetX, int targetY, boolean isPlayerBullet) {
        this.isPlayerBullet = isPlayerBullet;
        bulletVelocity = DEFAULT_BULLET_VELOCITY;
        bulletX = startX;
        bulletY = startY;
        this.targetX = targetX; // Assign the targetX and targetY here
        this.targetY = targetY;
    }
    public void setBulletX(int nX){
        bulletX = nX;
    }
    public void setBulletY(int nY){
        bulletY = nY;
    }
    public int getBulletX(){
        return bulletX;
    }
    public int getBulletY(){
        return bulletY;
    }
    public int getBulletVelocity(){
        return bulletVelocity;
    }
    public void update() {
        if (isPlayerBullet) {
            // Player bullet goes up
            bulletY -= bulletVelocity;
        } else {
            // Enemy bullet goes down
            bulletY += bulletVelocity;
        }
    }
    public boolean isPlayerBullet() {
        return isPlayerBullet;
    }
    public void draw(Graphics g) {
        ImageIcon image1 = new ImageIcon("bullet.png");
        bullet = image1.getImage();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bullet, bulletX, bulletY, this);
    }
}
