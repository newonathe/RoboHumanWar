import java.awt.*;
import javax.swing.*;

/**
 * This class represents an angle direction object that rotates around a pivot point.
 * It extends the GameObject class and overrides its paint method to rotate the image.
 */
public class AngleDirection extends GameObject {

    private int pivot1, pivot2; // Pivot points for rotation
    private double angle; // Current angle of rotation
    private boolean goUp; // Flag to determine the direction of rotation
    private double rSpeed = 0.6; // Rotation speed
    private boolean rotating = true; // Flag to control rotation

    /**
     * Constructor for AngleDirection class.
     *
     * @param x The x-coordinate of the object's position.
     * @param y The y-coordinate of the object's position.
     * @param image The image to be displayed for the object.
     * @param player The player object to determine the rotation direction.
     * @param id The unique identifier for the object.
     */
    public AngleDirection(int x, int y, ImageIcon image, Player player, String id) {
        super(x, y, image, id);
        pivot1 = x;
        pivot2 = y+120;
        generateAngle(player);
    }

    /**
     * Method to generate a random angle for rotation based on the player's position.
     *
     * @param player The player object to determine the rotation direction.
     */
    public void generateAngle(Player player) {
        rotating = true;
        if (player.playerPosition()){
            angle = (int) (Math.random() * 115 + 85);
        } else {
            angle = (int) (Math.random() * 115 - 20);
        }
    }

    /**
     * Method to rotate the angle based on the player's position and rotation speed.
     *
     * @param player The player object to determine the rotation direction.
     */
    public void rotateAngle(Player player) {
        if (rotating) {
            if (player.playerPosition()){
                if (Math.round(angle) == 200) {
                    goUp = true;
                } else if ( Math.round(angle) == 85) {
                    goUp = false;
                }
                
                if (goUp) {
                    angle -= rSpeed;
                } else {
                    angle += rSpeed;
                }
            } else {
                if (Math.round(angle) == -20) {
                    goUp = true;
                } else if ( Math.round(angle) == 95) {
                    goUp = false;
                }
                
                if (goUp) {
                    angle += rSpeed;
                } else {
                    angle -= rSpeed;
                }
            }
        }
    }

    /**
     * Method to stop the rotation of the angle.
     */
    public void stopRotation() {
        rotating = false; // Stop the rotation
    }

    /**
     * Method to get the current angle of rotation.
     *
     * @return The current angle of rotation.
     */
    public double getAngle() {
        return -angle;
    }

    /**
     * Overridden paint method to rotate the image of the object.
     *
     * @param g The Graphics object to draw on.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(90 - angle), pivot1, pivot2);
        image.paintIcon(this, g2d, x, y);
    }
}