package nl.mennospijker;

import com.fazecast.jSerialComm.SerialPort;
import nl.mennospijker.util.ConsoleColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SerialConnection {
    SerialPort[] comports;

    SerialConnection() {
        searchAvailableComPorts();
        readConsole();
    }

    public void searchAvailableComPorts() {
        System.out.println("Possible connections:");

        comports = SerialPort.getCommPorts();
        for (int i = 0; i < comports.length; i++) {
            System.out.println(ConsoleColor.ANSI_YELLOW + "[" + i + "] " + ConsoleColor.ANSI_RESET + comports[i]);
        }
    }

    public void readConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\nPlease enter comport number between " + ConsoleColor.ANSI_YELLOW + "[x]: " + ConsoleColor.ANSI_RESET);
        String input = null;
        try {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (checkContent(input) >= 0) {
            int inputInt = checkContent(input);
            SerialPort chosenPort = comports[inputInt];
            System.out.println("You've chose " + ConsoleColor.ANSI_PURPLE + chosenPort + ConsoleColor.ANSI_RESET);

            chosenPort.openPort();
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

            InputStream comportInputStream = chosenPort.getInputStream();

            try {
                int data = comportInputStream.read();
                while (data != -1) {
                    System.out.print((char) data);

                    data = comportInputStream.read();
                }
                comportInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int checkContent(String input) {
        try {
            Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println(ConsoleColor.ANSI_RED + "\nSorry, you can not use alphabetic characters for this selection. \nPlease try again." + ConsoleColor.ANSI_RESET);
            searchAvailableComPorts();
            readConsole();
            return -1;
        }
        return Integer.parseInt(input);
    }
}
