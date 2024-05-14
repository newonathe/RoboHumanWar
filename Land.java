import javax.swing.*;
import java.awt.*;

public class Land extends GameObject {
    public Land(int x, int y, ImageIcon image) {
        super(x, y, image);
    }
 
    public void drawGround(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 690, 900, 30);
    }
    
}
