package helloworld.client;

import helloworld.interfaces.HelloWorld;

public class Main implements Runnable {
    public static void main (String[] args) throws Exception {
        new Main().run();
    }

    public void run() {
        try { // locate remote object
            HelloWorld server = Naming.lookup(hostname+HelloWorld.NAME);
            //call the service
            String result = server.helloWorld("world");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
