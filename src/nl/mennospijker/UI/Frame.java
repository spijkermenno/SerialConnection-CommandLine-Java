package nl.mennospijker.UI;

import nl.mennospijker.SerialConnection;

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

        // initialize content pane
        Dashboard dashboard = new Dashboard(new SerialConnection());

        setContentPane(dashboard);
    }
}
