/*
 * class waiting for clients to connect and allocating a separate thread to them
*/
package com.transfer.chatjava.serv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class NetServer implements INetServer {

    private Set<String> userNames = new HashSet<>();
    private Set<ServerThread> userThreads = new HashSet<>();

    public static void main(String[] args) {
        NetServer serv = new NetServer();
        serv.start();

    }
    
   //waiting for clients to connect and allocating a separate thread to them
    private void start() {
        try {
            ServerSocket server = new ServerSocket(8071);
            System.out.println("initialized");
            while (true) {
                // ожидание клиента
                Socket socket = server.accept();
                System.out.println("New user connected");
                /*
                 создание отдельного потока для обмена данными *
                  с соединившимся клиентом *
                 */
                ServerThread thread = new ServerThread(socket, this);
                // запуск потока
                userThreads.add(thread);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    //sent message to all users
    public void sentAll(String msg, ServerThread thisThread) {
        for (ServerThread thread : userThreads) {
            if (thread != thisThread) {
                thread.sentMsg(msg);
            }
        }

    }
    
    //add new user
    public void addUserName(String userName) {
        userNames.add(userName);
    }
    
    //remove user
    public void removeUser(String userName, ServerThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }
    
    //get set of all users
    public Set<String> getUserNames() {
        return this.userNames;
    }
    //check of avelible
    public boolean check(String userName) {
        return this.userNames.contains(userName);
    }

    /**
     * Returns true if there are other users connected (not count the currently
     * connected user)
     */
    public boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}
