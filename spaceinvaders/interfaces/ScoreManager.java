package spaceinvaders.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ScoreManager extends Remote {
    String sayHello() throws RemoteException;
}

