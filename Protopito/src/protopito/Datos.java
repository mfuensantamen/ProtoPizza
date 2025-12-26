package protopito;

public class Datos {

	private double num = 0; // numero inicial
	private double nps = 0; // numeros/s iniciales
	private double recordMaximo = 0;
	private double clickIncremento = 1;

	public void click() {
		num += clickIncremento + nps / 50;
		if (num > recordMaximo) {
			recordMaximo = num;
		}
	}

	public void setClickIncremento(double click) {
		this.clickIncremento = click;
	}

	public double getClickIncremento() {
		return clickIncremento;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public double getNps() {
		return nps;
	}

	public void setNps(double nps) {
		this.nps = nps;
	}

	// numeros actuales + numeros + numeros/s * 0.025 (la frecuencoia de
	// actualizacion)
	public void reloj(double diferenciaTiempo) {
		num += nps * diferenciaTiempo;
		if (num > recordMaximo) {
			recordMaximo = num;
		}
	}

	public double getMaximo() {
		return recordMaximo;
	}

	public boolean verificarCompra(double coste) {
		return num >= coste;
	}

	public void subirClicker(double incremento) {
		clickIncremento += incremento;
	}

	public void subirNPS(double incremento) {
		nps += incremento;
	}

	public void gastar(double cantidad) {
		num -= cantidad;
	}

}
