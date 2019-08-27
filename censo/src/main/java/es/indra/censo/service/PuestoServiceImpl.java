package es.indra.censo.service;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IPuestoDao;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.wrapper.NoSorteableException;
import es.indra.censo.model.wrapper.PlantaBajaWrapper;
import es.indra.censo.model.wrapper.PlantaWrapper;
import es.indra.censo.model.wrapper.PlantaWrapperAbs;

@Service
public class PuestoServiceImpl implements IPuestoService {

	private Logger log = LoggerFactory.getLogger(PuestoServiceImpl.class);

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private IPuestoDao puestoDao;

	@Override
	public List<Puesto> findAll() throws Exception {
		try {
			return (List<Puesto>) puestoDao.findAll();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	@Transactional
	public void save(Puesto puesto) throws Exception {
		try {
			puestoDao.save(puesto);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	@Transactional
	public void deletePuesto(Integer id) throws Exception {
		try {
			puestoDao.deleteById(id);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Puesto findPuestoById(Integer id) throws Exception {
		try {
			return puestoDao.findById(id).get();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public List<Puesto> findByPlantaOrdenados(String nombrePlanta, Integer idRegistro)
			throws NoSorteableException, Exception {
		// TODO Auto-generated method stub
		try {
			String nombre = nombrePlanta.toString();
			PlantaWrapperAbs pWrapper;
			List<Puesto> puestosDesordenados;
			if (nombre.contains("0")) {
				puestosDesordenados = puestoDao.findByPlantaAndRegistro(nombre, idRegistro);
				pWrapper = new PlantaBajaWrapper();

				return pWrapper.ordenarPuesto(nombre, puestosDesordenados);
			} else if (nombre.contains("1")) {
				puestosDesordenados = puestoDao.findByPlantaAndRegistro(nombre, idRegistro);
				pWrapper = new PlantaWrapper();

				return pWrapper.ordenarPuesto(nombre, puestosDesordenados);
			} else if (nombre.contains("azahar")) {
				puestosDesordenados = puestoDao.findByPlantaAndRegistro("0", idRegistro);
				PlantaBajaWrapper pWrapperAzahar = new PlantaBajaWrapper();
				pWrapperAzahar.ordenarPuesto("0", puestosDesordenados);
				return pWrapperAzahar.getPlantaAzahara();
			} else {
				throw new NoSorteableException(
						msgSource.getMessage("text.error.encontrar.planta", null, new Locale("es", "ES")));
			}
		} catch (NoSorteableException ex) {
			log.error(ex.getMessage());
			throw new NoSorteableException(ex.getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}

	}

}
