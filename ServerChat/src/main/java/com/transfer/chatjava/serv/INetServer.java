package com.transfer.chatjava.serv;

import java.util.Set;

public interface INetServer {
    //sent missages from all users
    public void sentAll(String msg, ServerThread thisThread);
    //add new userName
    public void addUserName(String userName);
    //remove user
    public void removeUser(String userName, ServerThread aUser);
    //get set of UserNames
    public Set<String> getUserNames() ;
    //check for availability name
    public boolean check(String userName);
    //check for availability
    public boolean hasUsers();
}
