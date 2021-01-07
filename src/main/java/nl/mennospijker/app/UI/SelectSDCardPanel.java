package nl.mennospijker.app.UI;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class SelectSDCardPanel extends JPanel {
    private File file;
    JPanel panel = this;

    public SelectSDCardPanel(JFrame frame) {
        super();

        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(screen.width / 2 - this.getSize().width / 2, screen.height / 2 - this.getSize().height / 2);
        String fileLocation = dialog.getDirectory() + dialog.getFile();

        if (!fileLocation.equals("nullnull")) {
            file = new File(fileLocation);
            System.out.println(file);
        } else {
            System.exit(-1);
        }

        try {
            StringBuilder text = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            //read line by line
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }

            System.out.println(text.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
