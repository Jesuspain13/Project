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
	public List<Registro> findAll() throws Exception {
		try {
		return (List<Registro>) registroDao.findAll();
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	@Transactional
	public Registro save(Registro registro) throws Exception {
		try {
		return registroDao.save(registro);
		} catch (Exception ex) {
			throw new Exception(ex);
		}

	}

	@Override
	public Registro findRegistroById(Integer id) throws Exception {
		try {
		return registroDao.findById(id).get();
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}
	
	@Override
	public Registro findRegistroByVersion(String version) throws Exception {
		try {
		return registroDao.findByVersion(version);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public Registro findByIdWithJoinFetch(Integer id) throws Exception {
		try {
		return registroDao.findByIdWithJoinFetch(id);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}
	
}
