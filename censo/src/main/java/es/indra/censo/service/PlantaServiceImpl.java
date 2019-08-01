package es.indra.censo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IPlantaDao;
import es.indra.censo.model.Planta;

@Service
public class PlantaServiceImpl implements IPlantaService {

	@Autowired
	private IPlantaDao plantaDao;

	@Override
	public List<Planta> findAll() {
		return (List<Planta>) plantaDao.findAll();
	}

	@Override
	@Transactional
	public void save(Planta planta) {
		plantaDao.save(planta);

	}

	@Override
	@Transactional
	public void deletePlanta(Integer id) {
		plantaDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Planta findPlantaById(Integer id) {
		
		return plantaDao.findById(id).get();
	}

}
