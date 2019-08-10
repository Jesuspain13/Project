package es.indra.censo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IRegistroDao;
import es.indra.censo.model.Registro;

@Service
public class RegistroService implements IRegistroService {

	@Autowired
	private IRegistroDao registroDao;

	@Override
	public List<Registro> findAll() {
		return (List<Registro>) registroDao.findAll();
	}

	@Override
	@Transactional
	public Registro save(Registro registro) {
		return registroDao.save(registro);

	}

	@Override
	@Transactional(readOnly = true)
	public Registro findRegistroById(Integer id) {
		return registroDao.findById(id).get();
	}

}
