package protopito;

public class Datos {

	private boolean autoClickerPulsado = false;
	private double num = 250; // numero inicial
	private double nps = 0; // numeros/s iniciales
	private double recordMaximo = 0;
	private double clickIncremento = 1;
	private double periodoInicial = 1.0; // el clicker clickara automaticamente cada 4 segundos
	private double periodoAutoClicker = periodoInicial;
	private double contadorAutoClicker = 0.0; // cuenta el tiempo que pasa desde ultimo clik
	private double decrementoNivel = 0.033;
	private double periodoMinimo = 0.05;
	private int nivelAutoClicker = 0;

	public void subirAutoClicker() {
		nivelAutoClicker++;

		double calculado = periodoInicial - (nivelAutoClicker - 1) * decrementoNivel;

		periodoAutoClicker = calculado;
		if (periodoAutoClicker < periodoMinimo) {
			periodoAutoClicker = periodoMinimo;
		}
	}

	public void click() {
		num += clickIncremento + nps / 50;
		if (num > recordMaximo) {
			recordMaximo = num;
		}
	}

	// numeros actuales + numeros + numeros/s * 0.025 (la frecuencoia de
	// actualizacion)
	public void reloj(double diferenciaTiempo) {

		num += nps * diferenciaTiempo;
		if (num > recordMaximo) {
			recordMaximo = num;
		}
		if (nivelAutoClicker == 0) {
			return;
		}

		contadorAutoClicker += diferenciaTiempo;

		while (contadorAutoClicker >= periodoAutoClicker) {
			this.click();
			autoClickerPulsado = true;
			contadorAutoClicker -= periodoAutoClicker;
		}

	}

	public boolean autoCickerPulsado() {

		boolean pulsacion = autoClickerPulsado;
		autoClickerPulsado = false;
		return pulsacion;
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

	// getters setters
	public double getMaximo() {
		return recordMaximo;
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

	public double getPeriodoAutoClicker() {
		return periodoAutoClicker;
	}

	public void setPeriodoAutoClicker(double periodoAutoClicker) {
		this.periodoAutoClicker = periodoAutoClicker;
	}

	public int getNivelAutoClicker() {
		return nivelAutoClicker;
	}

	public void setNivelAutoClicker(int nivelAutoClicker) {
		this.nivelAutoClicker = nivelAutoClicker;
	}

}
