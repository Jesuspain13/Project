package es.indra.censo.model.wrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.indra.censo.model.Puesto;

public class PlantaBajaWrapper extends PlantaWrapperAbs {
	Integer[] nums = { 31, 98, 160, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183,
			184, 185, 186, 187 };
	List<Integer> posicionesAlfanum = Arrays.asList(nums);
	List<Integer> puestosPreviosAAlfanumericos = Arrays.asList(3, 9, 15, 21, 27, 33, 39, 45, 51, 57, 63, 69, 75, 81, 87,
			93, 99, 105, 111, 117, 123, 129, 135, 141);

	private List<Puesto> plantaAzahara;

	public PlantaBajaWrapper() {
		super();
		this.plantaAzahara = new ArrayList<Puesto>();
	}

	@Override
	public List<Puesto> ordenarPuesto(String nombrePlanta, List<Puesto> puestosDesordenados)
			throws NoSorteableException {

		try {

			Puesto iteraciónPuestoDesordenado;
			Puesto ultimoPuestoOrdenado;
			int value = 0;

			for (int i = 0; i < puestosDesordenados.size() - 21; i++) {
				iteraciónPuestoDesordenado = puestosDesordenados.get(i);

				// si no contiene Z
				// comprobar que la lista ya ordenada es mayor a 0
				if (super.getPuestosOrdenados().size() > 0) {
					int lastIndex = super.getPuestosOrdenados().size() - 1;
					// comprobar si el anterior valor guardado en lista ordenada tiene A
					ultimoPuestoOrdenado = super.getPuestosOrdenados().get(lastIndex);
					if (!ultimoPuestoOrdenado.getIdPuesto().contains("A")
							|| !ultimoPuestoOrdenado.getIdPuesto().contains("A")) {
						value = Integer.parseInt(ultimoPuestoOrdenado.getIdPuesto());
					}
					// comprobar que el valor que vamos a coger no tiene z
					if (iteraciónPuestoDesordenado.getIdPuesto().contains("Z")
							|| iteraciónPuestoDesordenado.getIdPuesto().contains("z")) {
						this.plantaAzahara.add(puestosDesordenados.get(i));
						// si tiene z pero el id del utlimo puesto añadido es uno de estos valores

						if (this.puestosPreviosAAlfanumericos.contains(value)) {
							this.añadirValoresAlfanumericos(puestosDesordenados, value, i);

						}

					} else {

						if (this.puestosPreviosAAlfanumericos.contains(value)) {
							this.añadirValoresAlfanumericos(puestosDesordenados, value, i);

						} else {
							this.añadirSiNoTieneZ(puestosDesordenados, i);
						}
					}
				} else {
					this.añadirSiNoTieneZ(puestosDesordenados, i);
				}

				value = 0;
			}
			System.out.println("");
			return getPuestosOrdenados();
		} catch (Exception ex) {
			throw new NoSorteableException(
					String.format("la lista de puestos de la planta %s no se ha podido ordenar", nombrePlanta));
		}
	}

	/**
	 * método para en caso de que el puesto en la posición i no tenga Z se añade a
	 * la lista
	 * 
	 * @param puestosDesordenados
	 * @param value
	 * @param i
	 */
	private void añadirSiNoTieneZ(List<Puesto> puestosDesordenados, int i) {
		if (!posicionesAlfanum.contains(i) && (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
				|| !puestosDesordenados.get(i).getIdPuesto().contains("z"))) {
			super.addPuesto(puestosDesordenados.get(i));
		}
	}

	private void añadirValoresAlfanumericos(List<Puesto> puestosDesordenados, int value, int i) {
		switch (value) {
		case 3:
			super.addPuesto(puestosDesordenados.get(31));
			this.añadirSiNoTieneZ(puestosDesordenados, i);

			break;
		case 9:
			super.addPuesto(puestosDesordenados.get(98));

			break;
		case 15:
			super.addPuesto(puestosDesordenados.get(160));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 21:
			super.addPuesto(puestosDesordenados.get(167));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 27:
			super.addPuesto(puestosDesordenados.get(168));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 33:

			super.addPuesto(puestosDesordenados.get(169));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 39:

			super.addPuesto(puestosDesordenados.get(170));

			break;
		case 45:
			super.addPuesto(puestosDesordenados.get(171));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 51:
			super.addPuesto(puestosDesordenados.get(172));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 57:
			super.addPuesto(puestosDesordenados.get(173));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 63:
			super.addPuesto(puestosDesordenados.get(174));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 69:
			super.addPuesto(puestosDesordenados.get(175));

			break;
		case 75:
			super.addPuesto(puestosDesordenados.get(176));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 81:
			super.addPuesto(puestosDesordenados.get(177));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 87:
			super.addPuesto(puestosDesordenados.get(178));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 93:
			super.addPuesto(puestosDesordenados.get(179));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 99:
			super.addPuesto(puestosDesordenados.get(180));

			break;
		case 105:
			super.addPuesto(puestosDesordenados.get(181));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 111:
			super.addPuesto(puestosDesordenados.get(182));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 117:
			super.addPuesto(puestosDesordenados.get(183));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 123:
			super.addPuesto(puestosDesordenados.get(184));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 129:
			super.addPuesto(puestosDesordenados.get(185));

			break;
		case 135:
			super.addPuesto(puestosDesordenados.get(186));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		case 141:
			super.addPuesto(puestosDesordenados.get(187));

			this.añadirSiNoTieneZ(puestosDesordenados, i);
			break;
		}
	}

	public List<Puesto> getPlantaAzahara() {
		return plantaAzahara;
	}

	public void setPlantaAzahara(List<Puesto> plantaAzahara) {
		this.plantaAzahara = plantaAzahara;
	}
	


}
