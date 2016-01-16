package spaceinvaders.interfaces;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ScoreManager extends Remote {
    int hiScore() throws RemoteException, IOException;
    String lecturaRanking(int fila, int columna) throws RemoteException, IOException;
}

