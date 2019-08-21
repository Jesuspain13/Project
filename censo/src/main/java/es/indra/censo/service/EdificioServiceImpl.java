package es.indra.censo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.censo.dao.IEdificioDao;
import es.indra.censo.dao.IRegistroDao;
import es.indra.censo.model.Edificio;

@Service
public class EdificioServiceImpl implements IEdificioService {
	
	@Autowired
	private IEdificioDao edificioDao;

	@Override
	public Edificio findByIdEdificioAndRegistro(Integer idEdificio, Integer idRegistro) throws Exception {
		try {
			return edificioDao.findByIdAndRegistro(idEdificio, idRegistro);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

}
