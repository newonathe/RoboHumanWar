import javax.swing.*;

/**
 * Represents a fence in the game.
 * Extends the GameObject class.
 */
public class Fence extends GameObject {

    /**
     * Constructs a new Fence object.
     *
     * @param x The x-coordinate of the fence.
     * @param y The y-coordinate of the fence.
     * @param image The image icon representing the fence.
     * @param id The unique identifier of the fence.
     */
    public Fence(int x, int y, ImageIcon image, String id) {
        super(x, y, image, id);
    }
}