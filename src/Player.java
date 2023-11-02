public class Player {
    private int playerHealth;
    private int playerSpeed;
    private int playerX;
    private int playerY;
    private static final int DEFAULT_PLAYER_HEALTH = 3;
    private static final int DEFAULT_PLAYER_SPEED = 5;
    private static final int DEFAULT_PLAYER_X = 0;
    private static final int DEFAULT_PLAYER_Y = 0;
    public Player(){
        playerHealth = DEFAULT_PLAYER_HEALTH;
        playerSpeed = DEFAULT_PLAYER_SPEED;
        playerX = DEFAULT_PLAYER_X;
        playerY = DEFAULT_PLAYER_Y;
    }
}
