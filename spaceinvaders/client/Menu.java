package spaceinvaders.client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextField;

import spaceinvaders.client.SInv;
import spaceinvaders.client.Ranking;
import spaceinvaders.interfaces.ScoreManager;

/**
 * @author Jaume Muntsant (1271258)
 *
 */
public class Menu implements ActionListener {

	SInv f;
	Ranking r;
	JTextField jtf;
    ScoreManager stub;

	JButton b1 = new JButton("Iniciar");
	JButton b2 = new JButton("Ranking");
	JButton b4 = new JButton("Sortir");
	JButton b5 = new JButton("Torna");

	static int Actiu = 0;

	public Menu(SInv f, ScoreManager stub) throws IOException {
		this.f = f;
        this.stub = stub;
	}

	/**
	 * Menu d'inici
	 * @throws InterruptedException
	 * @throws IOException
	 */
	void menuInici() throws InterruptedException, IOException {

		f.g.setColor(Color.black);
		f.g.fillRect(0, 0, f.PX, f.PY);
		f.g.drawImage(f.imgTitol, f.PX / 4, f.PY / 500, f.PX / 2, f.PX / 4,
				null);
		f.g.drawImage(f.img1, f.PX - f.PX / 4, f.PY - f.PY / 2, f.PX / 3,
				f.PX / 3, null);
		f.g.drawImage(f.img1, -f.PX / 12, f.PY - f.PY / 2, f.PX / 3, f.PX / 3,
				null);
		f.g.setColor(Color.green);
		f.g.setFont(f.g.getFont().deriveFont((float) f.PX / 50));
		f.g.drawString("Per: Jaume Muntsant", f.PX - f.PX / 4, f.PY / 3 + f.PY
				/ 20);

		f.setLayout(null);
		f.repaint();

		b1.setBounds((int) f.PX / 3, (int) (f.PY / 4 + f.PY / 8),
				(int) f.PX / 3, (int) f.PY / 10);
		b2.setBounds((int) f.PX / 3, (int) (f.PY / 4 + 2 * f.PY / 8),
				(int) f.PX / 3, (int) f.PY / 10);
		b4.setBounds((int) f.PX / 3, (int) (f.PY / 4 + 6 * f.PY / 10),
				(int) f.PX / 3, (int) f.PY / 10);

		f.setVisible(true);

		f.add(b1);
		f.add(b2);
		f.add(b4);

		b1.requestFocusInWindow();
		b2.requestFocusInWindow();
		b4.requestFocusInWindow();

		b1.addActionListener(this);
		b2.addActionListener(this);
		b4.addActionListener(this);

		while (Actiu == 0) {
			Thread.sleep(1000);
		}

		f.remove(b1);
		f.remove(b2);
		f.remove(b4);

	}

