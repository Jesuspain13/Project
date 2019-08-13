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
	
	@Autowired
	private IRegistroDao registroDao;
	
	@Override
	public Complejo findByIdAndRegistro(Integer idComplejo, Integer idRegistro) {
		Registro r = registroDao.findById(idRegistro).get();
		return complejoDao.findByIdAndRegistro(idComplejo, r);

	}

}
