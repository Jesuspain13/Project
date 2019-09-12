package es.indra.censo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.indra.censo.model.errores.excel.Fila;

public interface IFilasDao extends PagingAndSortingRepository<Fila, Integer> {
	
	@Query(value= "SELECT e FROM Fila e WHERE e.registro.idRegistro = ?1",
			countQuery= "SELECT COUNT(e) FROM Fila e WHERE e.registro.idRegistro = ?1")
	public Page<Fila> findAllByIdRegistro(Integer idRegistro, Pageable pageable);

}
