package es.indra.censo.service;

import org.springframework.data.jpa.repository.Query;

import es.indra.censo.model.Complejo;

public interface IComplejoService {

	public Complejo findByIdAndRegistro(Integer idComplejo, Integer idRegistro) throws Exception;

	public Complejo findByIdAndRegistroWithJoinFetch(Integer idComplejo, Integer idRegistro) throws Exception;
}
