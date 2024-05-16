import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class GameCanvas extends JComponent implements ActionListener, MouseListener {
    ImageIcon bgImage, humanface, robotface, humanImage, 
    robotImage, humanFire, robotFire, humanThrew, robotThrew, 
    fenceImage, arrowImage, energyorb, gunshot, landImage;
    
    AngleDirection arrowHuman, arrowRobot; // done
    PowerBar humanBar, robotBar; //done

    Fence fence; // done
    Land ground;
    Player human, robot; // done

    ArrayList<Throwable> projectiles;
    
    Timer animationTimer;
    int humanXPosition, robotXPosition, YPosition;
    boolean humanTurn;
    
    TurnState currentState;
    enum TurnState {
        IDLE, AWAITING_ARROW, ROTATING_ARROW, CHARGING_POWER, FIRING_PROJECTILE;
    }
    
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
        projectiles = new ArrayList<Throwable>();
        currentState = TurnState.IDLE;
        humanTurn = false;

        arrowHuman.generateAngle(human);
        arrowRobot.generateAngle(robot);

        animationTimer = new Timer(5, this);
        animationTimer.start();
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



