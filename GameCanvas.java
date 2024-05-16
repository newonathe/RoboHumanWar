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
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

/**
 * GameCanvas class represents the main game panel.
 * It handles all the game logic, graphics, and user interactions.
 */
public class GameCanvas extends JComponent implements ActionListener, MouseListener {
    ImageIcon bgImage, humanface, robotface, humanfacewin, robotfacewin,
    humanImage, robotImage, humanFire, robotFire, humanThrew, robotThrew,
     fenceImage, arrowImage, energyorb, gunshot, landImage, humanWin, robotWin;
    
    AngleDirection arrowHuman, arrowRobot;
    PowerBar humanBar, robotBar;

    Fence fence;
    Land ground;
    Player human, robot;
    ArrayList<Throwable> projectiles;
    
    Socket socket;
    /* ReadFromServer rfsRunnable;
    WriteToServer wtsRunnable; */
    Timer animationTimer;
    int humanXPosition, robotXPosition, YPosition, playerID;
    boolean humanTurn;
    String music, robotdmg, humandmg, gunshot1, gunshot2, laser1, laser2, collision, gameover, gameover2, lock;
    
    TurnState currentState;
    enum TurnState {
        IDLE, AWAITING_ARROW, ROTATING_ARROW, CHARGING_POWER, FIRING_PROJECTILE;
    }
    
     /**
     * Constructor for GameCanvas class.
     * Initializes all the game components, graphics, and user interface elements.
     */
    public GameCanvas() {

        bgImage = new ImageIcon("media resources/bg.png");
        humanface = new ImageIcon("media resources/humanface.png");
        humanfacewin = new ImageIcon("media resources/humanfacewin.png");
        robotface = new ImageIcon("media resources/robotface.png");
        robotfacewin = new ImageIcon("media resources/robotfacewin.png");
        humanImage = new ImageIcon("media resources/human.gif");
        robotImage = new ImageIcon("media resources/robot.gif");
        humanFire = new ImageIcon("media resources/humanturn.gif");
        robotFire = new ImageIcon("media resources/robotturn.gif");
        humanThrew = new ImageIcon("media resources/humanthrew.gif");
        robotThrew = new ImageIcon("media resources/robotthrew.gif");
        fenceImage = new ImageIcon("media resources/fence.gif");
        arrowImage = new ImageIcon("media resources/arrow.png");
        energyorb = new ImageIcon("media resources/energyorb.gif");
        gunshot = new ImageIcon("media resources/gunshot.gif");
        landImage = new ImageIcon("media resources/land.png");
        humanWin = new ImageIcon("media resources/humanWin.png");
        robotWin = new ImageIcon("media resources/robotWin.png");
        music = "media resources/music.wav";
        robotdmg = "media resources/robotdmg.wav";
        humandmg = "media resources/humandmg.wav";
        gunshot1 = "media resources/gunshot1.wav";
        gunshot2 = "media resources/gunshot2.wav";
        laser1 = "media resources/laser1.wav";
        laser2 = "media resources/laser2.wav";
        collision = "media resources/collision.wav";
        gameover = "media resources/gameover.wav";
        gameover2 = "media resources/gameover2.wav";
        lock = "media resources/lock.wav";

        humanXPosition = 100;
        robotXPosition = 1060;
        YPosition = 450;

        human = new Player(humanXPosition, YPosition, humanImage, 100, "human");
        human.idleMotion = humanImage;
        human.throwingMotion = humanFire;
        human.threwMotion = humanThrew;
        robot = new Player(robotXPosition, YPosition, robotImage, 100, "robot");
        robot.idleMotion = robotImage;
        robot.throwingMotion = robotFire;
        robot.threwMotion = robotThrew;

        fence = new Fence(590, 370, fenceImage, "fence"); 
        ground = new Land(0, 660, landImage, "ground");

        arrowHuman = new AngleDirection(humanXPosition+120, YPosition-100, arrowImage, human, "arrowHuman");
        arrowRobot = new AngleDirection(robotXPosition, YPosition-100, arrowImage, robot, "arrowRobot");
        humanBar = new PowerBar(humanXPosition+200, YPosition, arrowImage, "humanBar");
        robotBar = new PowerBar(robotXPosition+400, YPosition, arrowImage, "robotBar");
        projectiles = new ArrayList<>();
        currentState = TurnState.IDLE;
        humanTurn = true;

        arrowHuman.generateAngle(human);
        arrowRobot.generateAngle(robot);

        animationTimer = new Timer(0, this);
        animationTimer.start();
        PlayLoopMusic(music);
    }

