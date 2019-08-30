package es.indra.censo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import es.indra.censo.dao.IRolDao;
import es.indra.censo.model.Rol;
import es.indra.censo.model.Usuario;

@Service
public class RolServiceImpl implements IRolService {
	
	Logger log = LoggerFactory.getLogger(RolServiceImpl.class);
	
	@Autowired
	private IRolDao rolDao;

	@Override
	public List<Rol> encontrarRolesQueNoTiene(Usuario user) throws  InvalidDataAccessResourceUsageException, Exception {
		try {
			if (user.getRoles() == null || user.getRoles().size() < 1) {
				return (List<Rol>) rolDao.findAll();
			}
			return rolDao.findRolesNotEqualsToOtherRoles (user.getRoles());
			
		} catch(InvalidDataAccessResourceUsageException ex) {
			log.error(ex.getMessage());
			return new ArrayList<Rol>();
		} catch(Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}
}
