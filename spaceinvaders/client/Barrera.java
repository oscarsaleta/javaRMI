package spaceinvaders.client;

import java.awt.Color;

import spaceinvaders.client.SInv;

/**
 * @author Jaume Muntsant (1271258)
 *
 */
public class Barrera {
	SInv f;
	int vida;
	int x;
	int y;

	Barrera(SInv f, int x, int y, int vida) {
		this.f = f;
		this.x = x;
		this.y = y;
		this.vida = vida;
	}

	/**
	 * Inicialitza les posicions de la barerra
	 */
	void inicialitzacioBarrera() {
		f.g.setColor(Color.orange);
		f.g.fillRect(x, y, f.PX / 90, f.PY / 200);
		vida = 1;
	}

	/**
	 * Dibuixa les posicions de la barrera
	 */
	void printaBarrera() {
		f.g.setColor(Color.orange);
		if (vida == 1)
			f.g.fillRect(x, y, f.PX / 80, f.PY / 75);
	}

	/**
	 * Destrueix la barerra si arriba un tir o una nau
	 */
	void destrueixBarrera() {
		for (int i = 0; i < f.v_tir.size(); i++) {
			if (f.v_tir.get(i).x >= x && f.v_tir.get(i).x <= x + f.PX / 90
					&& vida == 1) {
				if (f.v_tir.get(i).y <= y + f.PY / 20 && f.v_tir.get(i).y >= y) {
					f.v_tir.remove(i);
					vida = 0;
				}
			}
		}

		for (int i = 0; i < f.v_nau.length; i++) {
			if (f.v_nau[i].x >= x
					&& f.v_nau[i].x <= x + f.PX / 90
					|| (f.v_nau[i].x + Nau.TAMANY_NAU >= x && f.v_nau[i].x
							+ Nau.TAMANY_NAU <= x + f.PX / 90) && vida == 1) {
				if (f.v_nau[i].y <= y + f.PY / 20
						&& f.v_nau[i].y >= y
						|| (f.v_nau[i].y + Nau.TAMANY_NAU <= y + f.PY / 20 && f.v_nau[i].y
								+ Nau.TAMANY_NAU >= y)) {
					vida = 0;
				}
			}
		}

	}

}
