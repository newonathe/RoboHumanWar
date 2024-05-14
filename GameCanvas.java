import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class GameCanvas extends JComponent implements ActionListener, MouseListener {
    AngleDirection arrowCat, arrowDog; // done
    ArrayList<Throwable> projectiles;
    BoneProjectile bone;
    CanProjectile can;
    Fence fence; // done
    ImageIcon catImage, dogImage, fenceImage, arrowImage;
    Land ground;
    Player dog, cat; // done
    PowerBar catBar, dogBar; //done
    
    Timer animationTimer;
    
    int catX, dogX, y;

    enum TurnState {
        IDLE, AWAITING_ARROW, ROTATING_ARROW, CHARGING_POWER, FIRING_PROJECTILE;
    }

    TurnState currentState;
    
    public GameCanvas() {
        catX = 100;
        dogX = 1060;
        y = 500;

        catImage = new ImageIcon("resources/cat.png"); // make an arraylist for images later
        dogImage = new ImageIcon("resources/dog.png");
        fenceImage = new ImageIcon("resources/fence.png");
        arrowImage = new ImageIcon("resources/arrow.png");
        
        cat = new Player(catX, y, catImage, 100);
        dog = new Player(dogX, y, dogImage, 100);
        fence = new Fence(620, 400, fenceImage); 

        arrowCat = new AngleDirection(catX+150, y-100, arrowImage, cat);
        arrowDog = new AngleDirection(dogX-30, y-100, arrowImage, dog);
        catBar = new PowerBar(catX+200, y, arrowImage);
        dogBar = new PowerBar(dogX+400, y, arrowImage);

        projectiles = new ArrayList<>();

        currentState = TurnState.IDLE;
        arrowCat.setVisible(false);
        arrowDog.setVisible(false); 

        animationTimer = new Timer(5, this);
        animationTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform reset = g2d.getTransform();
        
        switch (currentState) {
            case IDLE:
                break;
            case AWAITING_ARROW:
                // if (catTurn){
                    arrowCat.paint(g2d);
                    g2d.setTransform(reset);
                // } else {
                    arrowDog.paint(g2d);
                    g2d.setTransform(reset);
                // }
                break;
            case ROTATING_ARROW:
                // if (catTurn){
                    arrowCat.paint(g2d);
                    g2d.setTransform(reset);
                // } else {
                    arrowDog.paint(g2d);
                    g2d.setTransform(reset);
                    // }
                break;
            case CHARGING_POWER:
                // if (catTurn){
                    catBar.paint(g2d);
                    g2d.setTransform(reset);
                // } else {
                    dogBar.paint(g2d);
                    g2d.setTransform(reset);
                    // }

                // if (catTurn){
                    arrowCat.paint(g2d);
                    g2d.setTransform(reset);
                // } else {
                    arrowDog.paint(g2d);
                    g2d.setTransform(reset);
                    // }
                break;
        }

        for (Throwable projectile : projectiles) {
            projectile.paintComponent(g2d);
        }

        cat.paintComponent(g2d);
        cat.healthbar(g2d, 60 + 5*(cat.maxHealth - cat.health));
        dog.paintComponent(g2d);
        dog.healthbar(g2d, 720);
        fence.paintComponent(g2d);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (currentState) {
            case IDLE:
                currentState = TurnState.AWAITING_ARROW;
                break;
            case AWAITING_ARROW:
                // if (catTurn){
                    arrowCat.setVisible(true); 
                // } else {
                     arrowDog.setVisible(true);
                // }
                currentState = TurnState.ROTATING_ARROW;
                break;
            case ROTATING_ARROW:
                currentState = TurnState.CHARGING_POWER;
                break;
            case CHARGING_POWER:
                currentState = TurnState.FIRING_PROJECTILE;
                break;
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (currentState) {
            case IDLE:
                break;
            case ROTATING_ARROW:
                // if (catTurn){
                     arrowCat.rotateAngle(cat);
                //  } else {
                     arrowDog.rotateAngle(dog);
                //  }
                break;
            case CHARGING_POWER:
                // if (catTurn){
                    catBar.bounceBar(); 
                // } else { 
                    dogBar.bounceBar();
                // }
                break;
            case FIRING_PROJECTILE:
                projectiles.add(new CanProjectile(catX, y, catImage, catBar.getThrowStrength(), arrowCat.getAngle()));
                projectiles.add(new BoneProjectile(dogX, y, dogImage, catBar.getThrowStrength(), arrowDog.getAngle()));
            
                catBar.reset();
                dogBar.reset();

                arrowCat.setVisible(false);
                arrowDog.setVisible(false);

                currentState = TurnState.IDLE;
                // catTurn = !catTurn; // Switch turns
                break;
        }
        
        for (Throwable projectile : projectiles) {
            projectile.update();
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }
}



