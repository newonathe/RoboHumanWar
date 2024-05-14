    import java.awt.*;
// import java.awt.event.*;
import javax.swing.*;

public class AngleDirection extends GameObject {
    int pivot1, pivot2;
    double angle;
    boolean goUp;
    Timer timer;
    double rSpeed = 0.6;
    boolean rotating = true;

    public AngleDirection(int x, int y, ImageIcon image, Player player) {
        super(x, y, image);
        pivot1 = x;
        pivot2 = y+80;
        generateAngle(player);
    }

    //call
    public void generateAngle(Player player) {
        rotating = true;
        if (player.playerPosition()){
            angle = (int) (Math.random() * 115 + 85);
        } else {
            angle = (int) (Math.random() * 115 - 20);
        }
    }

    //call
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

    public void stopRotation() {
        rotating = false; // Stop the rotation
    }

    public double getAngle() {
        return angle;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(90 - angle), pivot1, pivot2);
        image.paintIcon(this, g2d, x, y);
    }
}
