package es.indra.censo.dao;

import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer> {

	public Usuario findByUsername(String name);

}
