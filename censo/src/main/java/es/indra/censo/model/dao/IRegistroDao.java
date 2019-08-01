package es.indra.censo.model.dao;

import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Registro;

public interface IRegistroDao extends CrudRepository <Registro, Integer>{

}
