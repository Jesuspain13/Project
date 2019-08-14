package es.indra.censo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.indra.censo.model.Registro;

@Repository
public interface IRegistroDao extends CrudRepository <Registro, Integer>{
	
	public Registro findByVersion(String version);

}
