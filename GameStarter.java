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

/**
 * This class is responsible for starting the game.
 * It creates an instance of the GameFrame class and sets up the GUI.
 */
public class GameStarter {

    /**
     * The main method is the entry point of the application.
     * It initializes the game by creating a new GameFrame instance and calling the setUpGUI method.
     *
     * @param args Command-line arguments (not used in this case)
     */
    public static void main(String[] args) {
        GameFrame frame = new GameFrame(); // Create a new instance of GameFrame
        frame.setUpGUI(); // Set up the GUI for the game
    }
}