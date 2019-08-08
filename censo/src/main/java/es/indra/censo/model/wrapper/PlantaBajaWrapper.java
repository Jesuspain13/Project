package es.indra.censo.model.wrapper;

import java.util.List;

import es.indra.censo.model.Puesto;

public class PlantaBajaWrapper extends PlantaWrapperAbs {

	public PlantaBajaWrapper() {
		super();
	}

	@Override
	public List<Puesto> ordenarPuesto(List<Puesto> puestosDesordenados) {

		int valoresAlfanumericos = 273;
		int value;
		boolean estaEnSalto;
		for (int i = 1; i < puestosDesordenados.size() - 17; i++) {
			if (puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| puestosDesordenados.get(i).getIdPuesto().contains("z")) {

			} else if (i > 1 && valoresAlfanumericos < 285) {
				value = Integer.parseInt(puestosDesordenados.get(i - 1).getIdPuesto());
				// si se cumple que la iteración actual es mayor que la anterior, entonces es
				// numérico
				if (value == 3 || value == 9 || value == 15 || value == 21 || value == 27 || value == 33 || value == 39
						|| value == 45 || value == 51 || value == 63 || value == 69 || value == 75 || value == 81
						|| value == 87 || value == 93 || value == 99 || value == 105 || value == 111 || value == 117
						|| value == 123 || value == 129 || value == 135 || value == 141) {

					switch (value) {
					case 3:
						super.addPuesto(puestosDesordenados.get(31));
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 9:
						super.addPuesto(puestosDesordenados.get(98));
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 15:
						super.addPuesto(puestosDesordenados.get(160));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 21:
						super.addPuesto(puestosDesordenados.get(167));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 27:
						super.addPuesto(puestosDesordenados.get(168));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 33:
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(169));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 39:
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(170));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 45:
						super.addPuesto(puestosDesordenados.get(171));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 51:
						super.addPuesto(puestosDesordenados.get(172));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 57:
						super.addPuesto(puestosDesordenados.get(173));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 63:
						super.addPuesto(puestosDesordenados.get(174));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 69:
						super.addPuesto(puestosDesordenados.get(175));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 75:
						super.addPuesto(puestosDesordenados.get(176));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 81:
						super.addPuesto(puestosDesordenados.get(177));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 87:
						super.addPuesto(puestosDesordenados.get(178));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 93:
						super.addPuesto(puestosDesordenados.get(179));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 99:
						super.addPuesto(puestosDesordenados.get(180));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 105:
						super.addPuesto(puestosDesordenados.get(181));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 111:
						super.addPuesto(puestosDesordenados.get(182));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 117:
						super.addPuesto(puestosDesordenados.get(183));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 123:
						super.addPuesto(puestosDesordenados.get(184));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 129:
						super.addPuesto(puestosDesordenados.get(185));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 135:
						super.addPuesto(puestosDesordenados.get(186));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					case 141:
						super.addPuesto(puestosDesordenados.get(187));
						valoresAlfanumericos++;
						super.addPuesto(puestosDesordenados.get(i));
						break;
					}

				} else {
					super.addPuesto(puestosDesordenados.get(i));
				}
			} else {
				super.addPuesto(puestosDesordenados.get(i));
			}
		}

		System.out.println("");
		return getPuestosOrdenados();
	}

}
