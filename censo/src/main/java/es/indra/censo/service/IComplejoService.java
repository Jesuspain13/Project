package es.indra.censo.service;

import es.indra.censo.model.Complejo;

public interface IComplejoService {

	public Complejo findByIdAndRegistro(Integer idComplejo, Integer idRegistro);
}
