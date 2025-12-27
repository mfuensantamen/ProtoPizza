package protoPizza;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class RoundedButton extends JButton {
// clase para generar boton con bordes redondeados

	private final int radius;

	public RoundedButton(String text, int radius) {
		super(text);
		this.radius = radius;

		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setOpaque(false);

		// quitamos márgenes raros del L&F
		setMargin(new Insets(0, 12, 0, 12));
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// fondo
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

		// borde suave
		g2.setColor(new Color(0, 0, 0, 50));
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

		// texto centrado REAL (baseline)
		String text = getText();
		if (text != null && !text.isEmpty()) {
			g2.setFont(getFont());
			FontMetrics fm = g2.getFontMetrics();
			int textWidth = fm.stringWidth(text);
			int x = (getWidth() - textWidth) / 2;

			// y centrado vertical exacto:
			int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

			g2.setColor(getForeground());
			g2.drawString(text, x, y);
		}

		g2.dispose();
	}
}
