/*
 * the thread class is used to work with client messages
*/
package com.transfer.chatjava.serv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ServerThread extends Thread implements IServerThead{

    private Socket socket;
    private PrintWriter os; // передача
    private BufferedReader is; // прием
    private InetAddress addr; // адрес клиента
    private NetServer nst;

    public ServerThread(Socket s, NetServer nst) throws IOException {
        this.socket = s;
        this.nst = nst;
        this.os = new PrintWriter(s.getOutputStream(), true);
        this.is = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.addr = s.getInetAddress();
    }

    public void run() {

        try {
            String userName = is.readLine();
            sentMsg("you are connected. communicate");
            nst.addUserName(userName);
            String str = "New user connected: " + userName;
            nst.sentAll(str, this);
            while (true) {              
                nst.sentAll(is.readLine(), this);
            }

        } catch (IOException e) {
            // если клиент не отвечает, соединение с ним разрывается
            System.err.println("Disconnect");
        } finally {
            disconnect(); // уничтожение потока
        }
    }
    
    //disconnect client
    public void disconnect() {
        try {

            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
            socket.close();
            System.out.println(addr.getHostName() + " disconnecting");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
    //sent message to client
    public void sentMsg(String msg) {
        os.println(msg);
        os.flush();//To change body of generated methods, choose Tools | Templates.
    }
    
    //sending a message about the client's joining
    public void printUsers() {
        if (nst.hasUsers()) {
            os.println("Connected users: " + nst.getUserNames());
        } else {
            os.println("No other users connected");
        }
    }
}
