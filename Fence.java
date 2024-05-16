/**

	@author Ethan Owen Taruc (236196)
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