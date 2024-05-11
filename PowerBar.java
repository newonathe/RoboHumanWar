import javax.swing.*;
import java.awt.*;

public class PowerBar extends GameObject{
    int barStrength;
    int barWidth;
    int x;
    int y;
    boolean willGoUp;
    int bounceSpeed = 1;

    public PowerBar(int x, int y, ImageIcon image) {
        super(x, y, image);
        this.x = x-250; //+ width of image
        this.y = y+100; // + height of image
        generateBar();
    } 

    public void generateBar() {
        barStrength = (int) (Math.random() * 100);
        barWidth = 30;
    }

    //can be manipulated in case 100 is too high/low
    public int getThrowStrength(){
        return barStrength;
    }

    public void bounceBar() {
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
        // g2d.rotate(Math.toRadians(45));
        // g2d.dispose();
    }
}

    
