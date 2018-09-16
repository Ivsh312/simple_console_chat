/*
 * class sent and messages on the server
*/
package com.transfer.chatjava.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread{

    private PrintWriter writer;
    private Socket socket;
    private NetClient client;

    public WriteThread(Socket socket, NetClient client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        Scanner a = new Scanner(System.in);
        System.out.println("input user name:");
        String nameUser = a.nextLine();
        client.setUserName(nameUser);
        writer.println(nameUser);
        writer.flush();
        String msg;
        while (true) {
            msg = nameUser+": "+a.nextLine();
            writer.println(msg);
            writer.flush();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
