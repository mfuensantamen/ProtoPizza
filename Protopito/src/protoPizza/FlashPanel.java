package protoPizza;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.Timer;

public class FlashPanel extends JPanel {
// clasepara el feedback de la compra de las mejoras un flash verde

	private final Color porDefecto;
	private Timer flashTimer;

	public FlashPanel(Color porDefecto) {
		this.porDefecto = porDefecto;
		setBackground(porDefecto);
		setOpaque(true);
	}

	public void flash(Color flashColor, int ms) {

		setBackground(flashColor);

		flashTimer = new Timer(ms, ejecutar -> {
			setBackground(porDefecto);
			((Timer) ejecutar.getSource()).stop();
		});
		flashTimer.setRepeats(false);
		flashTimer.start();
	}
}
