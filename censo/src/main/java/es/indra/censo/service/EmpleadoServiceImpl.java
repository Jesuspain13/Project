package es.indra.censo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IEmpleadoDao;
import es.indra.censo.model.Empleado;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

	private Logger log = LoggerFactory.getLogger(ComplejoServiceImpl.class);

	@Autowired
	private IEmpleadoDao empleadoDao;

	@Override
	public List<Empleado> findAll() throws Exception {
		try {
			return (List<Empleado>) empleadoDao.findAll();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Empleado findEmpleadoById(Integer id) throws Exception {
		try {
			return empleadoDao.findById(id).get();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Empleado findEmpleadoByIdPuesto(Integer id) throws Exception {
		try {
			return empleadoDao.findEmpleadoByIdPuesto(id);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public Page<Empleado> findEmpleadoByNombreYApellidos(String nombre, String apellidos, int pageNumber) throws Exception {
		try {
			Pageable pageRequest = PageRequest.of(pageNumber, 3);
			return empleadoDao.findEmpleadoByNameAndSurname(nombre.toUpperCase(), apellidos.toUpperCase(), pageRequest);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

}
