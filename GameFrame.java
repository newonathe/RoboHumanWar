import javax.swing.*;
import java.awt.*;
public class GameFrame {
    private JFrame frame;
    private GameCanvas canvas;

        //1535x795
        int w = 1920;
        int h = 1080;

    public GameFrame() {
        frame = new JFrame("Cats vs Dogs");
        canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(w,h));
    }

    public void setUpGui () {
        Container contentPane = frame.getContentPane();
        contentPane.add(canvas);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
