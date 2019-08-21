package es.indra.censo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IPuestoDao;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Registro;
import es.indra.censo.model.wrapper.NoSorteableException;
import es.indra.censo.model.wrapper.PlantaBajaWrapper;
import es.indra.censo.model.wrapper.PlantaWrapper;
import es.indra.censo.model.wrapper.PlantaWrapperAbs;


@Service
public class PuestoServiceImpl implements IPuestoService {

	@Autowired
	private IPuestoDao puestoDao;

	@Override
	public List<Puesto> findAll() throws Exception {
		try {
		return (List<Puesto>)puestoDao.findAll();
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	@Transactional
	public void save(Puesto puesto) throws Exception {
		try {
		puestoDao.save(puesto);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	@Transactional
	public void deletePuesto(Integer id) throws Exception {
		try {
		puestoDao.deleteById(id);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Puesto findPuestoById(Integer id) throws Exception {
		try {
			return puestoDao.findById(id).get();
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public List<Puesto> findByPlantaOrdenados(Integer nombrePlanta, Integer idRegistro) throws NoSorteableException, Exception {
		// TODO Auto-generated method stub
		try {
			String nombre = nombrePlanta.toString();
			PlantaWrapperAbs pWrapper;
			List<Puesto> puestosDesordenados = puestoDao
					.findByPlantaAndRegistro(nombre, idRegistro);
			if (nombre.contains("0")) {
				pWrapper = new PlantaBajaWrapper();
				
				return pWrapper.ordenarPuesto(nombre, puestosDesordenados);
			} else if (nombre.contains("1")) {
				pWrapper = new PlantaWrapper();
				
				return pWrapper.ordenarPuesto(nombre, puestosDesordenados);
			} else {
				return null;
			}
		} catch(NoSorteableException ex) {
			throw new NoSorteableException(ex.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex);
		}

	}

}
