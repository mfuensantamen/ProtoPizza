package protoPizza;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ProtoPizzaAPP {
// clase para la ejecucion de la app final, controla el timer interno el "motor" y parametros de las mejoras

// TODO 
	// * revertir texto coste y boton
	// * añadir clicker
	// * añadir clicker automatico con mejoras
	// añadir boton grande imagen de pizza
	// elemento gatcha
	// objetos consumibles tiempo limitado

	private Datos datos = new Datos();
	public static List<Mejora> mejoras = new ArrayList<>();
	public static List<Mejora> mejorasClicker = new ArrayList<>();

	public ProtoPizzaAPP() {

		// mejoras activas clickando
		// nombre / coste / incrementoCoste / umbralDesbloqueo / accion
		mejorasClicker.add(new Mejora("Experiencia del Chef", 18, 1.20, 0, accion -> datos.subirClicker(0.4)));
		mejorasClicker.add(new Mejora("Cuchillo bien afilado", 104, 1.21, 70, accion -> datos.subirClicker(0.9)));
		mejorasClicker.add(new Mejora("Manos de Maestro", 1_380, 1.23, 900, accion -> datos.subirClicker(4.5)));
		mejorasClicker.add(new Mejora("Contratar Cocineros", 288, 1.30, 200, accion -> datos.subirAutoClicker()));

		// mejoras pasivas autocloicker
		// nombre / coste / incrementoCoste / umbralDesbloqueo / accion
		mejoras.add(new Mejora("Tabla de Pizzería", 138, 1.21, 90, accion -> datos.subirNPS(0.5)));
		mejoras.add(new Mejora("Air Fryer", 575, 1.21, 380, accion -> datos.subirNPS(1.8)));
		mejoras.add(new Mejora("Horno de piedra", 1_955, 1.22, 1_300, accion -> datos.subirNPS(6)));
		mejoras.add(new Mejora("Horno doble", 6_325, 1.23, 4_200, accion -> datos.subirNPS(18)));
		mejoras.add(new Mejora("Horno industrial", 18_975, 1.24, 12_500, accion -> datos.subirNPS(55)));
		mejoras.add(new Mejora("Cinta automática", 55_200, 1.24, 36_000, accion -> datos.subirNPS(150)));
		mejoras.add(new Mejora("Amasadora automática", 155_250, 1.25, 100_000, accion -> datos.subirNPS(420)));
		mejoras.add(new Mejora("Fábrica de masa", 437_000, 1.25, 285_000, accion -> datos.subirNPS(1_200)));
		mejoras.add(new Mejora("Línea de producción", 1_207_500, 1.26, 800_000, accion -> datos.subirNPS(3_400)));
		mejoras.add(new Mejora("Central pizzera", 3_335_000, 1.26, 2_200_000, accion -> datos.subirNPS(9_500)));
		mejoras.add(new Mejora("Megafactoría", 9_430_000, 1.27, 6_200_000, accion -> datos.subirNPS(27_000)));
		mejoras.add(new Mejora("PizzaCorp", 26_450_000, 1.27, 17_000_000, accion -> datos.subirNPS(75_000)));
		mejoras.add(new Mejora("Red mundial de franquicias", 80_500_000, 1.29, 52_000_000,
				accion -> datos.subirNPS(220_000)));
		mejoras.add(new Mejora("Impresora 3D de pizzas", 241_500_000, 1.30, 160_000_000,
				accion -> datos.subirNPS(700_000)));

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new ProtoPizzaAPP().raiz();
			} catch (IOException e) {
				System.err.println("fallo en raiz");
				e.printStackTrace();
			}
		});
	}

	private void raiz() throws IOException {
		Interfaz interfaz = new Interfaz(datos, mejoras, mejorasClicker);
		interfaz.render();
		timer(interfaz);
	}

	// loop que se actualiza cada 0.015 segundos
	private void timer(Interfaz interfaz) {

		new Timer(15, ejecuta -> {
			datos.reloj(0.015);
			interfaz.render();
		}).start();
	}

}
