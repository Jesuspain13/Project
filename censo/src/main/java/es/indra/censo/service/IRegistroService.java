package es.indra.censo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.indra.censo.model.Registro;

public interface IRegistroService {

	public List<Registro> findAll() throws Exception;
	
	public Page<Registro> findAll(Pageable pageable);

	public Registro save(Registro registro) throws Exception;

	public Registro findRegistroById(Integer id) throws Exception;

	public Registro findRegistroByVersion(String version) throws Exception;

	public Registro findByIdWithJoinFetch(Integer id) throws Exception;

	public void deleteRegistroById(Integer id) throws Exception;
}
