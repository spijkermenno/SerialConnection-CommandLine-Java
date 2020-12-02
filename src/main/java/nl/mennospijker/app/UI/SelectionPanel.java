package nl.mennospijker.app.UI;

import net.miginfocom.swing.MigLayout;
import nl.mennospijker.app.SerialConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;

public class SelectionPanel extends JPanel {
    SerialConnection sc;
    JPanel panel = this;

    public SelectionPanel(SerialConnection SCon) {
        super();
        sc = SCon;

        setSize(480, 480);

        setLayout(new MigLayout("fill, novisualpadding, gap 10"));

        JLabel l = new JLabel("What port would you like to use?");
        l.setHorizontalAlignment(SwingConstants.CENTER);
        add(l, "span, grow, height 10%");

        JSONArray comportsAvailable = sc.getAvailableComports();

        for (Object o : comportsAvailable) {
            JSONObject port = (JSONObject) o;
            JButton button = new JButton(port.get("name").toString() + " - " + port.get("description").toString());

            button.addActionListener(e -> {
                System.out.println("Chosen port: " + port.toJSONString());
                panel.removeAll();
                panel.revalidate();
                panel.repaint();

                new ConsoleView(this, sc, Integer.parseInt(port.get("id").toString()));
            });


            this.add(button, "span, grow");
        }

        add(new JLabel(), "span, grow");

    }
}
