
package protopito;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.ButtonModel;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class BotonClicker extends JButton {
	private int radius = 80;

	// colores
	private Color normalBg = new Color(210, 255, 210);
	private Color hoverBg = new Color(180, 245, 180);
	private Color pressedBg = new Color(140, 230, 140);
	private Color disabledBg = new Color(210, 210, 210);

	private Color borderColor = new Color(90, 140, 90);

	public BotonClicker() {
		super();

		setRolloverEnabled(true);

		// por defecto todo
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setOpaque(false);

		// padding
		setMargin(new Insets(20, 40, 20, 40));

		setFont(getFont().deriveFont(Font.BOLD, 22f));

		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			ButtonModel model = getModel();

			// color por estado
			Color bg;

			if (!isEnabled()) {
				bg = disabledBg;
			} else if (model.isPressed()) {
				bg = pressedBg;
			} else if (model.isRollover()) {
				bg = hoverBg;
			} else {
				bg = normalBg;
			}

			// fondo redondeado
			int w = getWidth();
			int h = getHeight();

			g2.setColor(bg);
			g2.fillRoundRect(0, 0, w - 1, h - 1, radius, radius);

			// borde
			g2.setColor(borderColor);
			g2.drawRoundRect(0, 0, w - 1, h - 1, radius, radius);

			// pinta texto y icono encima del fondo
			super.paintComponent(g2);
		} finally {
			g2.dispose();
		}
	}

	// ajustar colores desde codigo
	public void setColors(Color normal, Color hover, Color pressed) {
		this.normalBg = normal;
		this.hoverBg = hover;
		this.pressedBg = pressed;
		repaint();
	}
}