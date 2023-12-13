import javax.swing.*;

/**
 * The SpaceInvaders class serves as the entry point for the Space Invaders game.
 * It creates a JFrame, initializes the game panel, and starts the game thread.
 */
public class SpaceInvaders {
    public static void main(String[] args) {
        // Create the main JFrame for the game
        JFrame win = new JFrame();
        win.setTitle("Space Invaders");
        win.setLocation(400, 0);

        // Initialize the game panel
        GamePanel p = new GamePanel();
        win.add(p);
        win.pack();
        p.spawnEnemies(10);

        // Configure JFrame settings
        win.setResizable(false);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);

        // Start the game thread
        p.startGameThread();

    }

}
