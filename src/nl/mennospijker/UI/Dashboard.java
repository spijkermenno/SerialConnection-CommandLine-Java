package nl.mennospijker.UI;

import net.miginfocom.swing.MigLayout;
import nl.mennospijker.SerialConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {
    SerialConnection sc;
    JPanel panel = this;

    public Dashboard(SerialConnection SCon) {
        super();
        sc = SCon;

        setSize(500, 500);

        setLayout(new MigLayout("gap 0"));

        JLabel l = new JLabel("What port would you like to use?");
        l.setHorizontalAlignment(SwingConstants.CENTER);
        add(l, "span, grow, width 500, wrap, gapy 15");

        JSONArray comportsAvailable = sc.getAvailableComports();

        for (Object o : comportsAvailable) {
            JSONObject port = (JSONObject) o;
            JButton button = new JButton(port.get("name").toString());

            button.addActionListener(e -> {
                System.out.println("Chosen port: " + port.toJSONString());
                panel.removeAll();
                panel.revalidate();
                panel.repaint();

                panel.add(new JLabel("<html><p style=\"width:100vw\">" + port.toJSONString() + "</p></html>"), "span, grow, width 500, wrap, gapy 15");
            });

            button.setBackground(Color.RED);
            this.add(button, "span, grow, width 500, wrap, gapy 15");
        }

    }
}
