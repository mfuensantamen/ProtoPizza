package protopito;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Protopito {
// TODO 
	// * revertir texto coste y boton
	// * añadir clicker
	// añadir clicker automatico con mejoras
	// elemento gatcha
	// objetos consumibles tiempo limitado

	private Datos datos = new Datos();
	public static List<Mejora> mejoras = new ArrayList<>();
	public static List<Mejora> mejorasClicker = new ArrayList<>();

	public Protopito() {
		// activas nombre / coste / incrementoClicker / incrementoCoste /
		// umbralDesbloqueo
		mejorasClicker.add(new Mejora("Clicker", 10, 1.15, 1.15, 10, Mejora.Tipo.CLICK));
		// pasivas nombre / coste / incrementoNps / incrementoCoste/ umbralDesbloqueo
		mejoras.add(new Mejora("Infra Pito", 100, 1.45, 1.15, 100));
		mejoras.add(new Mejora("Micro Pito", 500, 5.95, 1.15, 500));
		mejoras.add(new Mejora("Pitilin", 1000, 10.15, 1.15, 1000));
		mejoras.add(new Mejora("Pito", 5000, 80.8, 1.15, 5000));
		mejoras.add(new Mejora("Super Pito", 10000, 200.50, 1.15, 10000));
		mejoras.add(new Mejora("Mega Pito", 50000, 400.5, 1.15, 50000));
		mejoras.add(new Mejora("Hyper Pito", 100000, 1030.50, 1.15, 100000));
		mejoras.add(new Mejora("Ultra Pito", 500000, 10000, 1.15, 500000));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Protopito().raiz());
	}

	private void raiz() {
		Interfaz interfaz = new Interfaz(datos, mejoras, mejorasClicker);
		interfaz.render();
		timer(interfaz);
	}

	// loop que se actualiza cada 0.025 segundos
	private void timer(Interfaz interfaz) {

		new Timer(25, ejecuta -> {
			datos.reloj(0.025);
			interfaz.render();
		}).start();
	}

}
