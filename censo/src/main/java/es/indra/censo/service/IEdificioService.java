package es.indra.censo.service;

import es.indra.censo.model.Edificio;

public interface IEdificioService {

	public Edificio findByIdEdificioAndRegistro(Integer idEdificio, Integer idRegistro);
}
