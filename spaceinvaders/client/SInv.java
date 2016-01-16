package spaceinvaders.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import spaceinvaders.interfaces.ScoreManager;

/**
 * @author Jaume Muntsant (1271258)
 *
 */
public class SInv extends JFrame {

	BufferedImage img1 = ImageIO.read(this.getClass().getResource("I1.png"));
	BufferedImage img12 = ImageIO.read(this.getClass().getResource("I12.png"));
	BufferedImage img2 = ImageIO.read(this.getClass().getResource("I2.png"));
	BufferedImage img22 = ImageIO.read(this.getClass().getResource("I22.png"));
	BufferedImage img3 = ImageIO.read(this.getClass().getResource("I3.png"));
	BufferedImage img32 = ImageIO.read(this.getClass().getResource("I32.png"));
	BufferedImage imgJugador = ImageIO.read(this.getClass().getResource(
			"Jugador.png"));
	BufferedImage imgMort = ImageIO.read(this.getClass()
			.getResource("Mort.png"));
	BufferedImage img4 = ImageIO.read(this.getClass().getResource("I4.png"));
	BufferedImage imgTitol = ImageIO.read(this.getClass().getResource(
			"Titol2.png"));

	Image img;
	Graphics g;
	Nau2 nau2;
	Nau1 nau1;

	int nivell = 1;
	int puntuacio = 0;
	int live = 3;
	int hiScore = 0;
	int morts = 0;

	int frame = 0;
	int FRAME = 30;

	int NOMBRE_NAUS = 60;

	static Toolkit tk = Toolkit.getDefaultToolkit();
	static int PX = ((int) tk.getScreenSize().getWidth());
	int PY = ((int) tk.getScreenSize().getHeight());

	Vector<Tir> v_tir = new Vector<Tir>(0);
	Nau v_nau[] = new Nau[NOMBRE_NAUS];
	Barrera v_barrera_1[] = new Barrera[40];
	Barrera v_barrera_2[] = new Barrera[40];
	Barrera v_barrera_3[] = new Barrera[40];
	Barrera v_barrera_4[] = new Barrera[40];

	public static void main(String[] args) throws IOException {
        
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            ScoreManager stub = (ScoreManager) registry.lookup("ScoreManager");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
        
		new SInv();
	}

