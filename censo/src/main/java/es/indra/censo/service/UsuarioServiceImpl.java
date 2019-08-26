package es.indra.censo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IUsuarioDao;
import es.indra.censo.model.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
	
	private Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private IUsuarioDao usuarioDao;

//	@Override
//	public List<Usuario> findUsuarioByName(String username) throws Exception {
//		
//		try {
//			List user = usuarioDao.findUsuarioByName(username);
//			Iterator userIt = user.iterator();
//			List<Usuario> usuarios = new ArrayList<Usuario>();
//			
//			while(userIt.hasNext()) {
//				List<Object> usuarioSinFormar = (List<Object>) userIt.next();
//				Usuario usuarioFormado = new Usuario();
//				usuarioFormado.setId((Integer)usuarioSinFormar.get(0));
//				usuarioFormado.setUsername(usuarioSinFormar.get(1).toString());
//				usuarioFormado.setEnabled((boolean) usuarioSinFormar.get(2));
//				usuarios.add(usuarioFormado);
//			}
//			return usuarios;
//			
//		}catch (Exception ex){
//			
//			log.error(ex.getMessage());
//			throw new Exception(ex);
//			
//		}
//		
//	}
	
	@Override
	public List<Usuario> findUsuarioByName(String username) throws Exception {
		
		try {
			List<Usuario> users = usuarioDao.findFullUsuarioByName(username);
		
			for (Usuario u: users) {
				u.setPassword(null);
			}
			return users;
			
		}catch (Exception ex){
			log.error(ex.getMessage());
			throw new Exception(ex);
			
		}
		
	}

	/**
	 * establece el atributo enabled a false
	 */
	@Override
	@Transactional
	public void deleteUsuarioById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		try {
			Usuario user = usuarioDao.findById(id).get();
			user.setEnabled(false);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
 		
	}

	

}
