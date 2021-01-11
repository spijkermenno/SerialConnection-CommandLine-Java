package nl.mennospijker.app.UI;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame() {
        super("Drone Control Panel");

        // initalize frame and content
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        setSize(500, 500);

        // The dimension type contains the layout of the users screen.
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screen.width / 2 - this.getSize().width / 2, screen.height / 2 - this.getSize().height / 2);

        // Initizing selection panel with parameter type JFrame
        ModeSelectionPanel modeSelectionPanel = new ModeSelectionPanel(this);
        setContentPane(modeSelectionPanel);
    }
}
