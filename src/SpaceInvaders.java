import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpaceInvaders{
    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setTitle("Space Invaders");
        win.setLocation(400,10);
        GamePanel p = new GamePanel();
        win.add(p);
        win.pack();
        win.setResizable(false);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
        p.startGameThread();
        Player play = new Player();
        win.add(play);
    }
}