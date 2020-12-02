package nl.mennospijker.app.UI;

import com.fazecast.jSerialComm.SerialPort;
import net.miginfocom.swing.MigLayout;
import nl.mennospijker.app.SerialConnection;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class ConsoleView extends JPanel {
    public ConsoleView(JPanel ctx, SerialConnection serialConnection, int comportLocation) {
        // ctx = main body

        JPanel scrollpanel = new JPanel(new MigLayout(""));
        JScrollPane field = new JScrollPane(scrollpanel);

        serialConnection.createComportConnectionWithView(comportLocation, scrollpanel, field);

        ctx.add(field, "span, grow, height 80%");

        JPanel control = new JPanel(new MigLayout("fill"));

        JTextPane message = new JTextPane();
        message.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JButton send = new JButton("Send");

        send.addActionListener(e1 -> {
            if (!message.getText().trim().equals("")) {
                serialConnection.writeBytesToComPort(message.getText());
                message.setText("");
            }
        });

        control.add(message, "grow, width 70%");
        control.add(send, "grow");

        ctx.add(control, "span, grow, height 10%");
    }
}
