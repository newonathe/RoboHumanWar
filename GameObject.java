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
        this.width = image.getIconWidth(); 
        this.height = image.getIconHeight();
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
    // public void draw(Graphics g) {
    //     g.drawImage(image, (int) x, (int) y, null); 
    // }

    // Basic rectangular collision detection (could be refined later)
    public boolean checkCollision(GameObject other) {
        return (this.x < other.getX() + other.getWidth() &&
                this.x + this.width > other.getX() &&
                this.y < other.getY() + other.getHeight() &&
                this.y + this.height > other.getY());
    }
}
