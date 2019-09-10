package es.indra.censo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IRegistroDao;
import es.indra.censo.model.Registro;

@Service
public class RegistroService implements IRegistroService {

	private Logger log = LoggerFactory.getLogger(RegistroService.class);

	@Autowired
	private IRegistroDao registroDao;

	@Override
	public List<Registro> findAll() throws Exception {
		try {
			return (List<Registro>) registroDao.findAll();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public Registro save(Registro registro) throws Exception {
		try {
			return registroDao.save(registro);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}

	}

	@Override
	public Registro findRegistroById(Integer id) throws Exception {
		try {
			return registroDao.findById(id).get();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public Registro findRegistroByVersion(String version) throws Exception {
		try {
			return registroDao.findByVersion(version);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public Registro findByIdWithJoinFetch(Integer id) throws Exception {
		try {
			return registroDao.findByIdWithJoinFetch(id);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}
	
	@Override
	public void deleteRegistroById(Integer id) throws Exception {
		try {
			registroDao.deleteById(id);;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public Page<Registro> findAll(Pageable pageable) {
		
		return registroDao.findAll(pageable);
	}

}
