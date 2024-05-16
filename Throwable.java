/**
 * 	@author Ethan Owen Taruc (236196)
    @author Keith Ayeras (230564)
	@version 17 May 2024
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/


import javax.swing.*;

/**
 * Represents a throwable object in the game.
 * This class extends the GameObject class and adds specific properties and behaviors for throwable objects.
 */
public class Throwable extends GameObject {
    /** The horizontal velocity of the throwable object. */
    protected double velocityX, velocityY;  
    /** The damage the throwable object deals when it hits a player. */
    protected int damage;  
    /** Indicates whether the throwable object is currently sliding on the ground. */
    protected boolean isSliding; 

    /** Constants for physics calculations. */
    private static final double GRAVITY = 0.2; 
    private static final double BOUNCE_REDUCTION = 0.6; 
    private static final double SLIDE_FRICTION = 0.1; 
    private static final double VELOCITY_THRESHOLD = 0.1; // For stopping the slide

    /**
     * Constructs a throwable object with the given parameters.
     *
     * @param x The initial x-coordinate of the throwable object.
     * @param y The initial y-coordinate of the throwable object.
     * @param image The image icon representing the throwable object.
     * @param initialVelocity The initial velocity of the throwable object.
     * @param angle The angle at which the throwable object is thrown.
     * @param id The unique identifier of the throwable object.
     */
    public Throwable(int x, int y, ImageIcon image, double initialVelocity, double angle, String id) {
        super(x, y, image, id); 
        this.damage = 10;

        // Calculate initial velocities based on throw angle
        this.velocityX = initialVelocity/5 * Math.cos(Math.toRadians(angle));
        this.velocityY = initialVelocity/5 * Math.sin(Math.toRadians(angle));
    }

    /**
     * Handles the collision between the throwable object and a player.
     *
     * @param player The player object that collided with the throwable object.
     */
    public void handlePlayerCollision(Player player) {
        player.takeDamage(damage);
    } 

    /**
     * Updates the position and state of the throwable object based on its current velocity and state.
     */
    public void throwProjectile() {
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
    
    /**
     * Returns the horizontal velocity of the throwable object.
     *
     * @return The horizontal velocity of the throwable object.
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * Checks for collision between the throwable object and another game object.
     *
     * @param object The game object to check for collision with.
     * @return True if a collision occurred, false otherwise.
     */
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

    /**
     * Determines whether the throwable object's turn has ended.
     *
     * @return True if the throwable object's turn has ended (i.e., it has stopped moving horizontally), false otherwise.
     */
    public boolean endTurn() {
        return velocityX == 0;
    }

    /**
     * Handles the collision between the throwable object and a bouncy object (fence or player).
     */
    protected void handleBounceCollision() {
        velocityX *= -BOUNCE_REDUCTION; // Reverse direction, lose some velocity
    } 

    /**
     * Handles the collision between the throwable object and the ground.
     */
    private void handleGroundCollision() {
        isSliding = true; 
    }

    /**
     * Slows down the throwable object by applying friction when it is sliding on the ground.
     */
    private void slowDown() {
        // Gradually reduce velocity until below threshold
        if (Math.abs(velocityX) > VELOCITY_THRESHOLD) {
            velocityX -= Math.signum(velocityX) * SLIDE_FRICTION;
            x += velocityX;
        } else {
            velocityX = 0; // Stop completely
            isSliding = false; // No longer sliding
        }
    }

}