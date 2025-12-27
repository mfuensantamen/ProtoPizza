package protopito;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
		lblNps = new JLabel("Nums/s: 0.00");
		lblNps.setForeground(new Color(255, 228, 225));
		lblNps.setFont(new Font("Bahnschrift", Font.ITALIC, 18));
		lblNps.setAlignmentX(Component.CENTER_ALIGNMENT);

		lblNpc = new JLabel("+0.00 por Click / Autoclick cada 0.00s");
		lblNpc.setForeground(new Color(255, 228, 225));
		lblNpc.setFont(new Font("Bahnschrift", Font.ITALIC, 18));
		lblNpc.setAlignmentX(Component.CENTER_ALIGNMENT);
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

			JPanel fila = new JPanel();
			fila.setOpaque(false);
			fila.setLayout(new BorderLayout(10, 0));
			fila.setBorder(new EmptyBorder(8, 8, 8, 8));

			JLabel lblCoste = new JLabel("ERROR");
			lblCoste.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
			lblCoste.setForeground(Color.DARK_GRAY);

			JButton btnCompra = new JButton("" + m2.getCoste());
			btnCompra.setFocusPainted(false);
			btnCompra.setBackground(new Color(200, 255, 200));

			// accion del boton de compra
			btnCompra.addActionListener(ev -> {
				m2.comprar(datos);
				// actualiza
				render();
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

			JPanel fila = new JPanel();
			fila.setOpaque(false);
			fila.setLayout(new BorderLayout(10, 0));
			fila.setBorder(new EmptyBorder(8, 8, 8, 8));

			JLabel lblCoste = new JLabel("ERROR");
			lblCoste.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
			lblCoste.setForeground(Color.DARK_GRAY);

			JButton btnCompra = new JButton("" + m.getCoste());
			btnCompra.setFocusPainted(false);
			btnCompra.setBackground(new Color(200, 255, 200));
			// accion de boton de compra
			btnCompra.addActionListener(ev -> {
				m.comprar(datos);
				// actualiza
				render();
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

	public void render() {

		// actualizar nums
		lblNum.setText("" + (int) datos.getNum());
		lblNps.setText(String.format("Nums/s: %.2f ", datos.getNps()));

		double npc = datos.getClickIncremento() + datos.getNps() / 50;
		double npcAuto = npc / datos.getPeriodoAutoClicker();
		if (datos.getNivelAutoClicker() == 0) {
			// si no esta comprado no se enseña infor
			lblNpc.setText(String.format("+%.2f por Click ", npc));
		} else {
			// cuando se compra se enseña
			lblNpc.setText(String.format("+%.2f por Click / Autoclick %.2f ", npc, npcAuto));
		}

		if (datos.autoCickerPulsado()) {
			btnClicker.flash(new Color(230, 220, 255), 100);
		}

		// renderizamos en 2 bucles con offset.
		// 1) Render de mejoras CLICK (primer bloque de la lista)
		for (int i = 0; i < mejorasClicker.size(); i++) {
			Mejora m = mejorasClicker.get(i);
			JLabel lbl = labelsCoste.get(i);
			JButton btn = botonesCompra.get(i);

			boolean unlock = m.desbloquado(datos.getMaximo());
			if (!unlock) {
				lbl.setText("[???] Mejora Bloqueada [???]");
				btn.setText("[???]");
				btn.setEnabled(false);
			} else {
				lbl.setText(String.format("%s [Nivel %d]", m.getNombre(), m.getNivel()));
				btn.setText("" + (int) m.getCoste());
				btn.setEnabled(datos.verificarCompra(m.getCoste()));
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
				lbl.setText("[???] Mejora Bloqueada [???]");
				btn.setText("[???]");
				btn.setEnabled(false);
			} else {
				lbl.setText(String.format("%s [Nivel %d]", m.getNombre(), m.getNivel()));
				btn.setText("" + (int) m.getCoste());
				btn.setEnabled(datos.verificarCompra(m.getCoste()));
			}
		}
	}

}