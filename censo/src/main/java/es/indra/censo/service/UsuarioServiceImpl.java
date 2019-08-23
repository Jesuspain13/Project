package es.indra.censo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.censo.dao.IUsuarioDao;
import es.indra.censo.model.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
	
	private Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	public List<Usuario> findUsuarioByName(String username) throws Exception {
		
		try {
			return usuarioDao.findUsuarioByName(username);
			
		}catch (Exception ex){
			
			log.error(ex.getMessage());
			throw new Exception(ex);
			
		}
		
	}

	

}
