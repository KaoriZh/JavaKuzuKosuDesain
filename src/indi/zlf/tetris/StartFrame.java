package indi.zlf.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The main class of start frame
 */
public class StartFrame extends JFrame {

    /**
     * Generator
     * Set basic attributes of frame
     */
    public StartFrame() {

        setTitle(new String("TETRIS"));
        setSize(new Dimension(Parameter.StartFrame.MINIMUM_FRAME_WIDTH, Parameter.StartFrame.MINIMUM_FRAME_HEIGHT)); // 限制最小尺寸
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StartPanel startPanel = new StartPanel();
        add(startPanel);

        setLocationRelativeTo(null);

    }

}

/**
 * The panel component of start frame
 */
class StartPanel extends JPanel {

    /**
     * Start button
     */
    private static JButton startButton;

    /**
     * Help button
     */
    private static JButton helpButton;

    /**
     * Background image
     */
    private static Image backgroundImage;

    /**
     * Statically set attributes of buttons and
     * load background image.
     */
    static {

        startButton = new JButton("START");
        startButton.setSize(Parameter.StartFrame.BUTTON_WIDTH, Parameter.StartFrame.BUTTON_HEIGHT);
        startButton.setContentAreaFilled(false);
        helpButton = new JButton("HELP");
        helpButton.setSize(Parameter.StartFrame.BUTTON_WIDTH, Parameter.StartFrame.BUTTON_HEIGHT);
        helpButton.setContentAreaFilled(false);
        backgroundImage = Tool.getImage("img/StartBackground.jpg");

    }

    /**
     * Generator
     * Add buttons and listeners.
     */
    StartPanel() {

        add(Box.createVerticalStrut(Parameter.StartFrame.BUTTON_OFFSET));
        add(startButton);
        add(helpButton);
        add(Box.createHorizontalGlue());
        setListener();

    }

    /**
     * Set listeners of buttons.
     */
    private void setListener() {

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame gameFrame = new GameFrame();
                gameFrame.setVisible(true);
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(StartPanel.this,
                        Parameter.StartFrame.TEXT, "HELP",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

    }

    /**
     * Override the paintComponent method to paint background image.
     * @param g paintbrush object
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
