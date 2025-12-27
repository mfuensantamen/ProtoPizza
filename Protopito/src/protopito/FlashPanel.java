package protopito;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class FlashPanel extends JPanel {

	private final Color base;
	private Timer flashTimer;

	public FlashPanel(Color base) {
		this.base = base;
		setBackground(base);
		setOpaque(true);
	}

	public void flash(Color flashColor, int ms) {
		// Siempre vuelve a base, nunca a "prev" (así no se lía)
		setBackground(flashColor);

		// Si ya había un flash en marcha, lo reiniciamos
		if (flashTimer != null && flashTimer.isRunning()) {
			flashTimer.stop();
		}

		flashTimer = new Timer(ms, e -> {
			setBackground(base);
			((Timer) e.getSource()).stop();
		});
		flashTimer.setRepeats(false);
		flashTimer.start();
	}
}
