import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_W;

public class KeyHandler implements KeyListener {
    public boolean leftPressed, rightPressed;
    public int bullets = 0;
    private long lastBulletTime = 0;
    public static int bulletCooldown = 300;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        long currentTime = System.currentTimeMillis();
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        } else if (code == KeyEvent.VK_D) {
            rightPressed = true;
        } else if (code == KeyEvent.VK_W && bullets == 0 && currentTime - lastBulletTime > bulletCooldown) {
            bullets = 1;
            lastBulletTime = currentTime;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_W){
            bullets = 0;
        }
    }
}
