import javax.swing.*;
import java.awt.*;

/**
 * Represents a player in the game.
 */
public class Player extends GameObject{
    //properties
    protected int health, maxHealth, loc;
    protected boolean currentTurn, idle;
    protected ImageIcon idleMotion, throwingMotion, threwMotion, deadPlayer;

    /**
     * Constructs a new player object.
     * @param x the x-coordinate of the player's position
     * @param y the y-coordinate of the player's position
     * @param image the image representing the player
     * @param maxHealth the maximum health of the player
     * @param id the unique identifier of the player
     */
    public Player (int x, int y, ImageIcon image, int maxHealth, String id) {
        super(x, y, image, id);
        this.maxHealth = maxHealth;
        health = maxHealth;
        loc = x;
    }

    /**
     * Reduces the player's health by the given amount.
     * @param damage the amount of health to reduce
     */
    public void takeDamage(int damage) {
        health -= damage;
    }

    /**
     * Returns the current health of the player.
     * @return the current health of the player
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the maximum health of the player.
     * @return the maximum health of the player
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Draws a health bar for the player at the given x-position.
     * @param g the graphics context to draw on
     * @param xPosition the x-coordinate where to draw the health bar
     */
    public void healthbar(Graphics g, int xPosition) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillRoundRect(xPosition, 70, health*5, 20, 20, 20);
    }

    /**
     * Sets the player's image to the throwing motion.
     */
    public void setThrowingState(){
        super.image = throwingMotion;
    }

    /**
     * Sets the player's image to the idle motion.
     */
    public void setIdle() {
        super.image = idleMotion;
    }

    /**
     * Sets the player's image to the thrown motion.
     */
    public void setThrowState(){
        super.image = threwMotion;
    }

    /**
     * Checks if the player is dead.
     * @return true if the player's health is less than or equal to 0, false otherwise
     */
    public boolean isDead(){
        return (health <= 0);
    }

    /**
     * Checks if the player has reached the end of the game.
     * @return true if the player's x-coordinate is greater than 960, false otherwise
     */
    public boolean playerPosition() {
        return (loc>960);
    }
}