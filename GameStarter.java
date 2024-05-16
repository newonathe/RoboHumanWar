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