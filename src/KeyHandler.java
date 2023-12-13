import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyHandler class implements the KeyListener interface to handle keyboard input in the game.
 */
public class KeyHandler implements KeyListener {
    public boolean leftPressed, rightPressed, cheatLevel;
    public int bullets = 0;
    private long lastBulletTime = 0;
    public static int bulletCooldown = 300;

    /**
     * Handles key-typed events.
     *
     * @param e The KeyEvent representing the key-typed event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this implementation
    }

    /**
     * Handles key-pressed events.
     *
     * @param e The KeyEvent representing the key-pressed event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        long currentTime = System.currentTimeMillis();

        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        } else if (code == KeyEvent.VK_D) {
            rightPressed = true;
        } else if ((code == KeyEvent.VK_W || code == KeyEvent.VK_SPACE) && bullets == 0 && currentTime - lastBulletTime > bulletCooldown) {
            bullets = 1;
            lastBulletTime = currentTime;
        }

        if (code == KeyEvent.VK_U) {
            cheatLevel = false;
        }
    }

    /**
     * Handles key-released events.
     *
     * @param e The KeyEvent representing the key-released event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_W) {
            bullets = 0;
        }
    }
}
