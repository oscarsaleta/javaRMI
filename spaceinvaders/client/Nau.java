import java.io.IOException;

/**
 * @author Jaume Muntsant (1271258)
 *
 */
public class Nau {

	SInv f;

	int x;
	int y;
	int vida;
	static int Y = 0;
	static int VELOCITAT = SInv.PX / 50;
	int vel = VELOCITAT;
	static int TAMANY_NAU = SInv.PX / 30;

	Nau(SInv f, int x, int y, int vida) {
		this.f = f;
		this.x = x;
		this.y = y;
		this.vida = vida;
	}

	/**
	 * Mou les naus enemigues
	 * 
	 * @param num_nau
	 *            Numero de la nau enemiga
	 */
	void calculaMoviments(int num_nau) {

		int tir = (int) (Math.random() * 100);

		x = x + VELOCITAT;
		y = y + Y;

		if (vida == 0) {
			x = -f.PX;
			y = -f.PY;
		}

		if (num_nau == f.NOMBRE_NAUS - 1)
			Y = 0;

		if (tir % 100 == 0 && vida == 1)
			f.v_tir.addElement(new Tir(f, 1, x, y, 1));
	}

	/**
	 * Detecta quan es produeix una colisio
	 * 
	 * @return 1 si es produeix una colisio, 0 si no
	 */
	int detectaColisions() {
		if (x + 50 > f.PX && vida == 1) {
			Y = f.PY / 12;
			VELOCITAT = -VELOCITAT;
			return -1;
		}
		if (x - 10 < 0 && vida == 1) {
			Y = f.PY / 12;
			VELOCITAT = -VELOCITAT;
			return 1;
		}
		return 0;
	}

	/**
	 * Pinta les naus a la pantalla
	 * 
	 * @param num_nau
	 *            Numero de la nau
	 * @throws IOException
	 */
	void repinta(int num_nau) throws IOException {

		if (f.frame % f.FRAME < f.FRAME / 2) {
			if (num_nau < 12) {
				f.g.drawImage(f.img3, x, y, TAMANY_NAU, TAMANY_NAU, null);
			}
			if (num_nau < 36 && num_nau >= 12) {
				f.g.drawImage(f.img1, x, y, TAMANY_NAU, TAMANY_NAU, null);
			}
			if (num_nau >= 36) {
				f.g.drawImage(f.img2, x, y, TAMANY_NAU, TAMANY_NAU, null);
			}
		}

		else {
			if (num_nau < 12) {
				f.g.drawImage(f.img32, x, y, TAMANY_NAU, TAMANY_NAU, null);
			}
			if (num_nau < 36 && num_nau >= 12) {
				f.g.drawImage(f.img12, x, y, TAMANY_NAU, TAMANY_NAU, null);
			}
			if (num_nau >= 36) {
				f.g.drawImage(f.img22, x, y, TAMANY_NAU, TAMANY_NAU, null);
			}
		}

	}

	/**
	 * Indica si ha acabat la partida
	 * 
	 * @param humax
	 *            Posicio x de l'huma
	 * @param humay
	 *            Posicio y de l'huma
	 * @return 1 si acaba la partida, 0 si no
	 */
	int fi(int humax, int humay) {

		if (x + TAMANY_NAU >= humax && y + TAMANY_NAU >= humay)
			return 1;

		if (y >= f.PY)
			return 1;

		return 0;
	}

	/**
	 * Elimina el tir si se surt de la pantalla
	 */
	void eliminaTir() {

		for (int i = 0; i < f.v_tir.size(); i++)
			if (f.v_tir.get(i).y >= f.PY || f.v_tir.get(i).y <= 0)
				f.v_tir.remove(i);
	}

}
