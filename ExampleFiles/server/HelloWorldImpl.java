package helloworld.server;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import helloworld.interfaces.HelloWorld;

public class HelloWorldImpl extends RemoteServer implements HelloWorld {
    // Constructor de la classe
    public HelloWorldImpl () throws RemoteException; {
        // el server escolta connexions al port 1234
        UnicastRemoteObject.exportObject(this,1234);
    }

    public String helloWorld (String name) throws RemoteException {
        return "Hello "+name;
    }
}
