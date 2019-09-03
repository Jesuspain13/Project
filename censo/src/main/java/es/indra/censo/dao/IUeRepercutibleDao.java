package es.indra.censo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.indra.censo.model.UeRepercutible;

public interface IUeRepercutibleDao extends JpaRepository<UeRepercutible, Integer> {
	
	@Query("SELECT ue FROM UeRepercutible ue WHERE ue.registro.idRegistro = ?1")
	public List<UeRepercutible> findAllByIdRegistro(Integer idRegistro);

}
