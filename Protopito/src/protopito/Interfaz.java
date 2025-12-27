package protopito;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class Interfaz extends JFrame {

	// etiquetas
	private JLabel lblNum;
	private JLabel lblNps;
	private JLabel lblNpc;
	// boton
	private BotonClicker btnClicker;
	// panel
	private JPanel panelMejoras;
	// seccion con scroll
	private JScrollPane scrollMejoras;
	// listas
	private List<Mejora> mejorasClicker = new ArrayList<>();
	private List<Mejora> mejoras = new ArrayList<>();
	private List<JLabel> labelsCoste = new ArrayList<>();
	private List<JButton> botonesCompra = new ArrayList<>();
	// motor
	private Datos datos;

	Font emoji = new Font("Segoe UI Emoji", Font.BOLD, 16);

	// === CAMBIO: colores centralizados para estados del botón ===
	private static final Color BTN_VERDE_OK = new Color(200, 255, 200);
	private static final Color BTN_GRIS_NO = new Color(210, 210, 210);
	private static final Color BTN_ROJO_LOCK = new Color(250, 180, 180);

	// === CAMBIO: NumberFormat en español sin decimales + con separador de miles
	// ===
	private final NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("es-ES"));
	{
		nf.setMaximumFractionDigits(0);
		nf.setMinimumFractionDigits(0);
		nf.setGroupingUsed(true); // puntos en miles
	}

	public Interfaz(Datos datos, List<Mejora> mejoras, List<Mejora> mejorasClicker) {
		this.datos = datos;
		this.mejoras = mejoras;
		this.mejorasClicker = mejorasClicker;
		construirUI();
		construirMejorasUI();
		conectarEventos();
		render();
	}

	// panel de arriba + central
	private void construirUI() {
		setTitle("ProtoPito - Incremental");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// tamaño de ventana
		setMinimumSize(new Dimension(600, 755));

		getContentPane().setLayout(new BorderLayout(0, 0));

		// panel de arriba
		JPanel panelSuperior = new JPanel();
		panelSuperior.setPreferredSize(new Dimension(800, 300));
		panelSuperior.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelSuperior.setBackground(new Color(150, 150, 170));
		panelSuperior.setLayout(new BorderLayout(0, 0));
		getContentPane().add(panelSuperior, BorderLayout.NORTH);

		// contenedor para num + nps + boton
		JPanel panelNums = new JPanel();
		panelNums.setBorder(new EmptyBorder(20, 20, 20, 20));
		panelNums.setOpaque(false);
		panelNums.setLayout(new BoxLayout(panelNums, BoxLayout.Y_AXIS));
		panelSuperior.add(panelNums, BorderLayout.CENTER);

		// etiqueta num
		lblNum = new JLabel("0");
		lblNum.setForeground(new Color(255, 215, 0));
		lblNum.setFont(new Font("Bahnschrift", Font.BOLD, 60));
		lblNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelNums.add(lblNum);
		// separador
		panelNums.add(Box.createVerticalStrut(8));

		// etiqueta nps
		lblNps = new JLabel("ERROR");
		lblNps.setForeground(new Color(255, 228, 225));
		lblNps.setFont(emoji);
		lblNps.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNps.setBorder(new EmptyBorder(6, 0, 6, 0));

		lblNpc = new JLabel("ERROR");
		lblNpc.setForeground(new Color(255, 228, 225));
		lblNpc.setFont(emoji);
		lblNpc.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNpc.setBorder(new EmptyBorder(6, 0, 6, 0));
		panelNums.add(lblNps);
		panelNums.add(lblNpc);
		// separador
		panelNums.add(Box.createVerticalStrut(20));

		// boton grande
		btnClicker = new BotonClicker();
		btnClicker.setText("[ Púlsame! ]");
		btnClicker.setFont(new Font("Trebuchet MS", Font.BOLD, 26));
		btnClicker.setForeground(new Color(60, 60, 60));
		btnClicker.setAlignmentX(Component.CENTER_ALIGNMENT);

		// tamaño del boton
		btnClicker.setPreferredSize(new Dimension(360, 110));
		btnClicker.setMaximumSize(new Dimension(360, 110));

		panelNums.add(btnClicker);
		// separador
		panelNums.add(Box.createVerticalStrut(20));
		// etiqueta pieBoton
		JLabel pieBoton = new JLabel("Pulsa para cocinar Pizzas");
		pieBoton.setForeground(new Color(225, 208, 205));
		pieBoton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 10));
		pieBoton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelNums.add(pieBoton);

		// zona central de mejoras
		panelMejoras = new JPanel();
		panelMejoras.setLayout(new BoxLayout(panelMejoras, BoxLayout.Y_AXIS));
		panelMejoras.setBorder(new EmptyBorder(15, 15, 15, 15));
		panelMejoras.setBackground(new Color(245, 245, 245));

		scrollMejoras = new JScrollPane(panelMejoras);
		scrollMejoras.setBorder(null);
		getContentPane().add(scrollMejoras, BorderLayout.CENTER);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	// accion de clicker
	private void conectarEventos() {
		btnClicker.addActionListener(e -> {
			datos.click();
			// actualizar render
			render();
		});
	}

	// filas de mejoras
	private void construirMejorasUI() {

		panelMejoras.removeAll();
		labelsCoste.clear();
		botonesCompra.clear();

		for (int i = 0; i < mejorasClicker.size(); i++) {
			Mejora m2 = mejorasClicker.get(i);

			FlashPanel fila = new FlashPanel(new Color(245, 245, 245)); // mismo que panelMejoras
			fila.setLayout(new BorderLayout(10, 0));
			fila.setBorder(new EmptyBorder(4, 4, 4, 4));

			JLabel lblCoste = new JLabel("ERROR");
			lblCoste.setFont(emoji);
			lblCoste.setForeground(Color.DARK_GRAY);

			RoundedButton btnCompra = new RoundedButton(nf.format(m2.getCoste()), 14);
			btnCompra.setFont(emoji);
			btnCompra.setFocusPainted(false);
			btnCompra.setBackground(BTN_VERDE_OK); // (render() lo ajustará según estado)
			btnCompra.setPreferredSize(new Dimension(72, 34)); // tamaño fijo bonito
			btnCompra.setMaximumSize(new Dimension(72, 34));
			// centra texto bien
			btnCompra.setHorizontalAlignment(SwingConstants.CENTER);
			btnCompra.setVerticalAlignment(SwingConstants.CENTER);
			btnCompra.setMargin(new Insets(0, 12, 0, 12));

			// accion del boton de compra
			btnCompra.addActionListener(ev -> {
				if (datos.verificarCompra(m2.getCoste())) {
					m2.comprar(datos);
					fila.flash(new Color(210, 255, 210), 120); // flash verde suave
					render();
				}
			});

			labelsCoste.add(lblCoste);
			botonesCompra.add(btnCompra);

			fila.add(lblCoste, BorderLayout.CENTER);
			fila.add(btnCompra, BorderLayout.EAST);

			panelMejoras.add(fila);
			panelMejoras.add(Box.createVerticalStrut(6));
		}

		for (int i = 0; i < mejoras.size(); i++) {
			Mejora m = mejoras.get(i);

			FlashPanel fila = new FlashPanel(new Color(245, 245, 245)); // mismo que panelMejoras
			fila.setLayout(new BorderLayout(10, 0));
			fila.setBorder(new EmptyBorder(4, 4, 4, 4));

			JLabel lblCoste = new JLabel("ERROR");
			lblCoste.setFont(emoji);
			lblCoste.setForeground(Color.DARK_GRAY);

			RoundedButton btnCompra = new RoundedButton(nf.format(m.getCoste()), 14);
			btnCompra.setFont(emoji);
			btnCompra.setFocusPainted(false);
			btnCompra.setBackground(BTN_VERDE_OK); // (render() lo ajustará según estado)
			btnCompra.setPreferredSize(new Dimension(72, 34)); // tamaño fijo bonito
			btnCompra.setMaximumSize(new Dimension(72, 34));
			// centra texto bien
			btnCompra.setHorizontalAlignment(SwingConstants.CENTER);
			btnCompra.setVerticalAlignment(SwingConstants.CENTER);
			btnCompra.setMargin(new Insets(0, 12, 0, 12));

			// accion de boton de compra
			btnCompra.addActionListener(ev -> {
				if (datos.verificarCompra(m.getCoste())) {
					m.comprar(datos);
					fila.flash(new Color(210, 255, 210), 120); // flash verde suave
					render();
				}
			});

			labelsCoste.add(lblCoste);
			botonesCompra.add(btnCompra);

			fila.add(lblCoste, BorderLayout.CENTER);
			fila.add(btnCompra, BorderLayout.EAST);

			panelMejoras.add(fila);
			panelMejoras.add(Box.createVerticalStrut(6));
		}

		panelMejoras.revalidate();
		panelMejoras.repaint();
	}

	private String formatAbreviado(long n) {

		final String[] sufijos = { "", // 10^0
				"K", // 10^3
				"M", // 10^6
				"B", // 10^9
				"T", // 10^12
				"Qa", // 10^15
				"Qi", // 10^18
				"Sx", // 10^21
				"Sp", // 10^24
				"Oc", // 10^27
				"No" // 10^30
		};

		if (n < 10_000)
			return String.valueOf(n);

		double valor = n;
		int indice = 0;

		while (valor >= 1_000.0 && indice < sufijos.length - 1) {
			valor /= 1_000.0;
			indice++;
		}

		// evita cosas tipo 1000.0K (pasa a 1.00M)
		if (valor >= 999.5 && indice < sufijos.length - 1) {
			valor /= 1_000.0;
			indice++;
		}

		if (valor >= 100) {
			return String.format(Locale.US, "%.0f%s", valor, sufijos[indice]);
		} else if (valor >= 10) {
			return String.format(Locale.US, "%.1f%s", valor, sufijos[indice]);
		} else {
			return String.format(Locale.US, "%.2f%s", valor, sufijos[indice]);
		}
	}

	public void render() {

		// === CAMBIO: forzamos entero para que no salgan decimales ===
		lblNum.setText(nf.format((long) datos.getNum()));

		lblNps.setText(String.format("🍕/s: %.2f ", datos.getNps()));

		double npc = datos.getClickIncremento() + datos.getNps() / 50;
		double npcAuto = datos.getPeriodoAutoClicker();
		if (datos.getNivelAutoClicker() == 0) {
			// si no esta comprado no se enseña infor
			lblNpc.setText(String.format("Chef: +%.2f 🍕 ", npc));
		} else {
			// cuando se compra se enseña
			lblNpc.setText(String.format("Chef: +%.2f 🍕 | Cocineros: cada %.2fs ", npc, npcAuto));
		}

		if (datos.autoCickerPulsado()) {
			btnClicker.flash(new Color(230, 220, 255), 100);
		}

		// 1) Render de mejoras CLICK (primer bloque de la lista)
		for (int i = 0; i < mejorasClicker.size(); i++) {
			Mejora m = mejorasClicker.get(i);
			JLabel lbl = labelsCoste.get(i);
			JButton btn = botonesCompra.get(i);

			boolean unlock = m.desbloquado(datos.getMaximo());

			if (!unlock) {
				// === ESTADO 1: bloqueado (candado) ===
				lbl.setFont(emoji);
				btn.setFont(emoji);
				lbl.setText("🔒 Requiere " + (formatAbreviado((int) m.getCoste())) + " 🍕");
				btn.setText("🔒");
				btn.setBackground(BTN_ROJO_LOCK);
				btn.setEnabled(false);
				btn.setCursor(Cursor.getDefaultCursor());
			} else {
				// === CAMBIO: estados separados (gris si no alcanza, verde si se puede) ===
				lbl.setText(String.format("%s [ %d ]", m.getNombre(), m.getNivel()));

				long coste = (long) m.getCoste(); // === CAMBIO: sin (int) para no romper a futuro ===
				btn.setText(formatAbreviado(coste));

				boolean canBuy = datos.verificarCompra(m.getCoste());

				if (canBuy) {
					// ESTADO 2: se puede comprar
					btn.setBackground(BTN_VERDE_OK);
					btn.setEnabled(true);
					btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					// ESTADO 3: desbloqueado pero no llega el dinero
					btn.setBackground(BTN_GRIS_NO);
					btn.setEnabled(false);
					btn.setCursor(Cursor.getDefaultCursor());
				}
			}
		}

		// 2) Render de mejoras NPS (segundo bloque)
		int offset = mejorasClicker.size();
		for (int i = 0; i < mejoras.size(); i++) {
			Mejora m = mejoras.get(i);
			JLabel lbl = labelsCoste.get(offset + i);
			JButton btn = botonesCompra.get(offset + i);

			boolean unlock = m.desbloquado(datos.getMaximo());

			if (!unlock) {
				// === ESTADO 1: bloqueado (candado) ===
				lbl.setFont(emoji);
				btn.setFont(emoji);
				lbl.setText("🔒 Requiere " + (formatAbreviado((int) m.getCoste())) + " 🍕");
				btn.setText("🔒");
				btn.setBackground(BTN_ROJO_LOCK);
				btn.setEnabled(false);
				btn.setCursor(Cursor.getDefaultCursor());
			} else {
				// === CAMBIO: estados separados (gris si no alcanza, verde si se puede) ===
				lbl.setText(String.format("%s [ %d ]", m.getNombre(), m.getNivel()));

				long coste = (long) m.getCoste(); // === CAMBIO: sin (int) para no romper a futuro ===
				btn.setText(formatAbreviado(coste));

				boolean canBuy = datos.verificarCompra(m.getCoste());

				if (canBuy) {
					// ESTADO 2: se puede comprar
					btn.setBackground(BTN_VERDE_OK);
					btn.setEnabled(true);
					btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					// ESTADO 3: desbloqueado pero no llega el dinero
					btn.setBackground(BTN_GRIS_NO);
					btn.setEnabled(false);
					btn.setCursor(Cursor.getDefaultCursor());
				}
			}
		}
	}
}
