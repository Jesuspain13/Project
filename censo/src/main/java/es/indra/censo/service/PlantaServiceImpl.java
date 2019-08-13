package es.indra.censo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IPlantaDao;
import es.indra.censo.dao.IRegistroDao;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Registro;

@Service
public class PlantaServiceImpl implements IPlantaService {

	@Autowired
	private IPlantaDao plantaDao;
	
	@Autowired 
	private IRegistroDao registroDao;

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

	@Override
	public Planta findPlantaByNombrePlanta(Integer nombrePlanta) {
		// TODO Auto-generated method stub
		String nombre = nombrePlanta.toString();
		return plantaDao.findByNombrePlanta(nombre);
	}

	@Override
	public Planta findPlantaByIdPlantaAndRegistro(Integer idPlanta, int idRegistro) {
		// TODO Auto-generated method stub

		Registro r = registroDao.findById(idRegistro).get();
		return plantaDao.findByIdPlantaAndRegistro(idPlanta, r);
	}
	
	public Planta findPlantaByNombrePlantaAndRegistro(Integer nombrePlanta, Integer idRegistro) {
		String nombre = nombrePlanta.toString();
		return plantaDao.findByNombrePlantaAndRegistro(nombre, idRegistro);
	}
	
	

}
