package es.indra.censo.model.wrapper;

import java.util.ArrayList;
import java.util.List;

import es.indra.censo.model.Puesto;

/**
 * Nos permite ordenar correctamente los puestos de una planta,
 * teniendo en cuenta que hay puestos con id alfanumérico
 * @author jjespana
 *
 */
public class PlantaWrapper extends PlantaWrapperAbs {

	
	public PlantaWrapper() {
		super();
	}
	
	@Override
	public List<Puesto> ordenarPuesto(List<Puesto> puestosDesordenados) {
		puestosDesordenados.remove(0);
		int valoresAlfanumericos = 273;
		 for(int i = 0; i< puestosDesordenados.size()-17; i++) {
			 //si se cumple que la iteración actual es mayor que la anterior, entonces es numérico
			
				if (getPuestosOrdenados().isEmpty() || getPuestosOrdenados().size() < 1) {
					super.addPuesto(puestosDesordenados.get(i));
				} else {
					if (puestosDesordenados.get(i).getIdPuesto().contains("A") || puestosDesordenados.get(i).getIdPuesto().contains("a")) {
						super.addPuesto(puestosDesordenados.get(valoresAlfanumericos));
						valoresAlfanumericos++;
					}
					 else if (Integer.parseInt(puestosDesordenados.get(i).getIdPuesto()) >
					Integer.parseInt(getPuestosOrdenados().get(i-1).getIdPuesto())) {
						super.addPuesto(puestosDesordenados.get(i));
					}
					//sino es que es alfanumérico
					else {
						super.addPuesto(puestosDesordenados.get(valoresAlfanumericos));
						valoresAlfanumericos++;
					}
				
		 }
		}
		
		System.out.println("");
		return getPuestosOrdenados();
	}
	
	

}
