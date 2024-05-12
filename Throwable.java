import javax.swing.*;

public abstract class Throwable extends GameObject {
    protected double velocityX, velocityY;  
    protected int damage;  
    protected boolean isSliding; 

    private static final double GRAVITY = 0.2; 
    private static final double BOUNCE_REDUCTION = 0.6; 
    private static final double SLIDE_FRICTION = 0.1; 

    public Throwable(int x, int y, ImageIcon image, double initialVelocity, double angle, int damage) {
        super(x, y, image); // Call the parent (GameObject) constructor
        this.damage = damage;
        isSliding = false; 

        // Calculate initial velocities based on throw angle
        this.velocityX = initialVelocity * Math.cos(Math.toRadians(angle));
        this.velocityY = initialVelocity * Math.sin(Math.toRadians(angle));
    }


    public void update() {
        if (!isSliding) { 
            x += velocityX;
            y += velocityY;
            velocityY += GRAVITY; // Apply gravity
        } else {
            slowDown(); 
        }
    }

    @Override
    public boolean checkCollision(GameObject object) {
        boolean collided = super.checkCollision(object); 
        if (collided) {
            if (object instanceof Fence || object instanceof Player) {
                handleBounceCollision();
            } else if (object instanceof Land) {
                handleGroundCollision();
            }
        }
        return collided; 
    } //projectile removal in canvas

    protected void handleBounceCollision() {
        velocityX *= -BOUNCE_REDUCTION; // Reverse direction, lose some velocity
        isSliding = true; 
    }

    protected abstract void handlePlayerCollision(Player player);

    private void handleGroundCollision() {
        isSliding = true; 
    }

    private void slowDown() {
        if (Math.abs(velocityX) > 0.1) { 
            velocityX -= Math.signum(velocityX) * SLIDE_FRICTION;
        } else {
            velocityX = 0;
            isSliding = false; 
        }
    } 
}
