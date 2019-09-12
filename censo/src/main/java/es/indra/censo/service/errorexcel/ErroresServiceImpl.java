package es.indra.censo.service.errorexcel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.indra.censo.dao.IFilasDao;
import es.indra.censo.model.errores.excel.Fila;

@Service
public class ErroresServiceImpl implements IFilasExcelSvc {
	
	private Logger log = LoggerFactory.getLogger(ErroresServiceImpl.class);
	
	@Autowired
	private IFilasDao erroresDao;
	
	

	@Override
	public Page<Fila> guardarErrores(List<Fila> e) throws Exception {
		try {
			erroresDao.saveAll(e);
			return this.encontrarErroresDelRegistro(e.get(0).getRegistro().getIdRegistro(), 0);
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}

	@Override
	public void ignorarErrores(Fila e) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<Fila> encontrarErroresDelRegistro(Integer idRegistro, int pageNumber) throws Exception {
		try {
			Pageable pageRequest = PageRequest.of(pageNumber, 5);
		// TODO Auto-generated method stub
		return erroresDao.findAllByIdRegistro(idRegistro, pageRequest);
		} catch (Exception ex) {
		log.error(ex.getCause().toString());
		throw new Exception(ex);
		}
	}

}
