import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class GameCanvas extends JComponent implements ActionListener, MouseListener {
    AngleDirection arrowCat, arrowDog;
    BoneProjectile bone;
    CanProjectile can;
    Fence fence;
    ImageIcon catImage, dogImage, fenceImage, arrowImage;
    Land ground;
    Player dog, cat;
    Timer animationTimer;
    int catX, dogX, y;
    boolean lockAngle;
    
    public GameCanvas() {
        lockAngle = false;
        catImage = new ImageIcon("resources/cat.png");
        dogImage = new ImageIcon("resources/dog.png");
        fenceImage = new ImageIcon("resources/fence.png");
        arrowImage = new ImageIcon("resources/arrow.png");

        catX = 100;
        dogX = 1060;
        y = 500;

        arrowCat = new AngleDirection(catX+150, y-100, arrowImage);
        arrowDog = new AngleDirection(dogX-30, y-100, arrowImage);

        cat = new Player(catX, y, catImage, 100);
        dog = new Player(dogX, y, dogImage, 100);
        fence = new Fence(620, 400, fenceImage); 

        arrowCat.generateAngle(cat);
        arrowDog.generateAngle(dog);

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
        cat.paintComponent(g2d);
        cat.healthbar(g2d, 60 + 5*(cat.maxHealth - cat.health));
        dog.paintComponent(g2d);
        dog.healthbar(g2d, 720);
        fence.paintComponent(g2d);
    };

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        // animateArrow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        int count = 0;
        while (!lockAngle && count <= 3) {
            arrowCat.rotateAngle(cat);
            arrowDog.rotateAngle(dog);
            repaint();
            count++;
        }
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



