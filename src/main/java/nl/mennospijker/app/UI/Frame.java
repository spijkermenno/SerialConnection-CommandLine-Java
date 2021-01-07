package nl.mennospijker.app.UI;

import nl.mennospijker.app.SerialConnection;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame() {
        super("Drone Controller");

        // initalize frame and content
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        setSize(500, 500);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screen.width / 2 - this.getSize().width / 2, screen.height / 2 - this.getSize().height / 2);

        ModeSelectionPanel modeSelectionPanel = new ModeSelectionPanel(this);
        setContentPane(modeSelectionPanel);

        // initialize content pane
        //SerialPortSelectionPanel serialPortSelectionPanel = new SerialPortSelectionPanel(new SerialConnection());

        //setContentPane(serialPortSelectionPanel);
    }
}