	SInv() throws IOException {

		Menu menu = new Menu(this);

		nau1 = new Nau1(this);
		nau2 = new Nau2(this);

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		if (gd.isFullScreenSupported()) {
			setUndecorated(true);
			gd.setFullScreenWindow(this);
		}

		else {
			JOptionPane.showMessageDialog(null,
					"ERROR: NO es disposa de pantalla completa");
		}

		getContentPane().setBackground(Color.BLACK);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(PX, PY);
		setVisible(true);
		img = createImage(PX, PY);
		g = img.getGraphics();

		nau2.vida = 1;
		v_tir.addElement(new Tir(this, 1, nau1.x + PX / 30, nau1.y, 0));

		try {
			do {
				while (Menu.Actiu != 1) {
					switch (Menu.Actiu) {
					case 0:
						menu.menuInici();
						break;
					case 2:
						menu.menuRanking();
						break;
					}
				}

				inicialitzacio_Naus();
				menu.info();
				live = 3;
				puntuacio = 0;
				morts = 0;
				nivell = 1;
				hiScore = stub.hiscore();
				barrera();

				do {
					pintaPuntuacions();
					detectaColisions();
					calculaMoviments();
					pintaNaus();
					pintaBarrera();
					nouNivell();
					mort();
					if (conquesta() == 1)
						break;

					nau1.eliminaTir();

					Thread.sleep(25);

					FRAME = (30 - (morts / 3)) / nivell;
					frame++;

				} while (live >= 0);

				g.setColor(Color.white);
				g.setFont(g.getFont().deriveFont((float) PX / 10));
				g.drawString("GAME OVER", PX / 5, PY / 2);
				repaint();

				Thread.sleep(3000);

				menu.menuFinal();

				Thread.sleep(3000);
				Menu.Actiu = 0;

			} while (true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * Retorna la puntucio en funcio de la nau eliminada
	 * 
	 * @param num_nau
	 *            nombre de la nau eliminada
	 * @return Puntuacio en cada instant
	 */
	int score(int num_nau) {

		if (num_nau < 12) {
			g.setColor(Color.green);
			puntuacio += 300;
			g.setFont(g.getFont().deriveFont(PX / 100));
			g.drawString("300", v_nau[num_nau].x, v_nau[num_nau].y);
		}

		if (num_nau < 36 && num_nau >= 12) {
			g.setColor(Color.red);
			puntuacio += 200;
			g.setFont(g.getFont().deriveFont(PX / 100));
			g.drawString("200", v_nau[num_nau].x, v_nau[num_nau].y);
		}
		if (num_nau >= 36 && num_nau != 99) {
			g.setColor(Color.blue);
			puntuacio += 100;
			g.setFont(g.getFont().deriveFont(PX / 100));
			g.drawString("100", v_nau[num_nau].x, v_nau[num_nau].y);
		}

		if (num_nau == 99) {
			g.setColor(Color.orange);
			int aleatoriN2 = (int) (Math.random() * 10) * 100;
			puntuacio += aleatoriN2;
			g.setFont(g.getFont().deriveFont(PX / 10));
			g.drawString(Integer.toString(aleatoriN2), nau2.x, nau2.y);
		}

		return puntuacio;

	}

	/**
	 * Iniacialitza les naus enemigues
	 */
	void inicialitzacio_Naus() {

		for (int i = 0; i < v_nau.length; i++) {
			v_nau[i] = new Nau(this, PX / 15 + (i % 12) * PX / 20, PY / 5 + PY
					/ 15 * (i / 12), 1);
		}

		Nau.VELOCITAT = Math.abs(Nau.VELOCITAT);

	}

	/**
	 * Dibuixa les puntuacions a la pantalla: puntuacio, puntuacio maxima, vida
	 */
	void pintaPuntuacions() {
		g.setColor(Color.black);
		g.fillRect(0, 0, PX, PY);
		g.setColor(Color.white);
		g.setFont(g.getFont().deriveFont((float) PX / 50));
		g.drawString("SCORE", PX / 10, PY / 10);
		g.drawString("HI-SCORE", PX / 2 - PX / 10, PY / 10);
		g.drawString("LIVES", PX - PX / 4, PY / 10);
		g.setColor(Color.green);
		g.drawString("" + live, PX - PX / 4 + PX / 10, PY / 10);
		g.setColor(Color.red);
		if (puntuacio >= hiScore) {
			hiScore = puntuacio;
			g.drawString("" + puntuacio, PX / 10 + PX / 10, PY / 10);
			g.drawString("" + hiScore, PX / 2 + PX / 20, PY / 10);
		} else {
			g.drawString("" + hiScore, PX / 2 + PX / 20, PY / 10);
			g.setColor(Color.green);
			g.drawString("" + puntuacio, PX / 10 + PX / 10, PY / 10);
		}

	}

	/**
	 * Detecta quan una nau xoca contra una paret
	 */
	void detectaColisions() {
		for (int i = 0; i < v_nau.length; i++) {
			if (frame % FRAME == 0) {
				if (v_nau[i].detectaColisions() != 0)
					break;
			}
		}
	}

	/**
	 * Mou les naus enemigues
	 */
	void calculaMoviments() {
		for (int i = 0; i < v_nau.length; i++) {
			if (frame % FRAME == 0)
				v_nau[i].calculaMoviments(i);
		}
	}

	/**
	 * Pinta les naus enemigues per pantalla
	 * 
	 * @throws IOException
	 */
	void pintaNaus() throws IOException {
		for (int i = 0; i < v_nau.length; i++) {
			if (v_nau[i].vida == 1) {
				v_nau[i].repinta(i);
			}
		}
		nau1.pintaNau();
		if (frame >= 500)
			nau2.pintaNau();
	}

	/**
	 * Indica si les naus enemigues consegueixen arribar a terra
	 * 
	 * @return 1 si han arribat, 0 si no
	 */
	int conquesta() {
		for (int i = 0; i < v_nau.length; i++) {
			if (v_nau[i].fi(nau1.x, nau1.y) == 1) {
				v_tir.removeAllElements();
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Decideix si ha acabat el nivell i inicialitza el nou nivell
	 * 
	 * @throws InterruptedException
	 */
	void nouNivell() throws InterruptedException {
		int vius = 0;

		for (int i = 0; i < v_nau.length; i++) {
			if (v_nau[i].vida == 1)
				vius++;
		}

		if (vius == 0) {
			nivell++;
			barrera();
			nau2 = new Nau2(this);
			nau2.vida = 1;

			v_tir.removeAllElements();

			g.setColor(Color.black);
			g.fillRect(0, 0, PX, PY);
			g.setColor(Color.yellow);
			g.setFont(g.getFont().deriveFont((float) PX / 10));
			g.drawString("Nivell: " + nivell, PX / 50, PY / 2);
			g.setFont(g.getFont().deriveFont((float) PX / 50));
			g.setColor(Color.white);
			g.drawLine(PX / 2, 3 * PY / 4, PX / 2, PY / 4);
			g.drawString("Score: ", PX / 2 + PX / 10, PY / 3);
			if (puntuacio == hiScore) {
				g.setColor(Color.red);
				g.drawString("" + puntuacio, PX / 2 + PX / 10 + PX / 10, PY / 3);
			} else {
				g.setColor(Color.green);
				g.drawString("" + puntuacio, PX / 2 + PX / 10 + PX / 10, PY / 3);
			}
			g.setColor(Color.white);
			g.drawString("HiScore: ", PX / 2 + PX / 10, 2 * PY / 3);
			g.setColor(Color.red);
			g.drawString("" + hiScore, PX / 2 + PX / 10 + PX / 10, 2 * PY / 3);
			repaint();

			Thread.sleep(3000);
			morts = 0;
			frame = 0;
			FRAME = 30;

			inicialitzacio_Naus();
		}
	}

	/**
	 * Indica si alguna de les naus ha mort
	 * 
	 * @throws InterruptedException
	 */
	void mort() throws InterruptedException {

		if (nau1.mort(nau1.x, nau1.x + PX / 15, nau1.y, nau1.y + PY / 15) == 1) {
			g.setColor(Color.black);
			g.fillRect(nau1.x, nau1.y, PX / 10, PY / 10);
			this.g.drawImage(imgMort, nau1.x, nau1.y, PX / 20, PY / 20, null);

			v_tir.removeAllElements();
			repaint();
			Thread.sleep(3000);
		}

		for (int i = 0; i < v_tir.size(); i++) {
			if (v_tir.get(i).pare == 0) {

				for (int j = 0; j < v_nau.length; j++) {
					if (v_tir.get(i).x >= v_nau[j].x
							&& v_tir.get(i).x <= v_nau[j].x + PX / 20) {
						if (v_tir.get(i).y >= v_nau[j].y
								&& v_tir.get(i).y <= v_nau[j].y + PY / 20) {
							v_nau[j].vida = 0;
							this.g.drawImage(imgMort, v_nau[j].x, v_nau[j].y,
									PX / 20, PY / 20, null);
							score(j);
							v_tir.remove(i);
							v_nau[j].x = -PX;
							v_nau[j].y = -PY;
							morts++;
							break;
						}
					}
				}
			}
			v_tir.get(i).printaBala();
		}

		if (nau2.mort(nau2.x, nau2.x + PX / 10, nau2.y, nau2.y + PY / 20) == 1) {
			g.setColor(Color.black);
			g.fillRect(nau2.x, nau2.y, PX / 10, PY / 20);
			g.drawImage(imgMort, nau2.x, nau2.y, PX / 10, PY / 10, null);
			repaint();
			score(99);
			nau2.x = -PX;
			nau2.y = -PY;
		}

	}

	/**
	 * Inicialitza la barrera la barrera
	 */
	void barrera() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 4; j++) {
				v_barrera_1[10 * j + i] = new Barrera(this, PX / 9 + i * PX
						/ 90, PY - PY / 4 - j * PY / 80, 0);
				v_barrera_2[10 * j + i] = new Barrera(this, 3 * PX / 9 + i * PX
						/ 90, PY - PY / 4 - j * PY / 80, 0);
				v_barrera_3[10 * j + i] = new Barrera(this, 5 * PX / 9 + i * PX
						/ 90, PY - PY / 4 - j * PY / 80, 0);
				v_barrera_4[10 * j + i] = new Barrera(this, 7 * PX / 9 + i * PX
						/ 90, PY - PY / 4 - j * PY / 80, 0);
				v_barrera_1[10 * j + i].inicialitzacioBarrera();
				v_barrera_2[10 * j + i].inicialitzacioBarrera();
				v_barrera_3[10 * j + i].inicialitzacioBarrera();
				v_barrera_4[10 * j + i].inicialitzacioBarrera();
			}
		}
	}

	/**
	 * Destrueix i pinta la barrera
	 */
	void pintaBarrera() {
		for (int i = 0; i < v_barrera_1.length; i++) {
			v_barrera_1[i].destrueixBarrera();
			v_barrera_1[i].printaBarrera();
			v_barrera_2[i].destrueixBarrera();
			v_barrera_2[i].printaBarrera();
			v_barrera_3[i].destrueixBarrera();
			v_barrera_3[i].printaBarrera();
			v_barrera_4[i].destrueixBarrera();
			v_barrera_4[i].printaBarrera();
		}
		repaint();
	}

}
