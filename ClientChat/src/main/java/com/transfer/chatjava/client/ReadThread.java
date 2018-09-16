package com.transfer.chatjava.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadThread extends Thread {

    private BufferedReader reader;
    private Socket socket;
    private NetClient client;

    public ReadThread(Socket socket, NetClient client) {
        this.client = client;
        this.socket = socket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        String response;
        while (true) {
            try {
                response = reader.readLine();
                // prints the username after displaying the server's message
                if (client.getUserName() != null) {
                    System.out.println(response);
                }
                Thread.sleep(10);
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            } catch (InterruptedException ex) {
                Logger.getLogger(ReadThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
