import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class GameCanvas extends JComponent {
    ArrowAngle axis;
    BoneProjectile bone;
    CanProjectile can;
    Fence fence;
    ImageIcon catImage, dogImage, fenceImage;
    LandObstacle groundObstacle;
    Player dog, cat;
    Timer animationTimer;
    
    public GameCanvas() {
        catImage = new ImageIcon("resources/cat.png");
        dogImage = new ImageIcon("resources/dog.png");
        fenceImage = new ImageIcon("resources/fence.png");
        cat = new Player(150, 550, catImage, 100);
        dog = new Player(1265, 550, dogImage, 100);
        fence = new Fence(740, 445, fenceImage);
    }

    @Override
    public void paintComponent(Graphics g) {
        cat.paintComponent(g);
        cat.healthbar(g, 135 + 5*(cat.maxHealth - cat.health));
        dog.paintComponent(g);
        dog.healthbar(g, 900);
        fence.paintComponent(g);
    }

}

