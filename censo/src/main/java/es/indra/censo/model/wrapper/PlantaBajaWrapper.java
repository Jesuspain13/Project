package es.indra.censo.model.wrapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.indra.censo.model.Puesto;

public class PlantaBajaWrapper extends PlantaWrapperAbs {

	public PlantaBajaWrapper() {
		super();
	}

	@Override
	public List<Puesto> ordenarPuesto(List<Puesto> puestosDesordenados) {
		Integer[] nums = { 31, 98, 160, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182,
				183, 184, 185, 186, 187 };
		Integer[] puestosZ = {9, 20, 32, 43, 54, 65, 76,87, 99, 111, 122, 133, 144, 155, 163, 164, 165, 166, 167};
		List<Integer> posicionesAlfanum = Arrays.asList(nums);

		int value = 0;
		int contador = 0; 
		boolean estaEnSalto;
		for (int i = 0; i < puestosDesordenados.size() - 21; i++) {
			//comprobar que la lista ya ordenada es mayor a 0
			if (super.getPuestosOrdenados().size() > 0) {
				int lastIndex = super.getPuestosOrdenados().size()-1;
				if (!super.getPuestosOrdenados().get(lastIndex).getIdPuesto().contains("A") ||
						!super.getPuestosOrdenados().get(lastIndex).getIdPuesto().contains("A"))
				value = Integer.parseInt(super.getPuestosOrdenados().get(lastIndex).getIdPuesto());
			} 
			//comprobar que el valor que vamos a coger no tiene z
			if (puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| puestosDesordenados.get(i).getIdPuesto().contains("z")) {
				//si tiene z pero el valor anterior es uno de estos valores
			 if(value == 3 || value == 9 || value == 15 || value == 21 || value == 27
					|| value == 33 || value == 39 || value == 45 || value == 51 || value == 63 || value == 69
					|| value == 75 || value == 81 || value == 87 || value == 93 || value == 99 || value == 105
					|| value == 111 || value == 117 || value == 123 || value == 129 || value == 135
					|| value == 141) {
				 this.añadirValoresAlfanumericos(puestosDesordenados, value, i);
			 }
			} else {
				//si el iterador es mayor que 1
			
				if (i > 1) {
					int anterior = super.getPuestosOrdenados().size()-1;
					//comprobar que el ultimo valor que inyectamos no tiene a
					if (!super.getPuestosOrdenados().get(anterior).getIdPuesto().contains("A") ||
							!super.getPuestosOrdenados().get(anterior).getIdPuesto().contains("A")) {
						
						value = Integer.parseInt(super.getPuestosOrdenados().get(anterior).getIdPuesto());
						
						// si se cumple que la iteración actual es mayor que la anterior, entonces es
						// numérico
					
						if (value == 3 || value == 9 || value == 15 || value == 21 || value == 27
								|| value == 33 || value == 39 || value == 45 || value == 51 || value == 63 || value == 69
								|| value == 75 || value == 81 || value == 87 || value == 93 || value == 99 || value == 105
								|| value == 111 || value == 117 || value == 123 || value == 129 || value == 135
								|| value == 141) {
	
							this.añadirValoresAlfanumericos(puestosDesordenados, value, i);
						} else {
							if (!posicionesAlfanum.contains(i) && (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
									|| !puestosDesordenados.get(i).getIdPuesto().contains("z"))) {
								super.addPuesto(puestosDesordenados.get(i));
							}
						}
					
					} //si tiene a se inyecta normal el sigiente desordenado
					else {
						
						// si no tiene z ese valor
						if (!posicionesAlfanum.contains(i) && (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
								|| !puestosDesordenados.get(i).getIdPuesto().contains("z"))) {
							super.addPuesto(puestosDesordenados.get(i));
						}
					}
				}else {
	
						super.addPuesto(puestosDesordenados.get(i));
				}
			}
		}
		System.out.println("");
		return getPuestosOrdenados();
	
		}
	
	private void añadirValoresAlfanumericos(List<Puesto> puestosDesordenados, int value, int i) {
		switch (value) {
		case 3:
			super.addPuesto(puestosDesordenados.get(31));
			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			
			break;
		case 9:
			super.addPuesto(puestosDesordenados.get(98));
			
			break;
		case 15:
			super.addPuesto(puestosDesordenados.get(160));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 21:
			super.addPuesto(puestosDesordenados.get(167));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 27:
			super.addPuesto(puestosDesordenados.get(168));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 33:

			super.addPuesto(puestosDesordenados.get(169));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 39:

			super.addPuesto(puestosDesordenados.get(170));

			
			break;
		case 45:
			super.addPuesto(puestosDesordenados.get(171));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 51:
			super.addPuesto(puestosDesordenados.get(172));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 57:
			super.addPuesto(puestosDesordenados.get(173));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 63:
			super.addPuesto(puestosDesordenados.get(174));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 69:
			super.addPuesto(puestosDesordenados.get(175));

			break;
		case 75:
			super.addPuesto(puestosDesordenados.get(176));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 81:
			super.addPuesto(puestosDesordenados.get(177));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 87:
			super.addPuesto(puestosDesordenados.get(178));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 93:
			super.addPuesto(puestosDesordenados.get(179));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 99:
			super.addPuesto(puestosDesordenados.get(180));

			
			break;
		case 105:
			super.addPuesto(puestosDesordenados.get(181));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 111:
			super.addPuesto(puestosDesordenados.get(182));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 117:
			super.addPuesto(puestosDesordenados.get(183));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 123:
			super.addPuesto(puestosDesordenados.get(184));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 129:
			super.addPuesto(puestosDesordenados.get(185));

			
			break;
		case 135:
			super.addPuesto(puestosDesordenados.get(186));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		case 141:
			super.addPuesto(puestosDesordenados.get(187));

			if (!puestosDesordenados.get(i).getIdPuesto().contains("Z")
					|| !puestosDesordenados.get(i).getIdPuesto().contains("z")){
				super.addPuesto(puestosDesordenados.get(i));
			}
			break;
		}
	}

}
