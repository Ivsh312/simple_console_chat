/*
 * client part of the chat
*/
package com.transfer.chatjava.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetClient implements INetClient{
    private String userName;
    private Socket socket;
    public NetClient() throws UnknownHostException, IOException{
        this.socket = new Socket(InetAddress.getLocalHost(), 8071);
    }
    
    //sterts message
    public static void main(String[] args) {
        try {
            NetClient client = new NetClient();
            client.play();
        } catch (UnknownHostException ex) { 
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(NetClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //starts messages flows from the server and sends messages to the server
    private void play() {
         System.out.println("Connected to the chat server");
          new ReadThread(socket, this).start();
          new WriteThread(socket, this).start();
 
        //To change body of generated methods, choose Tools | Templates.
    }
    //set UserName
    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }
    //get UserName
    public String getUserName() {
        return this.userName;
    }
}

