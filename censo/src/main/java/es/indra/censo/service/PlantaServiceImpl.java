package es.indra.censo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IPlantaDao;
import es.indra.censo.dao.IRegistroDao;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Registro;

@Service
public class PlantaServiceImpl implements IPlantaService {
	private Logger log = LoggerFactory.getLogger(PlantaServiceImpl.class);

	@Autowired
	private IPlantaDao plantaDao;

	@Autowired
	private IRegistroDao registroDao;

	@Override
	public List<Planta> findAll() throws Exception {
		try {
			return (List<Planta>) plantaDao.findAll();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public void save(Planta planta) throws Exception {
		try {
			plantaDao.save(planta);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}

	}

	@Override
	@Transactional
	public void deletePlanta(Integer id) throws Exception {
		try {
			plantaDao.deleteById(id);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Planta findPlantaById(Integer id) throws Exception {
		try {
			return plantaDao.findById(id).get();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public Planta findPlantaByNombrePlanta(Integer nombrePlanta) throws Exception {
		// TODO Auto-generated method stub
		try {
			String nombre = nombrePlanta.toString();
			return plantaDao.findByNombrePlanta(nombre);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public Planta findPlantaByIdPlantaAndRegistro(Integer idPlanta, int idRegistro) throws Exception {
		// TODO Auto-generated method stub
		try {
			return plantaDao.findByIdPlantaAndRegistro(idPlanta, idRegistro);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	public Planta findPlantaByNombrePlantaAndRegistro(Integer nombrePlanta, Integer idRegistro) throws Exception {
		try {
			String nombre = nombrePlanta.toString();
			return plantaDao.findByNombrePlantaAndRegistro(nombre, idRegistro);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

}
