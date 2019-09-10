package es.indra.censo.model.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.indra.censo.model.Puesto;

/**
 * Nos permite ordenar correctamente los puestos de una planta, teniendo en
 * cuenta que hay puestos con id alfanumérico
 * 
 * @author jjespana
 *
 */
@Component
public class PlantaWrapper extends PlantaWrapperAbs {

	public PlantaWrapper() {
		super();
	}

	@Override
	public List<Puesto> decidirTipoPuesto(List<Puesto> puestosDesordenados) {
		List<Puesto> puestosFiltrados = new ArrayList<Puesto>();
		for (Puesto p : puestosDesordenados) {

			p.calcularValor();
			puestosFiltrados.add(p);

		}
		return puestosFiltrados;

	}

}
