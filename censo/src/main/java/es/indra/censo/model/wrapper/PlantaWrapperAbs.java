package es.indra.censo.model.wrapper;

import java.util.ArrayList;
import java.util.List;

import es.indra.censo.model.Puesto;

public abstract class PlantaWrapperAbs {

	private List<Puesto> puestosOrdenados;


	public PlantaWrapperAbs() {
		this.puestosOrdenados = new ArrayList<Puesto>();
	}

	public List<Puesto> ordenarPuesto(String nombrePlanta, List<Puesto> puestosDesordenados)
			throws NoSorteableException {
		try {
			return this.ordenarListaPuestos(puestosDesordenados);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NoSorteableException(
					String.format("la lista de puestos de la planta %s no se ha podido ordenar", nombrePlanta));
		}
	}
	
	public abstract List<Puesto> decidirTipoPuesto(List<Puesto> puestosDesordenados);
	
	public List<Puesto> ordenarListaPuestos(List<Puesto> puestosDesordenados) {
		try {
			List<Puesto> result;
			//recorre la lista y separa los puestos de la vista de los de otra planta aunque esten en el excel
			result = this.decidirTipoPuesto(puestosDesordenados);
			result.sort(new ComparadorPuestos<Puesto>());
			return result;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<Puesto> getPuestosOrdenados() {
		return puestosOrdenados;
	}

	public void setPuestosOrdenados(List<Puesto> puestosOrdenados) {
		this.puestosOrdenados = puestosOrdenados;
	}

	public void addPuesto(Puesto p) {
		this.puestosOrdenados.add(p);
	}
}
