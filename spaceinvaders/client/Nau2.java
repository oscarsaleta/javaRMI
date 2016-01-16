package spaceinvaders.client;

import java.awt.Graphics;

import spaceinvaders.client.Nau;
import spaceinvaders.client.SInv;

/**
 * @author Jaume Muntsant (1271258)
 *
 */
public class Nau2 extends Nau {

	SInv f;
	Graphics g;

	int VELOCITAT_NAU2 = f.PX / 300;
	int x = 0;
	int y = 0;

	public Nau2(SInv f) {
		super(f, 0, 0, 1);
		this.f = f;
	}

	/**
	 * Pinta la nau enemiga mare
	 */
	void pintaNau() {

		if (vida == 1) {
			f.g.drawImage(f.img4, x, f.PY / 8, f.PX / 10, f.PY / 20, null);

			x = x + VELOCITAT_NAU2;

			if (x >= f.PX) {
				vida = 0;
			}
		}
	}

	/**
	 * Indica si la nau mare ha mort
	 * @param dreta Posicio dreta la la nau mare
	 * @param esquerra Posicio esquerra de la nau mare
	 * @param amunt Posicio superior de la nau mare
	 * @param avall Posicio inferior de la nau mare
	 * @return Si la nau mare ha mort o no
	 */
	int mort(int dreta, int esquerra, int amunt, int avall) {
		for (int i = 0; i < f.v_tir.size(); i++) {
			if (f.v_tir.get(i).x >= dreta && f.v_tir.get(i).x <= esquerra) {
				if (f.v_tir.get(i).y <= avall && f.v_tir.get(i).y >= amunt) {
					if (f.v_tir.get(i).pare == 0) {
						vida = 0;
						f.v_tir.remove(i);
						return 1;
					}

				}
			}
		}
		return 0;

	}

}
