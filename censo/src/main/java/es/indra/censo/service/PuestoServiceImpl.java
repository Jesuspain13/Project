package es.indra.censo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IPuestoDao;
import es.indra.censo.model.Puesto;

@Service
public class PuestoServiceImpl implements IPuestoService {

	private Logger log = LoggerFactory.getLogger(PuestoServiceImpl.class);

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private IPuestoDao puestoDao;
	
	@Autowired
	private BeanFactory beanFactory;

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
	public Puesto save(Puesto puesto) throws Exception {
		try {
			return puestoDao.save(puesto);
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
	public List<Puesto> findByPlantaOrdenados(String nombrePlanta, Integer idRegistro) throws Exception {
		try {
			List<Puesto> sinFiltrar = puestoDao.findByPlantaAndRegistro(nombrePlanta, idRegistro);;
			List<Puesto> filtrado = new ArrayList<Puesto>();
			for (Puesto p: sinFiltrar) {
				//puestos que no se muestran en la lista
				if (!p.getIdPuesto().contains("D") && !p.getIdPuesto().contains("147") &&
						!p.getIdPuesto().contains("159") && !p.getIdPuesto().contains("213A") &&
						!p.getIdPuesto().contains("230A")) {
						filtrado.add(p);
						
				}
			} 
			return filtrado;
		}  catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public Puesto findPuestoByIdAndRegistro(Integer id, Integer idRegistro) throws Exception {
		try {
			return puestoDao.findByIdAndRegistro(id, idRegistro);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

}
