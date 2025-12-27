package protopito;

import java.util.function.Consumer;

public class Mejora {

	private Consumer<Datos> accion;

	protected String nombre;
	protected int nivel;
	protected double coste;

	protected double incrCoste;
	protected double requisitoDesbloqueo;

	// constructor para pasivas y activas
	public Mejora(String nombre, double coste, double incrCoste, double requisitoDesbloqueo, Consumer<Datos> accion) {
		this.nombre = nombre;
		this.nivel = 0;
		this.coste = coste;
		this.incrCoste = incrCoste;
		this.requisitoDesbloqueo = requisitoDesbloqueo;
		this.accion = accion;
	}

	public boolean comprar(Datos datos) {
		if (!datos.verificarCompra(coste)) {
			return false;
		}

		datos.gastar(coste);
		nivel++;

		if (accion != null)
			accion.accept(datos);

		coste *= incrCoste;
		return true;
	}

	public boolean desbloquado(double numActual) {
		return numActual >= requisitoDesbloqueo;
	}

	public double getIncrCoste() {
		return incrCoste;
	}

	public void setIncrCoste(double incrCoste) {
		this.incrCoste = incrCoste;
	}

	public String getNombre() {
		return nombre;
	}

	public int getNivel() {
		return nivel;
	}

	public double getCoste() {
		return coste;
	}

	public void setCoste(double coste) {
		this.coste = coste;
	}

}
