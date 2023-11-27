import javax.swing.*;

public class SpaceInvaders{
    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setTitle("Space Invaders");
        win.setLocation(400,0);

        GamePanel p = new GamePanel();
        win.add(p);
        win.pack();
        p.spawnEnemies(10);

        win.setResizable(false);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
        p.startGameThread();
    }
}