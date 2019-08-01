package es.indra.censo.dao;

import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Complejo;

public interface IComplejoDao extends CrudRepository <Complejo, Integer> {

}
