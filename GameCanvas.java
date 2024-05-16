import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class GameCanvas extends JComponent implements ActionListener, MouseListener {    
    ImageIcon bgImage, humanface, robotface, humanImage, 
    robotImage, humanFire, robotFire, humanThrew, robotThrew, 
    fenceImage, arrowImage, energyorb, gunshot, landImage;
    
    AngleDirection arrowHuman, arrowRobot; // done
    PowerBar humanBar, robotBar; //done

    Fence fence; // done
    Land ground;
    Player robot; //PLAYER 1
    Player human; //PLAYER 2

    ArrayList<Throwable> projectiles;
    
    Timer animationTimer;
    int humanXPosition, robotXPosition, YPosition;
    boolean humanTurn;
    
    TurnState currentState;
    enum TurnState {
        IDLE, AWAITING_ARROW, ROTATING_ARROW, CHARGING_POWER, FIRING_PROJECTILE;
    }
    
    // network field
    private Socket socket;
    private int playerID, throwStrength;
    private double angle;
    private ReadRotateArrow rra;
    private ReadChargePower rcp;
    private ReadFiringProjectile rfp;
    private WriteRotateArrow wrt;
    private WriteChargePower wcp;
    private WriteFiringProjectile wfp;

    public GameCanvas() {

        bgImage = new ImageIcon("resources/bg.png");
        humanface = new ImageIcon("resources/humanface.png");
        robotface = new ImageIcon("resources/robotface.png");
        humanImage = new ImageIcon("resources/human.gif");
        robotImage = new ImageIcon("resources/robot.gif");
        humanFire = new ImageIcon("resources/humanturn.gif");
        robotFire = new ImageIcon("resources/robotturn.gif");
        humanThrew = new ImageIcon("resources/humanthrew.gif");
        robotThrew = new ImageIcon("resources/robotthrew.gif");
        fenceImage = new ImageIcon("resources/fence.gif");
        arrowImage = new ImageIcon("resources/arrow.png");
        energyorb = new ImageIcon("resources/energyorb.gif");
        gunshot = new ImageIcon("resources/gunshot.gif");
        landImage = new ImageIcon("resources/land.png");

        humanXPosition = 100;
        robotXPosition = 1060;
        YPosition = 450;

        fence = new Fence(590, 370, fenceImage, "fence"); 
        ground = new Land(0, 660, landImage, "ground");

        humanBar = new PowerBar(humanXPosition+200, YPosition, arrowImage, "humanBar");
        robotBar = new PowerBar(robotXPosition+400, YPosition, arrowImage, "robotBar");
        projectiles = new ArrayList<Throwable>();
        currentState = TurnState.IDLE;
        humanTurn = true;

        animationTimer = new Timer(5, this);
        animationTimer.start();
    }
    
    public void createP() {
        if (playerID == 1) {
            System.out.println("Waiting for Player #2 to connect...");
            human = new Player(humanXPosition, YPosition, humanImage, 100, "human");
            human.idleMotion = humanImage;
            human.throwingMotion = humanFire;
            human.threwMotion = humanThrew;
            arrowHuman = new AngleDirection(humanXPosition+120, YPosition-100, arrowImage, human, "arrowHuman");
            arrowHuman.generateAngle(human);
        } else if (playerID == 2) {
            System.out.println("Player #1 has connected!");
            robot = new Player(robotXPosition, YPosition, robotImage, 100, "robot");
            robot.idleMotion = robotImage;
            robot.throwingMotion = robotFire;
            robot.threwMotion = robotThrew;
            arrowRobot = new AngleDirection(robotXPosition, YPosition-100, arrowImage, robot, "arrowRobot");
            arrowRobot.generateAngle(robot);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform reset = g2d.getTransform();

        bgImage.paintIcon(this, g2d, 0, 0);
        ground.paintComponent(g2d);

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

        // human.currentMotion();
        human.paintComponent(g2d);
        human.healthbar(g2d, 60 + 5*(human.maxHealth - human.health));
        humanface.paintIcon(this, g2d, 520, 25);

        // robot.currentMotion();
        robot.paintComponent(g2d);
        robot.healthbar(g2d, 720);
        robotface.paintIcon(this, g2d, 640, 25);
        
        fence.paintComponent(g2d);

        for (Throwable projectile : projectiles) {
            if (humanTurn && projectile.checkCollision(human)) {
                    projectile.handlePlayerCollision(human);
                    projectiles.clear();
             } else if (!humanTurn && projectile.checkCollision(robot)) {
                    projectile.handlePlayerCollision(robot);
                    projectiles.clear();
            }
        
            projectile.checkCollision(fence);
            projectile.checkCollision(ground);
            
            projectile.paintComponent(g2d);
        }

    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if ((projectiles.isEmpty())) {
            switch (currentState) {
                case IDLE:
                    currentState = TurnState.AWAITING_ARROW;
                    break;
                case AWAITING_ARROW:
                    currentState = TurnState.ROTATING_ARROW;
                    break;
                case ROTATING_ARROW:
                    currentState = TurnState.CHARGING_POWER;
                    break;
                case CHARGING_POWER:
                    currentState = TurnState.FIRING_PROJECTILE;
                    break;
                case FIRING_PROJECTILE:
                    currentState = TurnState.IDLE;
                    break;
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
                    projectiles.add(new Throwable(humanXPosition, YPosition, gunshot, throwStrength, angle, "humanprojectile")); 
                    humanBar.reset();
                } else {
                    robot.setThrowState();
                    projectiles.add(new Throwable(robotXPosition, YPosition, energyorb, throwStrength, angle, "robotprojectile"));
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
                human.setIdle();
                robot.setIdle();
                break;
            }
            projectile.throwProjectile();
        }
        outsideBounds();
        repaint();
    }

    public void outsideBounds() {
        for (int i = 0; i < projectiles.size(); i++) {
            if ((projectiles.get(i).getX() + projectiles.get(i).getWidth() < 0) || (projectiles.get(i).getX()>1280)) {
                projectiles.remove(i);
            }
        }
    }

    // network servers

    public void connectToServer() {
        try {
            socket = new Socket("localhost", 45371);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("You are player #" + playerID);
            
            rra = new ReadRotateArrow(playerID, in);
            rcp = new ReadChargePower(playerID, in);
            rfp = new ReadFiringProjectile(playerID, in);
            wrt = new WriteRotateArrow(playerID, out);
            wcp = new WriteChargePower(playerID, out);
            wfp = new WriteFiringProjectile(playerID, out);

            Thread rraThread = new Thread(rra);
            Thread rcpThread = new Thread(rcp);
            Thread rfpThread = new Thread(rfp);
            rraThread.start();
            rcpThread.start();
            rfpThread.start();
            
            Thread wrtThread = new Thread(wrt);
            Thread wcpThread = new Thread(wcp);
            Thread wfpThread = new Thread(wfp);
            wrtThread.start();
            wcpThread.start();
            wfpThread.start();
            System.out.println("Threads started");

        } catch (IOException e) {
            System.out.println("error from client cTS");
        }
    }

    private class ReadRotateArrow implements Runnable {
        private int playerID;
        private ObjectInputStream objectIn;

        private ReadRotateArrow(int id, ObjectInputStream in) {
            playerID = id;
            objectIn = in;
            System.out.println("RRA Runnable created");
        }

        public void run() {
            try {
                while (true) {
                    if (playerID == 1) {
                        humanTurn = objectIn.readBoolean();
                        human = (Player) objectIn.readObject();
                    } else if (playerID == 2) {
                        humanTurn = objectIn.readBoolean();
                        robot = (Player) objectIn.readObject();
                    }
                    repaint();
                }
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("error from client RRA");
            }
        }

    }

    private class ReadChargePower implements Runnable {
        private int playerID;
        private ObjectInputStream objectIn;
        
        private ReadChargePower(int id, ObjectInputStream in) {
            playerID = id;
            objectIn = in;
            System.out.println("RCP Runnable created");
        }
        public void run() {
            try {
                while (true) {
                    if (playerID == 1) {
                        humanTurn = objectIn.readBoolean();
                    } else if (playerID == 2) {
                        humanTurn = objectIn.readBoolean();
                    }
                    repaint();
                }
            } catch (IOException ex) {
                System.out.println("error from client RCP");
            }
        }
    }

    private class ReadFiringProjectile implements Runnable {
        private int playerID;
        private ObjectInputStream objectIn;

        private ReadFiringProjectile(int id, ObjectInputStream in) {
            playerID = id;
            objectIn = in;
            System.out.println("RFP Runnable created");
        }

        public void run() {
            try {
                while (true) {
                    if (playerID == 1) {
                        humanTurn = objectIn.readBoolean();
                        humanXPosition = objectIn.readInt();
                        YPosition = objectIn.readInt();
                        gunshot = (ImageIcon) objectIn.readObject();
                        throwStrength = (objectIn.readInt());
                        angle = (objectIn.readDouble());
                    } else if (playerID == 2) {
                        humanTurn = objectIn.readBoolean();
                        robotXPosition = objectIn.readInt();
                        YPosition = objectIn.readInt();
                        energyorb = (ImageIcon) objectIn.readObject();
                        throwStrength = (objectIn.readInt());
                        angle = (objectIn.readDouble());
                    }
                    repaint();
                }
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("error from client RFP");
            }
        }

    }


    private class WriteRotateArrow implements Runnable {
        private ObjectOutputStream objectOut;
        private int playerID;

        private WriteRotateArrow(int id, ObjectOutputStream out) {
            playerID = id;
            objectOut = out;
            System.out.println("WRA Runnable created");
        }

        public void run() {
            try {
                if (playerID == 1) {
                    objectOut.writeBoolean(humanTurn);
                    objectOut.writeObject(human);
                } else if (playerID == 2) {
                    objectOut.writeBoolean(!humanTurn);
                    objectOut.writeObject(robot);
                }
                objectOut.flush();
                } catch (IOException ex) {
                System.out.println("error from client RFS");
            }
        }
    }

    private class WriteChargePower implements Runnable {
        private ObjectOutputStream objectOut;
        private int playerID;

        private WriteChargePower(int id, ObjectOutputStream out) {
            playerID = id;
            objectOut = out;
            System.out.println("WCP Runnable created");
        }

        public void run() {
            try {
                if (playerID == 1) {
                    objectOut.writeBoolean(humanTurn);
                } else if (playerID == 2) {
                    objectOut.writeBoolean(!humanTurn);
                }
                objectOut.flush();
                } catch (IOException ex) {
                System.out.println("error from client RFS");
            }
        }
    }

    private class WriteFiringProjectile implements Runnable {
        private ObjectOutputStream objectOut;
        private int playerID;

        private WriteFiringProjectile(int id, ObjectOutputStream out) {
            playerID = id;
            objectOut = out;
            System.out.println("WFP Runnable created");
        }

        public void run() {
            try {
                if (playerID == 1) {
                    objectOut.writeBoolean(humanTurn);
                    objectOut.writeInt(humanXPosition+60);
                    objectOut.writeInt(YPosition);
                    objectOut.writeObject(gunshot);
                    objectOut.writeInt(humanBar.getThrowStrength());
                    objectOut.writeDouble(arrowHuman.getAngle());
                    objectOut.writeUTF("humanprojectile");

                } else if (playerID == 2) {
                    objectOut.writeBoolean(!humanTurn);
                    objectOut.writeInt(robotXPosition-20);
                    objectOut.writeInt(YPosition);
                    objectOut.writeObject(energyorb);
                    objectOut.writeInt(robotBar.getThrowStrength());
                    objectOut.writeDouble(arrowRobot.getAngle());
                    objectOut.writeUTF("robotprojectile");
                }
                objectOut.flush();
                } catch (IOException ex) {
                System.out.println("error from client RFS");
            }
        }
    }



















    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }
}