    /**
     * Paints the game components on the game canvas.
     * @param g Graphics object used for painting.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform reset = g2d.getTransform();

        bgImage.paintIcon(this, g2d, 0, 0);
        ground.paintComponent(g2d);

        human.paintComponent(g2d);
        human.healthbar(g2d, 60 + 5*(human.maxHealth - human.health));
        humanface.paintIcon(this, g2d, 520, 25);

        robot.paintComponent(g2d);
        robot.healthbar(g2d, 720);
        robotface.paintIcon(this, g2d, 640, 25);
        
        fence.paintComponent(g2d);

        switch (currentState) {
            case IDLE:
                break;
            case AWAITING_ARROW:
                if (humanTurn){
                    arrowHuman.paint(g2d);
                    g2d.setTransform(reset);
                } else {
                    arrowRobot.paint(g2d);
                    g2d.setTransform(reset);
                }
                break;
            case ROTATING_ARROW:
                if (humanTurn){
                    arrowHuman.paint(g2d);
                    g2d.setTransform(reset);
                } else {
                    arrowRobot.paint(g2d);
                    g2d.setTransform(reset);
                    }
                break;
            case CHARGING_POWER:
                if (humanTurn){
                    humanBar.paint(g2d);
                    g2d.setTransform(reset);
                    arrowHuman.paint(g2d);
                    g2d.setTransform(reset);
                } else {
                    robotBar.paint(g2d);
                    g2d.setTransform(reset);
                    arrowRobot.paint(g2d);
                    g2d.setTransform(reset);
                    }
                break;
                case FIRING_PROJECTILE:
                break;
        }

        for (Throwable projectile : projectiles) {
            if (humanTurn && projectile.checkCollision(human)) {
                    PlayMusic(humandmg);    
                    projectile.handlePlayerCollision(human);
                    projectiles.clear();;
             } else if (!humanTurn && projectile.checkCollision(robot)) {
                    PlayMusic(robotdmg);
                    projectile.handlePlayerCollision(robot);
                    projectiles.clear();;
            }
        
            projectile.checkCollision(fence);
            projectile.checkCollision(ground);
            if (projectile.checkCollision(fence) || projectile.checkCollision(ground)) {
                if (human.getHealth() > 0 && robot.getHealth() > 0) {
                    PlayMusic(collision);
                }
            }
            
            projectile.paintComponent(g2d);
        }

        if (robot.getHealth() <= 0) {
            humanWin.paintIcon(this, g2d, 340, 210);
            humanfacewin.paintIcon(this, g2d, 470, 315);
        } else if (human.getHealth() <= 0) {
            robotWin.paintIcon(this, g2d, 340, 210);
            robotfacewin.paintIcon(this, g2d, 470, 315);
        }
    }
    
    
    /**
 * Handles mouse click events on the game canvas.
 * Determines the game state based on the player's actions and triggers appropriate actions.
 *
 * @param e The MouseEvent object representing the mouse click event.
 */
@Override
public void mouseClicked(MouseEvent e) {
    // Check if there are no projectiles in flight
    if (projectiles.isEmpty()) {    
        // Check if both players are still alive
        if (human.getHealth() > 0 && robot.getHealth() > 0) {
            // Switch between different game states based on the current state
            switch (currentState) {
                case IDLE:
                    // Set the player's idle animation
                    human.setIdle();
                    robot.setIdle();
                    // Transition to the AWAITING_ARROW state
                    currentState = TurnState.AWAITING_ARROW;
                    break;
                case AWAITING_ARROW:
                    // Transition to the ROTATING_ARROW state
                    currentState = TurnState.ROTATING_ARROW;
                    break;
                case ROTATING_ARROW:
                    // Play the lock sound
                    PlayMusic(lock);
                    // Transition to the CHARGING_POWER state
                    currentState = TurnState.CHARGING_POWER;
                    break;
                case CHARGING_POWER:
                    // Play the lock sound
                    PlayMusic(lock);
                    // Play the gunshot or laser sound based on the player's turn
                    if (humanTurn) {
                        PlayMusic(gunshot1);
                        PlayMusic(gunshot2);
                    } else {
                        PlayMusic(laser1);
                        PlayMusic(laser2);
                    }
                    // Transition to the FIRING_PROJECTILE state
                    currentState = TurnState.FIRING_PROJECTILE;
                    break;
                case FIRING_PROJECTILE:
                    // Transition to the IDLE state
                    currentState = TurnState.IDLE;
                    break;
            }
        } else {
            // Play the game over sound
            PlayMusic(gameover);
        }
    }
}
    

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (currentState) {
            case IDLE:
            break;
            case AWAITING_ARROW:
            break;
            case ROTATING_ARROW:
                if (humanTurn){
                    human.setThrowingState();
                    arrowHuman.rotateAngle(human);
                } else {
                    robot.setThrowingState();
                    arrowRobot.rotateAngle(robot);
                 }
                break;
            case CHARGING_POWER:
                if (humanTurn){
                    humanBar.bounceBar(); 
                } else { 
                    robotBar.bounceBar();
                }
                break;
            case FIRING_PROJECTILE:
                if (humanTurn) {
                    human.setThrowState();
                    projectiles.add(new Throwable(humanXPosition+60, YPosition, gunshot, humanBar.getThrowStrength(), arrowHuman.getAngle(), "humanprojectile")); 
                    humanBar.reset();
                } else {
                    robot.setThrowState();
                    projectiles.add(new Throwable(robotXPosition-20, YPosition, energyorb, robotBar.getThrowStrength(), arrowRobot.getAngle(), "robotprojectile"));
                    robotBar.reset();
                }
                humanTurn = !humanTurn; // Switch turns
                currentState = TurnState.IDLE;
                break;
        }
        
