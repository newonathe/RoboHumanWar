import java.awt.*;
import javax.swing.*;

/**
 * This is an abstract class representing a game object.
 * It extends JPanel and provides common attributes and methods for game objects.
 */
public abstract class GameObject extends JPanel {
    protected int x, y; // Position of the object
    protected int width, height; // Size of the object
    protected String id; //ID of the object
    protected ImageIcon image; // Visual representation

     /**
     * Constructor for the GameObject class.
     *
     * @param x The initial x-coordinate of the object.
     * @param y The initial y-coordinate of the object.
     * @param image The image representing the object.
     * @param id The unique identifier of the object.
     */
    public GameObject(int x, int y, ImageIcon image, String id) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.id = id;

        // Initialize width and height (you may adjust this based on needs)
        width = image.getIconWidth(); 
        height = image.getIconHeight();
    }

      // Accessor methods (Getters)

    /**
     * Returns the x-coordinate of the object.
     *
     * @return The x-coordinate of the object.
     */
    public int getX() { return x; }

    /**
     * Returns the y-coordinate of the object.
     *
     * @return The y-coordinate of the object.
     */
    public int getY() { return y; }

    /**
     * Returns the width of the object.
     *
     * @return The width of the object.
     */
    public int getWidth() { return width; } 

    /**
     * Returns the height of the object.
     *
     * @return The height of the object.
     */
    public int getHeight() { return height; }

    /**
     * Returns the image representing the object.
     *
     * @return The image representing the object.
     */
    public ImageIcon getImage() { return image; } 

     /**
     * Paints the object on the given Graphics context.
     *
     * @param g The Graphics context on which to paint the object.
     */
    public void paintComponent(Graphics g) {
        image.paintIcon(this, g, x, y);
    }


    /**
     * Checks if this object collides with another object.
     *
     * @param other The other object to check for collision.
     * @return True if the objects collide, false otherwise.
     */
    public boolean checkCollision(GameObject other) {
        if (other.id.equals("fence")) {
            return (x < other.getX() + other.getWidth() - 30 &&
            x + this.width   > other.getX() + 30 &&
            y  < other.getY() + other.getHeight() &&
            y + this.height   > other.getY() + 40);
        } else 
        if (other.id.equals("human") || other.id.equals("robot")) {
            return (x  < other.getX() + other.getWidth() - 15 &&
            x + this.width  > other.getX() + 15 &&
            y  < other.getY() + other.getHeight() &&
            y + this.height  > other.getY() + 35);
        } else 
        {
            return (x  < other.getX() + other.getWidth() &&
            x + this.width  > other.getX() &&
            y  < other.getY() + other.getHeight() &&
            y + this.height  > other.getY());
        }
    }  
}
