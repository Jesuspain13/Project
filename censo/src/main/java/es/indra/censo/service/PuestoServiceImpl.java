package es.indra.censo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IPuestoDao;
import es.indra.censo.model.Puesto;


@Service
public class PuestoServiceImpl implements IPuestoService {

	@Autowired
	private IPuestoDao puestoDao;

	@Override
	public List<Puesto> findAll() {
		return (List<Puesto>)puestoDao.findAll();
	}

	@Override
	@Transactional
	public void save(Puesto puesto) {
		puestoDao.save(puesto);

	}

	@Override
	@Transactional
	public void deletePuesto(Integer id) {
		puestoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Puesto findPuestoById(Integer id) {
		
		return puestoDao.findById(id).get();
	}

}
