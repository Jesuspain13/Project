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

//	@Override
//	public List<Puesto> findByPlantaOrdenados(Planta p) throws NoSorteableException, Exception {
//		// TODO Auto-generated method stub
//		try {
//			PlantaWrapperAbs pWrapper;
//			if (p.getNombrePlanta().contains("0")) {
//				pWrapper = new PlantaBajaWrapper();
//				List<Puesto> puestosDesordenados = puestoDao.findByPlanta(p);
//				return pWrapper.ordenarPuesto(p.getNombrePlanta(), puestosDesordenados);
//			} else if (p.getNombrePlanta().contains("1")) {
//				pWrapper = new PlantaWrapper();
//				List<Puesto> puestosDesordenados = puestoDao.findByPlanta(p);
//				return pWrapper.ordenarPuesto(p.getNombrePlanta(), puestosDesordenados);
//			} else {
//				return null;
//			}
//		} catch(NoSorteableException ex) {
//			throw new NoSorteableException(ex.getMessage());
//		} catch (Exception ex) {
//			throw new Exception(ex);
//		}
//
//	}

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
