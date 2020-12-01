package nl.mennospijker.app;

import com.fazecast.jSerialComm.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import nl.mennospijker.util.ConsoleColor;

import javax.swing.*;
import java.io.*;

import static java.lang.Thread.sleep;

public class SerialConnection {
    SerialPort[] availableComports;
    SerialPort currentComport;
    boolean usingComport = false;

    public SerialConnection() {
        availableComports = SerialPort.getCommPorts();
    }

    public void startConsoleApplication() {
        searchAvailableComPorts();
        readConsole();
    }

    public JSONArray getAvailableComports(){
        JSONArray fullList = new JSONArray();

        for (int i = 0; i < availableComports.length; i++) {
            SerialPort cp = availableComports[i];
            JSONObject comport = new JSONObject();
            comport.put("id", i);
            comport.put("name", cp.toString());
            comport.put("description", cp.getDescriptivePortName());
            comport.put("System-name", cp.getSystemPortName());

            fullList.add(comport);
        }

        return fullList;
    }

    public void searchAvailableComPorts() {
        System.out.println("Possible connections:");

        for (int i = 0; i < availableComports.length; i++) {
            System.out.println(ConsoleColor.yellowString(
                    "[" + i + "] ")
                    + ConsoleColor.purpleString(availableComports[i].toString())
                    + " => "
                    + ConsoleColor.greenString(availableComports[i].getDescriptivePortName())
            );
        }
    }

    /**
     * Program prints list of available serialPorts.
     * user can input in console what port it needs to use
     */
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
            System.out.println(
                    "You've chose "
                            + ConsoleColor.ANSI_PURPLE + currentComport
                            + " (" + currentComport.getDescriptivePortName()
                            + ")"
                            + ConsoleColor.ANSI_RESET
            );

            createComportConnection(currentComport);
            usingComport = false;
        }
    }

    public void createComportConnection(SerialPort currentComport) {
        if (!currentComport.openPort()) {
            throw new SerialPortInvalidPortException("Port could not be opened");
        }

        System.out.println(ConsoleColor.greenString("Port opened successfully"));

        currentComport.setBaudRate(9600);

        currentComport.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }

                readInputStream();
            }
        });

        currentComport.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                    return;
                }
                System.out.println("LISTENING_EVENT_DATA_RECEIVED");
            }
        });

        writeBytesToComPort("test");
    }

    public void createComportConnectionWithView(int i, JPanel field) {
        currentComport = availableComports[i];

        if (currentComport == null){
            throw new SerialPortInvalidPortException();
        }

        if (!currentComport.openPort()) {
            throw new SerialPortInvalidPortException("Port could not be opened");
        }

        System.out.println(ConsoleColor.greenString("Port opened successfully"));

        currentComport.setBaudRate(9600);

        currentComport.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }

                readInputStreamWithView(field);
            }
        });

        currentComport.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                    return;
                }
                System.out.println("LISTENING_EVENT_DATA_RECEIVED");
            }
        });
    }

    public synchronized void readInputStreamWithView(JPanel field) {
        if (!usingComport) {
            usingComport = true;

            System.out.println(
                    ConsoleColor.redString("[" + java.time.LocalTime.now() + "]")
                            + " Read "
                            + currentComport.bytesAvailable()
                            + " bytes."
            );

            StringBuilder readedValue = new StringBuilder();

            InputStream comportInputStream = currentComport.getInputStream();
            currentComport.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

            System.out.print(ConsoleColor.ANSI_BLUE);

            try {
                final int bytes = currentComport.bytesAvailable();
                for (int i = bytes; i > 0; i--) {
                    char character = (char) comportInputStream.read();

                    if (character != '\n') {
                        readedValue.append(character);
                    }
                }

                JLabel l = new JLabel("[" + java.time.LocalTime.now() + "] " + readedValue.toString());
                field.add(l, "span, grow");

                field.revalidate();
                System.out.println(readedValue.toString());

                comportInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(ConsoleColor.ANSI_RESET + "\n");
            usingComport = false;
        } else {
            Thread t1 = new Thread(() -> {
                try {
                    sleep(200);
                    readInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t1.start();
        }
    }

    public synchronized void readInputStream() {
        if (!usingComport) {
            usingComport = true;

            System.out.println(
                    ConsoleColor.redString("[" + java.time.LocalTime.now() + "]")
                            + " Read "
                            + currentComport.bytesAvailable()
                            + " bytes."
            );

            StringBuilder readedValue = new StringBuilder();

            InputStream comportInputStream = currentComport.getInputStream();
            currentComport.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

            System.out.print(ConsoleColor.ANSI_BLUE);

            try {
                final int bytes = currentComport.bytesAvailable();
                for (int i = bytes; i > 0; i--) {
                    char character = (char) comportInputStream.read();

                    if (character != '\n') {
                        readedValue.append(character);
                    }
                }

                System.out.println(readedValue.toString());

                comportInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(ConsoleColor.ANSI_RESET + "\n");
            usingComport = false;
        } else {
            Thread t1 = new Thread(() -> {
                try {
                    sleep(200);
                    readInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t1.start();
        }
    }

    /**
     * @param input String
     * @return return intValue of the string.
     * <p>
     * if string is not an int, warn user and ask for retry
     */
    public int isInt(String input) {
        try {
            input = input.trim();
            Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println(ConsoleColor.ANSI_RED + "\nSorry, you can not use alphabetic characters for this selection. \nPlease try again." + ConsoleColor.ANSI_RESET);
            searchAvailableComPorts();
            readConsole();
            return -1;
        }
        return Integer.parseInt(input);
    }

    public synchronized void writeBytesToComPort(String data) {
        if (!usingComport) {
            usingComport = true;
            usingComport = currentComport.writeBytes(data.getBytes(), data.length()) <= 0;
            usingComport = false;
        } else {
            Thread t1 = new Thread(() -> {
                try {
                    sleep(200);
                    writeBytesToComPort(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t1.start();
        }
    }
}