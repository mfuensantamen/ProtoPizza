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

		// ===== MEJORAS CLICKER (ACTIVAS) =====
		// nombre / coste / incrementoCoste / umbralDesbloqueo / accion

		mejorasClicker.add(new Mejora("Contratar Cocineros", 120, 1.18, 100, accion -> datos.subirAutoClicker()));
		mejorasClicker.add(new Mejora("Experiencia del Chef", 10, 1.12, 0, accion -> datos.subirClicker(0.5)));
		mejorasClicker.add(new Mejora("Cuchillo bien afilado", 80, 1.12, 50, accion -> datos.subirClicker(1.0)));
		mejorasClicker.add(new Mejora("Manos de Maestro", 1_500, 1.15, 1_000, accion -> datos.subirClicker(6.0)));

		// ===== MEJORAS PASIVAS (NPS) =====
		// nombre / coste / incrementoCoste / umbralDesbloqueo / accion

		mejoras.add(new Mejora("Tabla de Pizzería", 200, 1.12, 150, accion -> datos.subirNPS(0.8)));
		mejoras.add(new Mejora("Air Fryer", 650, 1.12, 500, accion -> datos.subirNPS(2.5)));
		mejoras.add(new Mejora("Horno de piedra", 1_800, 1.14, 1_200, accion -> datos.subirNPS(7.5)));
		mejoras.add(new Mejora("Horno doble", 6_000, 1.15, 4_500, accion -> datos.subirNPS(25)));
		mejoras.add(new Mejora("Horno industrial", 18_000, 1.15, 15_000, accion -> datos.subirNPS(85)));
		mejoras.add(new Mejora("Cinta automática", 55_000, 1.16, 45_000, accion -> datos.subirNPS(260)));
		mejoras.add(new Mejora("Amasadora automática", 150_000, 1.16, 120_000, accion -> datos.subirNPS(750)));
		mejoras.add(new Mejora("Fábrica de masa", 450_000, 1.17, 350_000, accion -> datos.subirNPS(2200)));
		mejoras.add(new Mejora("Línea de producción", 1_200_000, 1.17, 900_000, accion -> datos.subirNPS(6000)));
		mejoras.add(new Mejora("Central pizzera", 3_200_000, 1.18, 2_500_000, accion -> datos.subirNPS(16000)));
		mejoras.add(new Mejora("Megafactoría", 9_000_000, 1.18, 7_000_000, accion -> datos.subirNPS(45000)));
		mejoras.add(new Mejora("PizzaCorp", 25_000_000, 1.19, 20_000_000, accion -> datos.subirNPS(130000)));
		mejoras.add(new Mejora("Red mundial de franquicias", 75_000_000, 1.19, 60_000_000,
				accion -> datos.subirNPS(400000)));
		mejoras.add(new Mejora("Impresora 3D de pizzas", 220_000_000, 1.20, 180_000_000,
				accion -> datos.subirNPS(1_200_000)));

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
