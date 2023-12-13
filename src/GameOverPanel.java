import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    private JLabel levelLabel;
    private JLabel pointsLabel;

    public GameOverPanel() {
        setPreferredSize(new Dimension(900, 1000));
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));

        levelLabel = new JLabel("Level: ", SwingConstants.CENTER);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 30));

        pointsLabel = new JLabel("Points: ", SwingConstants.CENTER);
        pointsLabel.setForeground(Color.WHITE);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        add(gameOverLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0); // Add some space between Game Over and Level
        add(levelLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 0, 0); // Add some space between Level and Points
        add(pointsLabel, gbc);
    }

    public void updateGameOverPanel(int finalLevel, int finalPoints) {
        levelLabel.setText("Level: " + finalLevel);
        pointsLabel.setText("Points: " + finalPoints);
        repaint();
    }
}
