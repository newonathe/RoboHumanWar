import javax.swing.*;
import java.awt.*;

/**
 * This class represents a power bar in a game.
 * It extends the GameObject class and provides methods to manipulate and display the power bar.
 */
public class PowerBar extends GameObject{
    /** The current strength of the power bar. */
    int barStrength;
    /** Indicates whether the power bar is locked. */
    boolean isLocked = false;
    /** The width of the power bar. */
    int barWidth = 20;
    /** The x-coordinate of the power bar. */
    int x;
    /** The y-coordinate of the power bar. */
    int y;
    /** Indicates whether the power bar should move up or down. */
    boolean willGoUp;
    /** The speed at which the power bar bounces. */
    int bounceSpeed = 1;

    /**
     * Constructs a new PowerBar object.
     * @param x The x-coordinate of the power bar.
     * @param y The y-coordinate of the power bar.
     * @param image The image of the power bar.
     * @param id The id of the power bar.
     */
    public PowerBar(int x, int y, ImageIcon image, String id) {
        super(x, y, image, id);
        this.x = x-250; //+ width of image
        this.y = y+100; // + height of image
        reset();
    } 

    /**
     * Resets the power bar to its initial state.
     */
    public void reset() {
        isLocked = false;
        generateBar();
    }

    /**
     * Generates a new random strength for the power bar.
     */
    public void generateBar() {
        barStrength = (int) (Math.random() * 100);
    }

    /**
     * Locks the power bar and returns its current strength.
     * @return The current strength of the power bar.
     */
    public int getThrowStrength() {
        isLocked = true; // Lock the bar when this method is called
        return barStrength;
    }

    /**
     * Manipulates the power bar's strength based on its current state.
     */
    public void bounceBar() {
        if (isLocked){
            generateBar();
            return;
        } 
        
        if (barStrength == 0) {
           willGoUp = true;
        } else if (barStrength == 100) {
            willGoUp = false;
        }
        
        if (willGoUp) {
            barStrength += bounceSpeed;
        } else {
            barStrength -= bounceSpeed;
        }
    }

    /**
     * Paints the power bar on the given graphics context.
     * @param g The graphics context on which to paint the power bar.
     */
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.fillRoundRect(x, y-100, barWidth, 100, 20, 20);
        g2d.setColor(Color.blue);
        g2d.fillRoundRect(x, y-barStrength, barWidth, barStrength, 20, 20);
    }
}