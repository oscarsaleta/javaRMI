package spaceinvaders.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import spaceinvaders.client.SInv;

/**
 * @author Jaume Muntsant (1271258)
 *
 */
public class Ranking {

	SInv f;
	static int posR = 50;

	public Ranking(SInv f) throws IOException {
		this.f = f;
	}

	/**
	 * Llegeix el ranking
	 * @param fila Fila del document que llegim
	 * @param columna Columna del document que llegim
	 * @return El valor referent a aquesta posicio
	 * @throws IOException
	 */
	String lecturaRanking(int fila, int columna) throws IOException {
		File arxiu;
		FileReader fr;
		BufferedReader br;
		String linia;
		String s = "NULL";

		int contador = 0;

		arxiu = new File("spaceinvaders/server/Ranking.txt");
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

	/**
	 * Escriu al ranking
	 * @throws IOException
	 */
	void esripturaRanking() throws IOException {

		String linia1 = null;

		File arxiu = new File("spaceinvaders/server/Ranking.txt");
		FileReader fr = new FileReader(arxiu);
		BufferedReader br = new BufferedReader(fr);

		int punts[] = new int[11];

		for (int i = 0; i < punts.length - 1; i++) {

			linia1 = br.readLine();
			StringTokenizer st = new StringTokenizer(linia1);
			String s = st.nextToken();

			int y = Integer.parseInt(s);

			punts[i] = y;
		}
		
		punts[10] = 0;

		for (int i = 0; i < punts.length; i++) {
			if (f.puntuacio >= punts[i]) {
				for (int j = 9; j >= i; j--) {
					punts[j + 1] = punts[j];
				}
				posR = i;
				punts[i] = f.puntuacio;
				break;
			}

		}

		FileWriter fw = new FileWriter(arxiu);
		BufferedWriter bw = new BufferedWriter(fw);

		for (int i = 0; i < punts.length - 1; i++) {
			Integer a = new Integer(punts[i]);

			bw.append(a.toString());
			bw.flush();
			bw.newLine();
		}

	}

	/**
	 * Determina la puntuacio mes alta
	 * @return La puntuacio mes alta
	 * @throws IOException
	 */
	static int hiscore() throws IOException {

		String linia1 = null;

		File arxiu = new File("Ranking.txt");
		FileReader fr = new FileReader(arxiu);
		BufferedReader br = new BufferedReader(fr);

		linia1 = br.readLine();
		StringTokenizer st = new StringTokenizer(linia1);
		String s = st.nextToken();

		int y = Integer.parseInt(s);

		return y;
	}

	/**
	 * Determina la puntuacio que ha assolit el jugador al ranking
	 * @return La posicio al ranking del jugador
	 */
	int posicioRanking() {
		if (f.puntuacio != 0)
			return posR + 1;
		else
			return 50;
	}

}
