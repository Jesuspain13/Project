package es.indra.censo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Rol;

public interface IRolDao extends CrudRepository<Rol, Integer> {
	
	@Query("SELECT r FROM Rol r WHERE r.authority LIKE %?1%")
	public Rol findByAuthority(String authority);
	
	@Query("SELECT r FROM Rol r WHERE r NOT IN ?1")
	public List<Rol> findRolesNotEqualsToOtherRoles (List<Rol> roles);


}
