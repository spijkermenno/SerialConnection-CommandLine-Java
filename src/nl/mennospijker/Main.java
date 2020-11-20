package nl.mennospijker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.fazecast.jSerialComm.SerialPort;
import nl.mennospijker.util.ConsoleColor;

public class Main {

    public static void main(String[] args) {
        new SerialConnection();
    }
}
