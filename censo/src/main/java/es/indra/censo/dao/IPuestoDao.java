package es.indra.censo.dao;

import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Puesto;

public interface IPuestoDao extends CrudRepository <Puesto, Integer>{

}
