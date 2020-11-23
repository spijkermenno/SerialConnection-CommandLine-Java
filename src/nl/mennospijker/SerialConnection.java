package nl.mennospijker;

import com.fazecast.jSerialComm.*;
import nl.mennospijker.util.ConsoleColor;

import java.io.*;

public class SerialConnection {
    SerialPort[] availableComports;
    SerialPort currentComport;

    SerialConnection() {
        searchAvailableComPorts();
        readConsole();
    }

    public void searchAvailableComPorts() {
        System.out.println("Possible connections:");

        availableComports = SerialPort.getCommPorts();
        for (int i = 0; i < availableComports.length; i++) {
            System.out.println(ConsoleColor.yellowString("[" + i + "] ") + ConsoleColor.purpleString(availableComports[i].toString()) + " => " + ConsoleColor.greenString(availableComports[i].getDescriptivePortName()));
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

        if (isInt(input) >= 0) {
            int inputInt = isInt(input);
            currentComport = availableComports[inputInt];
            System.out.println("You've chose " + ConsoleColor.ANSI_PURPLE + currentComport + " (" + currentComport.getDescriptivePortName() + ")" + ConsoleColor.ANSI_RESET);

            if (!currentComport.openPort()) {
                throw new SerialPortInvalidPortException("Port could not be opened");
            }

            System.out.println(ConsoleColor.greenString("Port opened succesfully"));

            currentComport.setBaudRate(9600);

            /**
             * DataListener
             */
            currentComport.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }


                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                        return;
                    byte[] newData = new byte[currentComport.bytesAvailable()];
                    int numRead = currentComport.readBytes(newData, newData.length);
                    System.out.println(ConsoleColor.redString("[" + java.time.LocalTime.now() + "]") + " Read " + numRead + " bytes.");

                    readInputStream(numRead);
                }
            });

            currentComport.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                }


                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_RECEIVED)
                        return;
                    System.out.println("All data read");
                }
            });
        }
    }

    public synchronized void readInputStream(int bytes) {

        InputStream comportInputStream = currentComport.getInputStream();
        currentComport.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        try {
            for (int i = 0; i < bytes; i++){
                System.out.print((char) comportInputStream.read());
            }
            comportInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n");
    }

    public int isInt(String input) {
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