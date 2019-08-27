package es.indra.censo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.censo.dao.IEdificioDao;
import es.indra.censo.dao.IPlantaDao;
import es.indra.censo.dao.IRegistroDao;
import es.indra.censo.model.Edificio;

@Service
public class EdificioServiceImpl implements IEdificioService {

	private Logger log = LoggerFactory.getLogger(EdificioServiceImpl.class);

	@Autowired
	private IEdificioDao edificioDao;
	
	@Autowired
	private IPlantaDao plantaDao;

	@Override
	public Edificio findByIdEdificioAndRegistro(Integer idEdificio, Integer idRegistro) throws Exception {
		try {
			return edificioDao.findByIdAndRegistro(idEdificio, idRegistro);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

}
