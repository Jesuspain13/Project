package es.indra.censo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.censo.dao.IComplejoDao;
import es.indra.censo.dao.IRegistroDao;
import es.indra.censo.model.Complejo;
import es.indra.censo.model.Registro;

@Service
public class ComplejoServiceImpl implements IComplejoService {

	@Autowired
	private IComplejoDao complejoDao;

	
	@Override
	public Complejo findByIdAndRegistro(Integer idComplejo, Integer idRegistro) throws Exception {
		try {
			return complejoDao.findByIdAndRegistro(idComplejo, idRegistro);
		} catch (Exception ex) {
			throw new Exception(ex);
		}

	}

	@Override
	public Complejo findByIdAndRegistroWithJoinFetch(Integer idComplejo, Integer idRegistro) throws Exception {
		
		try {
			return complejoDao.findByIdAndRegistroWithJoinFetch(idComplejo, idRegistro);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

}
