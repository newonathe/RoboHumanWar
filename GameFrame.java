import javax.swing.*;
import java.awt.*;
public class GameFrame {
    private JFrame frame;
    private GameCanvas canvas;

        //1535x795
        int w = 1280;
        int h = 720;

    public GameFrame() {
        frame = new JFrame("Cats vs Dogs");
        canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(w,h));
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setUpGui () {
        Container contentPane = frame.getContentPane();
        contentPane.add(canvas);
        frame.pack();
        frame.addMouseListener(canvas);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setFocusable(true);
    }
}
