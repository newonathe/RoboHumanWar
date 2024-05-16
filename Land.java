import javax.swing.*;

/**
 * Represents a land object in the game.
 * Extends the GameObject class.
 */
public class Land extends GameObject {

    /**
     * Constructs a new Land object with the given parameters.
     *
     * @param x The x-coordinate of the land.
     * @param y The y-coordinate of the land.
     * @param image The image icon representing the land.
     * @param id The unique identifier of the land.
     */
    public Land(int x, int y, ImageIcon image, String id) {
        super(x, y, image, id);
    }
}