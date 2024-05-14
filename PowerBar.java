import javax.swing.*;
import java.awt.*;

public class PowerBar extends GameObject{
    int barStrength;
    boolean isLocked = false;
    int barWidth = 30;
    int x;
    int y;
    boolean willGoUp;
    int bounceSpeed = 1;

    public PowerBar(int x, int y, ImageIcon image) {
        super(x, y, image);
        this.x = x-250; //+ width of image
        this.y = y+100; // + height of image
        reset();
    } 

    public void reset() {
        isLocked = false;
        generateBar();
    }

    public void generateBar() {
        barStrength = (int) (Math.random() * 100);
    }

    public int getThrowStrength() {
        isLocked = true; // Lock the bar when this method is called
        return barStrength;
    }

    public void bounceBar() {
        if (isLocked){
            generateBar();
            return;
        } 
        
        if (barStrength == 0) {
           willGoUp = true;
        } else if (barStrength == 100) {
            willGoUp = false;
        }
        
        if (willGoUp) {
            barStrength += bounceSpeed;
        } else {
            barStrength -= bounceSpeed;
        }
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.blue);
        g2d.fillRect(x, y-barStrength, barWidth, barStrength);
    }
}

    
