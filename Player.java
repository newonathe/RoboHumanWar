import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Player extends GameObject{
    //properties
    protected int health, maxHealth, loc;
    protected boolean currentTurn, idle;
    protected ImageIcon idleMotion, throwingMotion, threwMotion, deadPlayer;
    protected List<PowerUp> powerUps;


    public Player (int x, int y, ImageIcon image, int maxHealth, String id) {
        super(x, y, image, id);
        this.maxHealth = maxHealth;
        health = maxHealth;
        loc = x;

        powerUps = new ArrayList<>();
    }
    

    public void takeDamage(int damage) {
        health -= damage;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void healthbar(Graphics g, int xPosition) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillRoundRect(xPosition, 70, health*5, 20, 20, 20);
    }

    //just the coordinates of x and y here but in the logic for the actual game lets make
    // if statements to identify the left, middle and right positions for each
    public void move(int x, int y) {
        super.x = x;
        super.y = y;
    }

    public boolean turnTracker() {
        return currentTurn;
    }

    public void setTurn(boolean currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void setThrowingState(){
        /* this.threw = false;
        this.throwing = true; */
        super.image = throwingMotion;
    }

    public void setIdle() {
        /* this.throwing = false;
        this.threw = false; */
        super.image = idleMotion;
    }

    public void setThrowState(){
        /* this.throwing = false;
        this.threw = true; */
        super.image = threwMotion;
    }
    public boolean isDead(){
        return (health <= 0);
    }

    /* public void currentMotion(){
        if (throwing) {
            super.image = throwingMotion;
        }
        else if (threw) {
            super.image = threwMotion;
        }
        else {
            if (isDead()) {
                super.image = deadPlayer;
            } else {
                super.image = idle;
            }
        }
    } */

    // if x>960 = dog; else, cat
    public boolean playerPosition() {
        return (loc>960);
    }

    }


    
    