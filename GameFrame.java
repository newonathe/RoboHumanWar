import javax.swing.*;
import java.awt.*;
public class GameFrame {
    private JFrame frame;
    private GameCanvas canvas;

        int w = 1280;
        int h = 720;

    public GameFrame() {
        frame = new JFrame("Cats vs Dogs");
        canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(w,h));
    }

    public void setUpGui () {
        Container contentPane = frame.getContentPane();
        contentPane.add(canvas);
        frame.pack();
        frame.addMouseListener(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setVisible(true);
    }
}
