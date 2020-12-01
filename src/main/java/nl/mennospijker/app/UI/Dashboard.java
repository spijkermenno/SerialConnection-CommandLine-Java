package nl.mennospijker.app.UI;

import net.miginfocom.swing.MigLayout;
import nl.mennospijker.app.SerialConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JPanel {
    SerialConnection sc;
    JPanel panel = this;

    public Dashboard(SerialConnection SCon) {
        super();
        sc = SCon;

        setSize(480, 480);

        setLayout(new MigLayout("fill, novisualpadding, gap 10"));

        JLabel l = new JLabel("What port would you like to use?");
        l.setHorizontalAlignment(SwingConstants.CENTER);
        add(l, "span, grow, width 500, wrap, gapy 15");

        JSONArray comportsAvailable = sc.getAvailableComports();

        for (Object o : comportsAvailable) {
            JSONObject port = (JSONObject) o;
            JButton button = new JButton(port.get("name").toString() + " - " + port.get("description").toString());

            button.addActionListener(e -> {
                System.out.println("Chosen port: " + port.toJSONString());
                panel.removeAll();
                panel.revalidate();
                panel.repaint();

                JPanel scrollpanel = new JPanel(new MigLayout(""));
                JScrollPane field = new JScrollPane(scrollpanel);

                sc.createComportConnectionWithView(Integer.parseInt(port.get("id").toString()), scrollpanel);

                this.add(field, "span, grow, height 80%");

                JPanel control = new JPanel(new MigLayout("fill"));

                JTextPane message = new JTextPane();
                control.add(message, "grow, width 70%");

                JButton send = new JButton("Send");

                send.addActionListener(e1 -> {
                    if (!message.getText().trim().equals("")) {
                        sc.writeBytesToComPort(message.getText());
                        message.setText("");
                    }
                });

                control.add(send, "grow");

                this.add(control, "span, grow, height 10%");
            });

            this.add(button, "span, grow");
        }

        add(new JLabel(), "span, grow");

    }
}
