import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

    public GameOverPanel() {
        setPreferredSize(new Dimension(900, 1000));
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(gameOverLabel, BorderLayout.CENTER);
    }
}
