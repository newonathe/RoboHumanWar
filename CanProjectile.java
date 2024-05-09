import javax.swing.*;

public class CanProjectile extends Throwable { 
    private static final int CAN_DAMAGE = 10; 

    public CanProjectile(int x, int y, ImageIcon image, double initialVelocity, double angle) {
        super(x, y, image, initialVelocity, angle, CAN_DAMAGE);
    }

    public void handlePlayerCollision(Player player) {
        player.takeDamage(damage); 
    } 
}
