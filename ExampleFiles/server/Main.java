import java.rmi.Naming;

public class Main() {
    public static void main(String[] args) throws Exception  {
        new Main();
    }

    public Main() {
        try {
            // create remote object (!!!)
            HelloWorldImpl server = new HelloWorldImpl();
            // register server with name NAME
            Naming.rebind(HelloWorldImpl.NAME,sever);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
}
