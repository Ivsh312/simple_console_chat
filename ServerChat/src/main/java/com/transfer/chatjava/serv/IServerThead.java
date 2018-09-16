package com.transfer.chatjava.serv;


public interface IServerThead {
    //disconnect client
    public void disconnect();
    //sent Message client
    void sentMsg(String msg);
    
    void printUsers();
}
