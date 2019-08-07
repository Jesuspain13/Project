package es.indra.censo.model.wrapper;

import java.util.ArrayList;
import java.util.List;

import es.indra.censo.model.Puesto;

public abstract class PlantaWrapperAbs {
	
	private List<Puesto> puestosOrdenados;
	
	//planta 0 = desde 1 hasta 189
	// planta 1 = desde 1 hasta 289 (16 puestos alfanumericos)
	int puestosTotales;
	
	public PlantaWrapperAbs() {
		this.puestosOrdenados = new ArrayList<Puesto>();
	}
	
	public abstract List<Puesto> ordenarPuesto(List<Puesto> puestosDesordenados);

	public List<Puesto> getPuestosOrdenados() {
		return puestosOrdenados;
	}

	public void setPuestosOrdenados(List<Puesto> puestosOrdenados) {
		this.puestosOrdenados = puestosOrdenados;
	}

	public int getPuestosTotales() {
		return puestosTotales;
	}

	public void setPuestosTotales(int puestosTotales) {
		this.puestosTotales = puestosTotales;
	}

	public void addPuesto(Puesto p) {
		this.puestosOrdenados.add(p);
	}
	
}
