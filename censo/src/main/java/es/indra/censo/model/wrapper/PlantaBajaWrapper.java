package es.indra.censo.model.wrapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.indra.censo.model.Puesto;

@Component
public class PlantaBajaWrapper extends PlantaWrapperAbs {
	
	private List<Puesto> puestosAzahar = new ArrayList<Puesto>();


	public PlantaBajaWrapper() {
		super();
	}

	/**
	 * Método que devuelve la lista de puestos de la planta azahar si ya está ordenada 
	 * y, si no lo está, ejecuta el método que la ordena
	 * @param puestosPlanta
	 * @return
	 * @throws NoSorteableException
	 */
	public List<Puesto> recuperarPuestosAzahar(List<Puesto> puestosPlanta) throws NoSorteableException {
		try {
			this.puestosAzahar.clear();
			this.decidirTipoPuesto(puestosPlanta);
			return this.puestosAzahar;
		} catch (Exception ex) {
			throw new NoSorteableException(ex.getMessage());
		}
	}
	
	/**
	 * Recibe el nombre del puesto y comprueba si contiene Z / A o es normal(numerico)
	 * @param idPuesto
	 */
	@Override
	public List<Puesto> decidirTipoPuesto(List<Puesto> puestosDesordenados) {
		this.puestosAzahar.clear();
		String idPuesto;
		List<Puesto> puestosAMostrar = new ArrayList<Puesto>();
		for (Puesto p: puestosDesordenados) {
			//nombre del puesto en cada iteración
			idPuesto = p.getIdPuesto();
			if (idPuesto.contains("Z") || idPuesto.contains("z")) {
				this.puestosAzahar.add(p);
			} else {
				p.calcularValor();
				puestosAMostrar.add(p);
			}
		}
		return puestosAMostrar;
	}

	public List<Puesto> getPuestosAzahar() {
		return puestosAzahar;
	}

	public void setPuestosAzahar(List<Puesto> puestosAzahar) {
		this.puestosAzahar = puestosAzahar;
	}
	

	

	
	
	
}
