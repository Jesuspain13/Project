package es.indra.censo.model.wrapper;

import java.util.Comparator;

import org.apache.logging.log4j.util.PropertySource;
import org.springframework.stereotype.Component;

import es.indra.censo.model.Puesto;


public class ComparadorPuestos<T> implements Comparator<T>{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(T o1, T o2) {
		Puesto p1 = (Puesto) o1;
		Puesto p2 = (Puesto) o2;
		double result;
		//delante -> atras
		if (p2.getValor() > p1.getValor()) {
			result = Math.floor(p1.getValor() - p2.getValor());
		} else {
			result = Math.ceil(p1.getValor() - p2.getValor());
		}

		return (int) (result);
	}
	
	

}
