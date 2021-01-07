package nl.mennospijker.app.UI;

import net.miginfocom.swing.MigLayout;
import nl.mennospijker.app.SerialConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;

public class ModeSelectionPanel extends JPanel {
    JPanel panel = this;

    public ModeSelectionPanel(JFrame frame) {
        super();

        setSize(480, 480);

        setLayout(new MigLayout("fill, novisualpadding, gap 10"));

        JLabel l = new JLabel("What do you want to do?");
        l.setHorizontalAlignment(SwingConstants.CENTER);
        add(l, "span, grow, height 10%");



        JButton serialButton = new JButton("Read serial port");

        serialButton.addActionListener(e -> {
            System.out.println("Serial port reading");
            panel.removeAll();
            panel.revalidate();
            panel.repaint();

            frame.setContentPane(new SerialPortSelectionPanel(new SerialConnection()));
        });

        this.add(serialButton, "span, grow");

        JButton SDReadButton = new JButton("Read SD card");

        SDReadButton.addActionListener(e -> {
            System.out.println("SD Card reading");
            panel.removeAll();
            panel.revalidate();
            panel.repaint();

            frame.setContentPane(new SelectSDCardPanel(frame));
        });

        this.add(SDReadButton, "span, grow");

        add(new JLabel(), "span, grow");

    }
}
