package spaceinvaders.client;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import spaceinvaders.client.SInv;

/**
 * @author Jaume Muntsant
 *
 */
public class Tir implements KeyListener {
	int x;
	int y;
	int vida;
	int pare;
	SInv f;
	boolean apertat;

	public Tir(SInv f, int vida, int x, int y, int pare) {
		this.f = f;
		this.x = x;
		this.y = y;
		this.vida = vida;
		this.pare = pare;
		if (pare == 0)
			this.f.addKeyListener(this);
	}

	/**
	 * Dibuixa les bales
	 */
	void printaBala() {
		if (vida == 0 && pare == 0) {
			y = y - f.PX/30;
			f.g.setColor(Color.red);
			f.g.drawLine(x, y, x, y + f.PX/100);
		} else if (vida == 1 && pare == 0) {
			f.g.setColor(Color.red);
			x = f.nau1.x + f.PX / 30;
			y = f.nau1.y+f.PX/20;
		} else if (vida == 1 && pare == 1) {
			y = y + f.PX/100;
			f.g.setColor(Color.blue);
			f.g.drawLine(x, y, x, y + f.PX/100);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE && vida == 1) {
			vida = 0;
			f.v_tir.addElement(new Tir(f, 1, f.nau1.x + f.PX / 30, f.nau1.y, 0));
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
	}
}
