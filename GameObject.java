import java.awt.*;
import javax.swing.*;

public abstract class GameObject extends JPanel {
    protected int x, y; // Position of the object
    protected int width, height; // Size of the object
    protected ImageIcon image; // Visual representation

    // Constructor 
    public GameObject(int x, int y, ImageIcon image) {
        this.x = x;
        this.y = y;
        this.image = image;

        // Initialize width and height (you may adjust this based on your needs)
        width = image.getIconWidth(); 
        height = image.getIconHeight();
    }

    // Accessor methods (Getters)
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; } 
    public int getHeight() { return height; }
    public ImageIcon getImage() { return image; } 


    public void paintComponent(Graphics g) {
        image.paintIcon(this, g, x, y);
    }

    public boolean checkCollision(GameObject other) {
    int tolerance = 10; // Adjust
    return (x - tolerance < other.getX() + other.getWidth() &&
            x + this.width + tolerance > other.getX() &&
            y - tolerance < other.getY() + other.getHeight() &&
            y + this.height + tolerance > other.getY());
}

}
