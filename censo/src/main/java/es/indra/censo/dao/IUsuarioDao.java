package es.indra.censo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.indra.censo.model.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer> {
	
	public static final String FIND_USERS = "SELECT id,username FROM public.usuarios where username like '%?1%'";

	public Usuario findByUsername(String name);
	
	@Query("SELECT u.id, u.username, u.enabled FROM Usuario u WHERE u.username LIKE CONCAT('%',:username,'%')")
	public List<List<Object>> findUsuarioByName(@Param("username") String username);

}
