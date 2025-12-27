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

		// ACTIVAS nombre / coste / incrementoCoste / umbralDesbloqueo /
		// incrementoClicker
		mejorasClicker.add(new Mejora("Clicker", 10, 1.05, 10, accion -> datos.subirClicker(0.15)));
		mejorasClicker.add(new Mejora("Auto Clicker", 100, 1.05, 100, accion -> datos.subirAutoClicker()));
		// PASIVAS nombre / coste / incrementoCoste / umbralDesbloqueo /
		// incrementoClicker
		mejoras.add(new Mejora("Infra Pito", 500, 1.05, 500, accion -> datos.subirNPS(1.45)));
		mejoras.add(new Mejora("Micro Pito", 750, 1.05, 750, accion -> datos.subirNPS(5.95)));
		mejoras.add(new Mejora("Pitilin", 1000, 1.15, 1000, accion -> datos.subirNPS(10.15)));
		mejoras.add(new Mejora("Pito", 5000, 1.15, 5000, accion -> datos.subirNPS(80.8)));
		mejoras.add(new Mejora("Super Pito", 10000, 1.15, 10000, accion -> datos.subirNPS(200.50)));
		mejoras.add(new Mejora("Mega Pito", 50000, 1.15, 50000, accion -> datos.subirNPS(400.5)));
		mejoras.add(new Mejora("Hyper Pito", 100000, 1.15, 100000, accion -> datos.subirNPS(1030.50)));
		mejoras.add(new Mejora("Ultra Pito", 500000, 1.15, 500000, accion -> datos.subirNPS(10000)));
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
