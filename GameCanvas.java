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
    boolean lockAngle;

    enum TurnState {
        IDLE, AWAITING_ARROW, ROTATING_ARROW, CHARGING_POWER, FIRING_PROJECTILE;
    }

    TurnState currentState = TurnState.IDLE;
    
    public GameCanvas() {
        lockAngle = false;
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

        animationTimer = new Timer(5, this);
        animationTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform reset = g2d.getTransform();
        
        arrowCat.paint(g2d);
        g2d.setTransform(reset);

        arrowDog.paint(g2d);
        g2d.setTransform(reset);

        catBar.paint(g2d);
        dogBar.paint(g2d);

        cat.paintComponent(g2d);
        cat.healthbar(g2d, 60 + 5*(cat.maxHealth - cat.health));
        dog.paintComponent(g2d);
        dog.healthbar(g2d, 720);
        fence.paintComponent(g2d);

        for (Throwable projectile : projectiles) {
            projectile.paintComponent(g2d);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (currentState) {
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
    
    // @Override
    // public void mouseClicked(MouseEvent e) {
    //     // if (cat.turnTracker() && !lockAngle) {
    //         // lockAngle = true; // Lock the angle once clicked
    //         // int throwStrength = bar.getThrowStrength();
    //         // double throwAngle = arrowCat.getAngle();
    //         // projectiles.add(new CanProjectile(catX, y, catImage, throwStrength, throwAngle));
    //         // cat.setTurn(false);
    //         // dog.setTurn(true);
    //         // bar.generateBar();
    //     // } else if (dog.turnTracker() && !lockAngle) {
    //     //     lockAngle = true;
    //     //     int throwStrength = bar.getThrowStrength();
    //     //     double throwAngle = arrowDog.getAngle();
    //     //     projectiles.add(new BoneProjectile(dogX, y, dogImage, throwStrength, throwAngle));
    //     //     dog.setTurn(false);
    //     //     cat.setTurn(true);
    //     //     bar.generateBar();
    //     // }
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (currentState) {
            case IDLE:
                arrowCat.setVisible(false);
                arrowDog.setVisible(false);
                currentState = TurnState.AWAITING_ARROW;
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
                int throwStrengthC = catBar.getThrowStrength()/4;
                int throwStrengthD = dogBar.getThrowStrength()/4;
                double throwAngleC = -arrowCat.getAngle();
                double throwAngleD = -arrowDog.getAngle();
                
                projectiles.add(new CanProjectile(catX, y, catImage, throwStrengthC, throwAngleC));
                projectiles.add(new BoneProjectile(dogX, y, dogImage, throwStrengthD, throwAngleD));
            
                catBar.reset();
                dogBar.reset();

                currentState = TurnState.IDLE;
                // catTurn = !catTurn; // Switch turns
                break;
        }
        
        for (Throwable projectile : projectiles) {
            projectile.update();
        }

        repaint();
    

        
        // // TODO Auto-generated method stub
        // catBar.bounceBar();
        // dogBar.bounceBar();
        
        // // int count = 0;
        // // while (!lockAngle && count <= 3) {
        //     arrowCat.rotateAngle(cat);
        //     arrowDog.rotateAngle(dog);

            // int throwStrengthC = catBar.getThrowStrength();
            // int throwStrengthD = dogBar.getThrowStrength();
            // double throwAngleC = -arrowCat.getAngle();
            // double throwAngleD = -arrowDog.getAngle();
            // projectiles.add(new CanProjectile(catX, y, catImage, throwStrengthC, throwAngleC));
            // projectiles.add(new BoneProjectile(dogX, y, dogImage, throwStrengthD, throwAngleD));

            // for (Throwable projectile : projectiles) {
            //     projectile.update();
            // }

            // repaint();
        //     count++;
        // }
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