	/**
	 * Menu de Ranking
	 * @throws InterruptedException
	 * @throws IOException
	 */
	void menuRanking() throws InterruptedException, IOException {

		f.g.setColor(Color.black);
		f.g.fillRect(0, 0, f.PX, f.PY);
		f.g.drawImage(f.imgMort, 2 * f.PX / 3 - f.PX / 20, f.PY / 4, f.PX / 10,
				f.PX / 10, null);
		f.g.setColor(Color.red);
		f.g.drawLine(2 * f.PX / 3, f.PY / 2, 2 * f.PX / 3, f.PY / 2 - f.PY / 20);
		f.g.drawImage(f.imgJugador, f.PX / 2, f.PY / 2, f.PX / 3, f.PX / 3,
				null);
		f.g.setColor(Color.green);
		f.g.setFont(f.g.getFont().deriveFont(72.0f));
		f.g.drawString("Ranking", f.PX / 4, f.PY / 10);
		r = new Ranking(f);

		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 1; j++) {

				if (i == 1) {
					f.g.setColor(Color.red);
					if (i == r.posicioRanking())
						f.g.setColor(Color.yellow);
					f.g.setFont(f.g.getFont().deriveFont((float) f.PY / 15));
					f.g.drawString(+i + " " + stub.lecturaRanking(i, j), f.PX / 20
							+ (j - 1) * f.PX / 7, f.PY / 4 + i * f.PY / 15);
				} else if (i == 2 || i == 3) {
					f.g.setColor(Color.blue);
					if (i == r.posicioRanking())
						f.g.setColor(Color.yellow);
					f.g.setFont(f.g.getFont().deriveFont((float) f.PY / 15));
					f.g.drawString(+i + " " + stub.lecturaRanking(i, j), f.PX / 20
							+ (j - 1) * f.PX / 7, f.PY / 4 + i * f.PY / 15);
				} else {
					f.g.setColor(Color.white);
					if (i == r.posicioRanking())
						f.g.setColor(Color.yellow);
					f.g.setFont(f.g.getFont().deriveFont((float) f.PY / 15));
					f.g.drawString(+i + " " + stub.lecturaRanking(i, j), f.PX / 20
							+ (j - 1) * f.PX / 10, f.PY / 4 + f.PX / 100 + i
							* f.PY / 15);
				}
			}
		}

		f.repaint();

		b5.setBounds((int) f.PX / 3, (int) (f.PY / 4 + 6 * f.PY / 10),
				(int) f.PX / 3, (int) f.PY / 10);

		f.setVisible(true);
		f.add(b5);
		b5.requestFocusInWindow();
		b1.setFocusable(true);
		b5.addActionListener(this);

		while (Actiu == 2) {
			Thread.sleep(100);
		}
		f.remove(b5);
	}

	/**
	 * Menu final
	 * @throws IOException
	 * @throws InterruptedException
	 */
	void menuFinal() throws IOException, InterruptedException {

		String s = new String();
		Integer score = new Integer(f.puntuacio);

		s = score.toString();

		f.g.setColor(Color.black);
		f.g.fillRect(0, 0, f.PX, f.PY);
		f.g.setColor(Color.white);
		f.g.setFont(f.g.getFont().deriveFont((float) f.PX / 20));
		f.g.drawString("Puntuació final", f.PX / 10, f.PY / 2);
		f.g.setColor(Color.green);
		f.g.setFont(f.g.getFont().deriveFont((float) f.PX / 10));
		f.g.drawString(s, f.PX / 2, f.PY / 2 + f.PY / 100);

		f.repaint();
	
		//r = new Ranking(f);
		stub.esripturaRanking(f.puntuacio);

		Thread.sleep(3000);
		menuRanking();

	}

	/**
	 * Informació inicial sobre les puntuacions de les naus
	 * @throws InterruptedException
	 */
	void info() throws InterruptedException {

		f.g.setFont(f.g.getFont().deriveFont((float) f.PX / 40));
		f.g.setColor(Color.black);
		f.g.fillRect(0, 0, f.PX, f.PY);
		f.g.setColor(Color.blue);
		f.g.drawImage(f.img2, f.PX / 2 - f.PX / 10, f.PY / 4 - f.PY / 30,
				f.PX / 30, f.PX / 30, null);
		f.repaint();
		Thread.sleep(150);
		f.g.drawString("=", f.PX / 2, f.PY / 4);
		f.repaint();
		Thread.sleep(150);
		f.g.drawString("   100", f.PX / 2, f.PY / 4);
		f.repaint();
		Thread.sleep(150);
		f.g.setColor(Color.red);
		f.g.drawImage(f.img1, f.PX / 2 - f.PX / 10, f.PY / 4 + f.PY / 10 - f.PY
				/ 30, f.PX / 30, f.PX / 30, null);
		f.repaint();
		Thread.sleep(150);
		f.g.drawString("=", f.PX / 2, f.PY / 4 + f.PY / 10);
		f.repaint();
		Thread.sleep(150);
		f.g.drawString("   200", f.PX / 2, f.PY / 4 + f.PY / 10);
		f.repaint();
		Thread.sleep(150);
		f.g.setColor(Color.green);
		f.g.drawImage(f.img3, f.PX / 2 - f.PX / 10, f.PY / 4 + 2 * f.PY / 10
				- f.PY / 30, f.PX / 30, f.PX / 30, null);
		f.repaint();
		Thread.sleep(150);
		f.g.drawString("=", f.PX / 2, f.PY / 4 + 2 * f.PY / 10);
		f.repaint();
		Thread.sleep(150);
		f.g.drawString("   300", f.PX / 2, f.PY / 4 + 2 * f.PY / 10);
		f.repaint();
		Thread.sleep(150);
		f.g.setColor(Color.gray);
		f.g.drawImage(f.img4, f.PX / 2 - f.PX / 8, f.PY / 4 + 3 * f.PY / 10
				- f.PY / 30, f.PX / 10, f.PX / 30, null);
		f.repaint();
		Thread.sleep(150);
		f.g.drawString("=", f.PX / 2, f.PY / 4 + 3 * f.PY / 10);
		f.repaint();
		Thread.sleep(150);
		f.g.drawString("    ?", f.PX / 2, f.PY / 4 + 3 * f.PY / 10);
		f.repaint();
		Thread.sleep(1500);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == b4) {
			System.exit(0);
		}

		if (e.getSource() == b1) {
			Actiu = 1;
		}

		if (e.getSource() == b2) {
			Actiu = 2;
		}

		if (e.getSource() == b5) {
			Actiu = 0;
		}

	}

}
