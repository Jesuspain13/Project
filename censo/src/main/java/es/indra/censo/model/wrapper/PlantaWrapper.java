package es.indra.censo.model.wrapper;

import java.util.List;

import es.indra.censo.model.Puesto;

/**
 * Nos permite ordenar correctamente los puestos de una planta, teniendo en
 * cuenta que hay puestos con id alfanumérico
 * 
 * @author jjespana
 *
 */
public class PlantaWrapper extends PlantaWrapperAbs {

	private int incioValoresAlfanumericos = 273;

	public PlantaWrapper() {
		super();
	}

	@Override
	public List<Puesto> ordenarPuesto(String nombrePlanta, List<Puesto> puestosDesordenados)
			throws NoSorteableException {
		try {

			int value;
			for (int i = 1; i < puestosDesordenados.size() - 18; i++) {
				if (i > 1 && this.incioValoresAlfanumericos < 291) {
					value = Integer.parseInt(puestosDesordenados.get(i - 1).getIdPuesto());
					// si se cumple que la iteración actual es mayor que la anterior, entonces es
					// numérico
					if (value == 146 || value == 158 || value == 179 || value == 196 || value == 210 || value == 227
							|| value == 244 || value == 261 || value == 278 || value == 295 || value == 306
							|| value == 317 || value == 328 || value == 339 || value == 352 || value == 369
							|| value == 386 || value == 403) {

						this.añadirValoresAlfanumericos(puestosDesordenados, value, i);

					} else {
						super.addPuesto(puestosDesordenados.get(i));
					}
				} else {
					super.addPuesto(puestosDesordenados.get(i));
				}
			}

			return getPuestosOrdenados();

		} catch (Exception ex) {
			throw new NoSorteableException(
					String.format("la lista de puestos de la planta %s no se ha podido ordenar", nombrePlanta));
		}
	}

	private void añadirValoresAlfanumericos(List<Puesto> puestosDesordenados, int value, int i) {
		switch (value) {
		case 146:
			break;
		case 158:
			break;
		case 179:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 196:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 210:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 227:
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 244:
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 261:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 278:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 295:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 306:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 317:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 328:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 339:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 352:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 369:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 386:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		case 403:
			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
			this.incioValoresAlfanumericos++;
			super.addPuesto(puestosDesordenados.get(i));
			break;
		}
	}

}
