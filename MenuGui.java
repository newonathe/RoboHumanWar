import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuGui extends JFrame implements ActionListener {
    
    ImageIcon background,playButton, exitButton, title;

    JRadioButton multiCon, singleCon;
    JButton play, exit;

    final JPanel panel;


    public MenuGui() {
        panel = new JPanel(new FlowLayout());
        background = new ImageIcon("");
        playButton = new ImageIcon("");
        exitButton = new ImageIcon("");
        title = new ImageIcon("");

        play = new JButton(playButton);
        exit = new JButton(exitButton);
        multiCon = new JRadioButton("Multi-Console");
        singleCon = new JRadioButton("Single");

        play.addActionListener(this);
        exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
            GameFrame frame = new GameFrame();
            frame.setUpGui();
        } else if (e.getSource() == exit) {
            System.exit(0);
        }
    }

    public void setUpGui() {
        this.setContentPane(panel);
        panel.add(play);
        panel.add(exit);
        panel.add(multiCon);
        panel.add(singleCon);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    public static void main(String[] args) {
        MenuGui frame = new MenuGui();
        frame.setUpGui();
    }

}
