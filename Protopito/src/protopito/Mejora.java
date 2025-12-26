package protopito;

/**
 * ✅ REFAC: Antes tenías 2 clases: - Mejora (subía NPS) - MejoraClicker (subía
 * el incremento por click)
 *
 * Esto te obligaba a crear clases nuevas cuando cambiaban atributos/efectos.
 * Ahora "Mejora" también puede ser de tipo CLICK o NPS.
 *
 * La UI y Protopito sólo trabajan con "Mejora".
 */
public class Mejora {

	// ✅ NUEVO: tipo de mejora (qué estadística toca al comprar)
	public enum Tipo {
		NPS, // mejora pasiva: nums por segundo
		CLICK // mejora activa: nums por click
	}

	protected String nombre;
	protected int nivel;
	protected double coste;

	// ✅ CAMBIO: "incrNPS" ahora es "incremento" genérico.
	// - Si tipo == NPS -> suma a nps
	// - Si tipo == CLICK -> suma a clickIncremento
	protected double incremento;

	protected double incrCoste;
	protected double requisitoDesbloqueo;

	// ✅ NUEVO: guardamos el tipo en la mejora
	protected Tipo tipo;

	public Mejora() {
		// ✅ NUEVO: por defecto, una mejora "normal" es de NPS
		this.tipo = Tipo.NPS;
	}

	// ✅ NUEVO: constructor universal (sirve para CLICK y para NPS)
	public Mejora(String nombre, double coste, double incremento, double incrCoste, double requisitoDesbloqueo,
			Tipo tipo) {
		this.nombre = nombre;
		this.nivel = 1;
		this.coste = coste;
		this.incremento = incremento;
		this.incrCoste = incrCoste;
		this.requisitoDesbloqueo = requisitoDesbloqueo;
		this.tipo = (tipo == null) ? Tipo.NPS : tipo;
	}

	public Mejora(String nombre, double coste, double incrNPS, double incrCoste, double requisitoDesbloqueo) {
		this(nombre, coste, incrNPS, incrCoste, requisitoDesbloqueo, Tipo.NPS);
	}

	public boolean desbloquado(double numActual) {
		return numActual >= requisitoDesbloqueo;
	}

	public boolean comprar(Datos datos) {
		if (!datos.verificarCompra(coste)) {
			return false;
		}

		datos.gastar(coste);
		nivel++;

		if (tipo == Tipo.CLICK) {
			datos.subirClicker(incremento);
		} else {
			datos.subirNPS(incremento);
		}

		coste *= incrCoste;
		return true;
	}

	public double getIncremento() {
		return incremento;
	}

	public void setIncremento(double incremento) {
		this.incremento = incremento;
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

	public Tipo getTipo() {
		return tipo;
	}
}
