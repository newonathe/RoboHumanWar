import javax.swing.*;

public abstract class Throwable extends GameObject {
    protected double velocityX, velocityY;  
    protected int damage;  
    protected boolean isSliding; 

    private static final double GRAVITY = 0.2; 
    private static final double BOUNCE_REDUCTION = 0.6; 
    private static final double SLIDE_FRICTION = 0.1; 
    private static final double VELOCITY_THRESHOLD = 0.1; // For stopping the slide

    public Throwable(int x, int y, ImageIcon image, double initialVelocity, double angle, int damage) {
        super(x, y, image); 
        this.damage = damage;

        // Calculate initial velocities based on throw angle
        this.velocityX = initialVelocity/5 * Math.cos(Math.toRadians(angle));
        this.velocityY = initialVelocity/5 * Math.sin(Math.toRadians(angle));
    }

    public void update() {
        if (!isSliding) { 
            // If in the air, apply gravity and update position
            x += velocityX;
            y += velocityY;
            velocityY += GRAVITY; // Apply gravity
        } else {
            // If sliding, apply friction and check for stopping
            slowDown(); 
        }
    }
    
    public double getVelocityX() {
        return velocityX;
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
    }

    protected void handleBounceCollision() {
        velocityX *= -BOUNCE_REDUCTION; // Reverse direction, lose some velocity
        isSliding = true; // Start sliding after bouncing
    } 

    protected abstract void handlePlayerCollision(Player player);

    private void handleGroundCollision() {
        isSliding = true; 
    }

    private void slowDown() {
        // Gradually reduce velocity until below threshold
        if (Math.abs(velocityX) > VELOCITY_THRESHOLD) {
            velocityX -= Math.signum(velocityX) * SLIDE_FRICTION;
        } else {
            velocityX = 0; // Stop completely
            isSliding = false; // No longer sliding
        }
    }
}
