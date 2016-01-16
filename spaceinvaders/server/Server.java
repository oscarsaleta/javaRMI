package spaceinvaders.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.StringTokenizer;

import spaceinvaders.interfaces.ScoreManager;

public class Server implements ScoreManager {

    public Server() {}

    public int hiScore() throws RemoteException, IOException {
        String linia1 = null;
        File arxiu = new File("spaceinvaders/server/Ranking.txt");
        BufferedReader br = new BufferedReader(new FileReader(arxiu));
        linia1 = br.readLine();
        StringTokenizer st = new StringTokenizer(linia1);
        String s = st.nextToken();
        int y = Integer.parseInt(s);
        return y;
    }

    public String lecturaRanking(int fila, int columna) throws RemoteException, IOException {
		File arxiu;
		FileReader fr;
		BufferedReader br;
		String linia;
		String s = "NULL";

		int contador = 0;

		arxiu = new File("Ranking.txt");
		fr = new FileReader(arxiu);
		br = new BufferedReader(fr);

		while ((linia = br.readLine()) != null) {
			++contador;

			if (contador == fila) {
				StringTokenizer st = new StringTokenizer(linia);

				switch (columna) {
				case 1: {
					s = st.nextToken();
					return s;
				}

				case 2: {
					s = st.nextToken();
					s = st.nextToken();
					return s;
				}

				case 3: {
					s = st.nextToken();
					s = st.nextToken();
					s = st.nextToken();
					return s;
				}

				default: {
					return s;
				}

				}
			}
		}
		return s;
    }

    public String sayHello() {
        return "Hello, world!";
    }

    public static void main(String args[]) {

        try {
            Server obj = new Server();
            ScoreManager stub = (ScoreManager) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("ScoreManager", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

