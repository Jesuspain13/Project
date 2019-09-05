package es.indra.censo.model.wrapper;

import java.util.ArrayList;
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


	public PlantaWrapper() {
		super();
	}
		
	@Override
	public List<Puesto> decidirTipoPuesto(List<Puesto> puestosDesordenados) {
		List<Puesto> puestosFiltrados = new ArrayList<Puesto>();
		for (Puesto p: puestosDesordenados) {

			if (!p.getIdPuesto().contains("D") && !p.getIdPuesto().contains("147") &&
					!p.getIdPuesto().contains("159") && !p.getIdPuesto().contains("213A") &&
					!p.getIdPuesto().contains("230A")) {
				p.calcularValor();
				puestosFiltrados.add(p);
			} 

		}
		return puestosFiltrados;
		
		}

}
