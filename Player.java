import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Player extends GameObject{
    //properties
    protected int health, maxHealth, loc;
    protected boolean currentTurn, throwing, threw, dead;
    protected ImageIcon idle, throwingMotion, threwMotion, deadPlayer;
    protected List<PowerUp> powerUps;


    public Player (int x, int y, ImageIcon image, int maxHealth) {
        super(x, y, image);
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
        g.setColor(Color.RED);
        g.fillRect(xPosition, 100, health*5, 30);
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

    public boolean isThrowing(){
        return throwing;
    }

    public void setThrowingState(boolean throwing){
        this.throwing = throwing;
    }

    public boolean didThrow(){
        return threw;
    }

    public void setThrowState(boolean threw){
        this.threw = threw;
    }
    public boolean isDead(){
        return (health <= 0);
    }

    public void currentMotion(){
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
    }

    // if x>960 = dog; else, cat
    public boolean playerPosition() {
        return (loc>960);
    }

    }


    
    