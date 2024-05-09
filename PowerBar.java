import javax.swing.*;
import java.awt.*;

public class PowerBar extends GameObject{
    int barStrength;
    int barWidth;
    int x;
    int y;
    boolean willGoDown;
    boolean willGoUp;

    public PowerBar(int x, int y, ImageIcon image) {
        super(x, y, image);
        this.x = x; //+ width of image
        this.y = y; // + height of image
    } // Close the constructor method here

    public void generateBar() {
        barStrength = (int) (Math.random() * 100);
        barWidth = 30;
        goUp();
        isUpOrDown();
    }

    public void createBar(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(x, y, barWidth, barStrength);
    }

    //can be manipulated in case 100 is too high/low
    public int getThrowStrength(){
        return barStrength;
    }

    public void bounceBar() {
        if (willGoUp) {
            bounceUp();
        } else if (willGoDown) {
            bounceDown();
        }
    }

    private void goUp(){
        willGoUp = true;
        willGoDown = false;
    }

    private void goDown(){
        willGoDown = true;
        willGoUp = false;
    }

    public void bounceUp() {
        if (barStrength < 100 && barStrength > 0) {
            barStrength ++;
        } else {
            barStrength = 100;
        }
    }
    public void bounceDown() {
        if (barStrength > 0 && barStrength < 100) {
            barStrength --;
        } else {
            barStrength = 0;
        }
    }

    public void isUpOrDown() {
        if (barStrength == 100) {
            goDown();
        } else if (barStrength == 0) {
            goUp();
        }
    }
}

    
