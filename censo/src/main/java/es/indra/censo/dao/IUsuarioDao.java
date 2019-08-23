package es.indra.censo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer> {

	public Usuario findByUsername(String name);
	
	@Query("SELECT u FROM Usuario u WHERE u.username like %?1%")
	public List<Usuario> findUsuarioByName(String username);

}