        for (Throwable projectile : projectiles) {
            if (projectile.endTurn()) {
                projectile.throwProjectile();
                projectiles.clear();
                break;
            }
            projectile.throwProjectile();
        }
        outsideBounds();
        repaint();
    }

    /**
 * This method checks if any projectile has gone out of the game canvas bounds.
 * If a projectile has gone beyond the left or right boundaries, it is removed from the projectiles list.
 */
public void outsideBounds() {
    // Iterate over the projectiles list
    for (int i = 0; i < projectiles.size(); i++) {
        // Get the current projectile
        Throwable projectile = projectiles.get(i);
        
        // Check if the projectile has gone beyond the left or right boundaries
        if ((projectile.getX() + projectile.getWidth() < 0) || (projectile.getX() > 1280)) {
            // Remove the projectile from the list
            projectiles.remove(i);
        }
    }
}

    /**
 * Plays a sound effect from a given file.
 *
 * @param file The path to the sound file.
 * @throws Exception If an error occurs while playing the sound.
 */
public void PlayMusic(String file) {
    try {
        // Create a File object for the sound file
        File musicPath = new File(file);
        
        // Get an AudioInputStream object for the sound file
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
        
        // Get a Clip object for playing the sound
        Clip clip = AudioSystem.getClip();
        
        // Open the sound file and start playing it
        clip.open(audioInput);
        clip.start();
    } catch (Exception e) {
        // Print any error messages to the console
        System.out.println(e);
    }
}

    /**
 * Plays a looping sound effect from a given file.
 *
 * @param file The path to the sound file.
 * @throws Exception If an error occurs while playing the sound.
 */
public void PlayLoopMusic(String file) {
    try {
        // Create a File object for the sound file
        File musicPath = new File(file);
        
        // Get an AudioInputStream object for the sound file
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
        
        // Get a Clip object for playing the sound
        Clip clip = AudioSystem.getClip();
        
        // Open the sound file and start playing it in a loop
        clip.open(audioInput);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    } catch (Exception e) {
        // Print any error messages to the console
        System.out.println(e);
    }
}


    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}

/* public void connectToServer() {
    try {
        socket = new Socket("localhost", 12345);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        playerID = in.readInt();
        System.out.println("You are Player #" + playerID);
        if (playerID == 1) {
            System.out.println("Waiting for Player #2 to connect...");
        }
        rfsRunnable = new ReadFromServer(in);
        wtsRunnable = new WriteToServer(out);
    } catch (IOException ex) {
        System.out.println("IOException from connectToServer");
    }
}

private class ReadFromServer implements Runnable {
    private DataInputStream dataIn;

    public ReadFromServer(DataInputStream in) {
        dataIn = in;
        System.out.println("RFS Runnable Created");
    }

    public void run() {
        try {
            while (true) {
                double enemyX = dataIn.readDouble();
                double enemyY = dataIn.readDouble();
                if (p2 != null) {
                    if (enemyX != p2.getX()) {
                        p2.setColor(Color.BLACK); 
                    } else {
                        if (playerID==1) {
                            p2.setColor(Color.RED);
                        } else {
                            p2.setColor(Color.BLUE);
                        }
                    }
                    p2.setX(enemyX);
                    p2.setY(enemyY);
                    dc.repaint();
                }
            }
        } catch (IOException ex) {
            System.out.println("IOException from RFS run()");
        }
    }
}


private class WriteToServer implements Runnable {
    private DataOutputStream dataOut;

    public WriteToServer(DataOutputStream out) {
        dataOut = out;
        System.out.println("WTS Runnable Created");
    }

    public void run() {
        try {
            while (true) {
                if (p1 != null) {
                    dataOut.writeDouble(p1.getX());
                    dataOut.writeDouble(p1.getY());
                    dataOut.flush();
                }
                
                try {
                    Thread.sleep(25); // Send updates every 25 ms
                } catch (InterruptedException ex) {
                    System.out.println("Interrupted Exception from WTS Run");
                }
            }
        } catch (IOException ex) {
            System.out.println("IOException from WTS Runnable");
        }
    }
} */




