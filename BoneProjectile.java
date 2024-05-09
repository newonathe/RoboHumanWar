import javax.swing.*;

public class BoneProjectile extends Throwable { 
    private static final int BONE_DAMAGE = 10;

    public BoneProjectile(int x, int y, ImageIcon image, double initialVelocity, double angle) {
        super(x, y, image, initialVelocity, angle, BONE_DAMAGE);
    }

    public void handlePlayerCollision(Player player) {
        player.takeDamage(damage);
    } 
}
