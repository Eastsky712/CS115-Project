public class Enemy {
    private int enemyHealth;
    private int enemySpeed;
    private int enemyX;
    private int enemyY;
    private static final int DEFAULT_ENEMY_HEALTH = 1;
    private static final int DEFAULT_ENEMY_SPEED = 2;
    private static final int DEFAULT_ENEMY_X = 100;
    private static final int DEFAULT_ENEMY_Y = 100;
    public Enemy(){
        enemyHealth = DEFAULT_ENEMY_HEALTH;
        enemySpeed = DEFAULT_ENEMY_SPEED;
        enemyX = DEFAULT_ENEMY_X;
        enemyY = DEFAULT_ENEMY_Y;
    }
}
