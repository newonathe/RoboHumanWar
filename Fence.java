import javax.swing.*;

public class Fence extends Obstacle {

    public Fence(int x, int y, ImageIcon image) {
        super(x, y, image);
    }

    @Override
    public boolean checkCollision(GameObject object) {
        boolean collided = super.checkCollision(object);
        if (collided && object instanceof Throwable) {
            ((Throwable) object).handleBounceCollision(); // Call bounce behavior 
        }
        return collided;
    }

}

