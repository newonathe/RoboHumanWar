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
import java.awt.*;

/**
 * This class represents the main game frame.
 * It sets up the JFrame and GameCanvas for the game.
 */
public class GameFrame {
    private JFrame frame;
    private GameCanvas canvas;

    // Game window dimensions
    int w = 1280;
    int h = 720;

    /**
     * Constructor for GameFrame.
     * Initializes the JFrame and GameCanvas.
     */
    public GameFrame() {
        frame = new JFrame("Cats vs Dogs");
        canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(w, h));
    }

    /**
     * Getter for the JFrame.
     * @return the JFrame object
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Sets up the GUI for the game.
     * Adds the GameCanvas to the JFrame, sets up mouse listener,
     * and other necessary configurations.
     */
    public void setUpGUI() {
        Container contentPane = frame.getContentPane();
        contentPane.add(canvas);
        frame.pack();
        frame.addMouseListener(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setVisible(true);
    }
}