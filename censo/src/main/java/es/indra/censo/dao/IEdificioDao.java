package es.indra.censo.dao;

import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Edificio;

public interface IEdificioDao extends CrudRepository <Edificio, Integer> {

}
