package es.indra.censo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import es.indra.censo.model.errores.excel.TipoError;

public interface ITiposErroresDao extends JpaRepository<TipoError, Integer> {

}
