import javax.swing.*;
import java.awt.*;

/**
 * The GameOverPanel class represents the panel displayed when the game is over.
 * It includes a message indicating "Game Over."
 */
public class GameOverPanel extends JPanel {

    /**
     * Constructor for the GameOverPanel class.
     */
    public GameOverPanel() {
        setPreferredSize(new Dimension(900, 1000));
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        // Create and configure the "Game Over" label
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));

        // Add the label to the center of the panel
        add(gameOverLabel, BorderLayout.CENTER);
    }
}
