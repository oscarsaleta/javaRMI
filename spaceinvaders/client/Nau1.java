package spaceinvaders.client;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import spaceinvaders.client.Nau;
import spaceinvaders.client.SInv;

/**
 * @author Jaume Muntsant (1271258)
 *
 */
public class Nau1 extends Nau implements KeyListener {

	SInv f;
	Graphics g;

	int VELOCITAT_HUMA = f.PX / 100;
	boolean keydreta = false;
	boolean keyesquerra = false;

	public Nau1(SInv f) {
		super(f, f.PX / 2, f.PY - f.PY / 10, 1);
		this.f = f;
		this.f.addKeyListener(this);
	}

	/**
	 * Pinta la nau humana
	 */
	void pintaNau() {
		if (keyesquerra == true) {
			if (x >= 0) {
				x -= VELOCITAT_HUMA;
			}
		}

		if (keydreta == true) {
			if (x + f.PX / 15 <= f.PX)
				x += VELOCITAT_HUMA;
		}

		f.g.drawImage(f.imgJugador, x, y, f.PX / 15, f.PY / 15, null);
		f.requestFocusInWindow();

	}

	/**
	 * Indica si la nau humana ha mort
	 * @param dreta Posicio dreta de la nau
	 * @param esquerra Posicio esquerra de la nau
	 * @param amunt Posicio superior de la nau
	 * @param avall Posicio inferior de la nau
	 * @return Si la nau ha mort o no
	 */
	int mort(int dreta, int esquerra, int amunt, int avall) {
		for (int i = 0; i < f.v_tir.size(); i++) {
			if (f.v_tir.get(i).x >= dreta && f.v_tir.get(i).x <= esquerra) {
				if (f.v_tir.get(i).y <= avall && f.v_tir.get(i).y >= amunt) {
					if (f.v_tir.get(i).pare == 1) {
						f.live--;
						return 1;
					}

				}
			}
		}
		return 0;

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A)
			keyesquerra = true;

		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D)
			keydreta = true;
        if (e.getKeyCode() == KeyEvent.VK_M)
            f.live = -1;
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A)
			keyesquerra = false;

		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D)
			keydreta = false;

	}

}
