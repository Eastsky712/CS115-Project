import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    Player p = new Player();
    int FPS = 60;

    public GamePanel(){
        this.setPreferredSize(new Dimension(900,1000));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null){

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void update(){
        if(keyH.rightPressed){
            p.setPlayerX(p.getPlayerX() + p.getPlayerVelocity());
        }else if(keyH.leftPressed){
            p.setPlayerX(p.getPlayerX() - p.getPlayerVelocity());
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        super.setBackground(Color.black);
        p.draw(g);
    }
}
