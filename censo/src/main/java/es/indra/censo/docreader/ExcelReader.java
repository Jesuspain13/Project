package es.indra.censo.docreader;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IEmpleadoDao;
import es.indra.censo.dao.IUeDao;
import es.indra.censo.docreader.validator.ValidadorColumnasExcel;
import es.indra.censo.model.Complejo;
import es.indra.censo.model.Edificio;
import es.indra.censo.model.Empleado;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Registro;
import es.indra.censo.model.Ue;
import es.indra.censo.service.IRegistroService;

@Service
public class ExcelReader {

	Logger log = LoggerFactory.getLogger(ExcelReader.class);

	@Autowired
	private IRegistroService rService;

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private IUeDao ueDao;

	@Autowired
	private IEmpleadoDao empDao;

	@Autowired
	private ValidadorColumnasExcel validadorColumnas;

	@Autowired
	private EntityManager em;

	private Locale locale;

	public ExcelReader() {
	};

	@Transactional
	public void reader(Workbook workbook, String version, Locale locale) throws Exception {
		boolean censoEncontrado = false;
		try {
			this.locale = locale;
			Sheet sheet = null;
			Iterator<Sheet> sheetsIterator = workbook.sheetIterator();
			// buscar la hoja de calculo que coincida con el nombre (es la que contiene los
			// datos)
			while (sheetsIterator.hasNext() && !censoEncontrado) {
				sheet = sheetsIterator.next();
				if (sheet.getSheetName().contains("Censo")) {
					censoEncontrado = true;
				}
			}
			if (sheet != null && censoEncontrado) {
				Iterator<Row> rows = sheet.rowIterator();
				Registro r = new Registro(version);
				this.recorrerFilas(workbook, rows, r);
				workbook.close();
				rService.save(r);
			}
		} catch (OrdenColumnasException ex) {
			log.error(ex.getMessage());
			throw new OrdenColumnasException(ex.getMessage());
		} catch (Exception ex) {

			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	/**
	 * Método que recorre las filas del excel
	 * 
	 * @param workbook
	 * @param rows
	 * @param rDao
	 * @param registroGuardado
	 * @return
	 */

	private void recorrerFilas(Workbook workbook, Iterator<Row> rows, Registro registroGuardado) throws Exception {
		try {
			int contador = 0;
			Row row;
			boolean filasVacias = false;
			while (rows.hasNext() &&  !filasVacias) {
				// primera fila -> comprobar orden de las columnas
				row = rows.next();
				
				if (contador == 0) {
					comprobarOrdenColumnas(row);
					contador++;

				} else {
					if (row == null) {
						filasVacias = true;
					}
					recogerResultadoYAsignarARegistro(workbook, row, registroGuardado);
				}
			}
		} catch (OrdenColumnasException ex) {
			log.error(ex.getMessage());
			throw new Exception(ex.getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * Ejecuta la función recorrer celdas, y asigna el complejo y la ue resultante
	 * al registro que se va a guardar
	 * 
	 * @param workbook
	 * @param row
	 * @param registroGuardado
	 * @throws Exception
	 */
	private void recogerResultadoYAsignarARegistro(Workbook workbook, Row row, Registro registroGuardado)
			throws Exception {
		try {
			Map<String, Object> res = this.recorrerCeldasDeFila(workbook, row, registroGuardado);
			Complejo c = (Complejo) res.get("complejo");
			if (c != null && !registroGuardado.getComplejos().contains(c)) {
				registroGuardado.addComplejo(c);
			}

			Ue ue = (Ue) res.get("ue");
			// comprobar si la ue es null
			if (ue != null && !registroGuardado.getUes().contains(ue)) {
				registroGuardado.addUes(ue);
			}
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	/**
	 * Ejecuta la comprobación del orden de las columnas en la primera fila, la que
	 * contiene los títulos
	 * 
	 * @param row
	 * @throws OrdenColumnasException
	 */
	private void comprobarOrdenColumnas(Row row) throws OrdenColumnasException {

		boolean resultadoValidarColumnas = validadorColumnas.iterarCeldadYComprobar(row);
		if (!resultadoValidarColumnas) {
			String msg = msgSource.getMessage("text.orden.columnas.exception.mensaje", null, this.locale);
			log.error(msg);
			throw new OrdenColumnasException(msg);
		}
	}

	/**
	 * método para recorrer las celas de una fila
	 * 
	 * @param rows
	 */
	private Map<String, Object> recorrerCeldasDeFila(Workbook wb, Row row, Registro registroGuardado) throws Exception {
		// Iterator<Cell> cells = row.cellIterator();
		try {

			TablaModelo tabla = new TablaModelo();
			// método que recoge los valores de una fila
			tabla.asignarValores(row);

			Map<String, Object> res = this.constructorEntidades(tabla, registroGuardado);

			return res;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	/**
	 * Método para creación de todas las entidades
	 * 
	 * @param tabla
	 */

	private Map<String, Object> constructorEntidades(TablaModelo tabla, Registro registroGuardado) throws Exception {
		// si el nombre del complejo del registro en la posición 1 no está vacío y
		// coincide con el de la tabla
		try {
			Complejo c = seleccionarComplejo(tabla, registroGuardado);
			Edificio e = seleccionarEdificio(tabla, c, registroGuardado);
			Planta p = seleccionarPlanta(tabla, e, registroGuardado);
			Puesto puesto = this.buildPuesto(tabla, p, registroGuardado);
			Ue ue = seleccionarUe(tabla, registroGuardado);
			Empleado emp = this.buildEmpleado(tabla, ue, registroGuardado);
			// comprobar si el empleado creado es null
			if (emp != null && ue != null) {
				puesto.setOcupado(true);
				puesto.setEmpleado(emp);
				// ue.addEmpleado(emp);
			} else if (emp == null) {
				puesto.setEmpleado(null);
			}
			p.addPuesto(puesto);
			if (!e.getPlantas().contains(p)) {
				e.addPlanta(p);
			}
			if (!c.getEdificios().contains(e)) {
				c.addEdificio(e);
			}

			// mapa para devolver resultados fuera del método
			Map<String, Object> res = new HashedMap<String, Object>();
			res.put("complejo", c);
			res.put("ue", ue);
			return res;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	// MÉTODOS PARA CONSTRUIR CADA ENTIDADES CON LOS DATOS RECOGIDOS DE CADA CELDA

	// Métodos auxiliares para complejo

	private Complejo buildComplejo(TablaModelo tabla, Registro r) {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Complejo comp = new Complejo();
		comp.setIdComplejo(tabla.getIdComplejo());
		comp.setNombreComplejo(tabla.getNombreComplejo());
		comp.setRegistro(r);
		return comp;
	}

	/**
	 * comprueba si el complejo está creado, sino, o si coincide con el de la fila
	 * 
	 * @param tabla
	 * @param r
	 * @return
	 */
	private Complejo seleccionarComplejo(TablaModelo tabla, Registro registroGuardado) {
		Complejo c = null;
		boolean complejoEncontrado = false;
		if (registroGuardado.getComplejos().size() > 0) {
			Iterator<Complejo> complejosGuardados = registroGuardado.getComplejos().iterator();

			while (!complejoEncontrado && complejosGuardados.hasNext()) {
				Complejo iteracionComplejoActual = complejosGuardados.next();
				if (!iteracionComplejoActual.getNombreComplejo().isEmpty()
						&& iteracionComplejoActual.getNombreComplejo().equals(tabla.getNombreComplejo())) {
					// si esta creado y coincide en nombre lo usamos
					c = registroGuardado.getComplejos().get(0);
					complejoEncontrado = true;
				} else if (!iteracionComplejoActual.getNombreComplejo().isEmpty()
						&& !iteracionComplejoActual.getNombreComplejo().equals(tabla.getNombreComplejo())) {
					c = this.buildComplejo(tabla, registroGuardado);
					complejoEncontrado = true;
				} else if (iteracionComplejoActual.getNombreComplejo().isEmpty()) {
					c = this.buildComplejo(tabla, registroGuardado);
					complejoEncontrado = true;
				}

			}
		} else {
			// si no tiene nombre es que no hay ninguno, se crea
			c = this.buildComplejo(tabla, registroGuardado);
		}

		return c;
	}

	// Métodos auxiliares de edificio

	private Edificio buildEdificio(TablaModelo tabla, Complejo c, Registro r) {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Edificio edificio = new Edificio();
		edificio.setNombreEdificio(tabla.getNombreEdificio());
		edificio.setComplejo(c);
		edificio.setRegistro(r);
		return edificio;
	}

	/**
	 * comprueba si el Edificio está creado, sino, o si coincide con el de la fila
	 * 
	 * @param tabla
	 * @param r
	 * @return
	 */
	private Edificio seleccionarEdificio(TablaModelo tabla, Complejo c, Registro r) {
		Edificio e = null;
		List<Edificio> listaEdif = c.getEdificios();
		// si la lista de edificios del complejo no está vacía
		boolean encontrado = false;
		int contador = 0;
		if (listaEdif.size() == 0) {
			e = this.buildEdificio(tabla, c, r);
			encontrado = true;
		}
		while (!encontrado) {
			// vamos a recorrer la lista y comprobar los nombres
			if (contador < listaEdif.size()) {
				if (listaEdif.get(contador).getNombreEdificio().contains(tabla.getNombreEdificio())) {
					e = listaEdif.get(contador);
					encontrado = true;
				}
			} else if (contador == listaEdif.size() - 1) {
				e = this.buildEdificio(tabla, c, r);
				encontrado = true;
			} else {
				contador++;
			}
		}

		return e;
	}

	// Métodos auxiliares para Planta

	private Planta buildPlanta(TablaModelo tabla, Edificio e, Registro r) {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Planta planta = new Planta();
		planta.setNombrePlanta(Long.toString(tabla.getNombrePlanta()));
		planta.setEdificio(e);
		planta.setRegistro(r);
		return planta;
	}

	/**
	 * comprueba si la Planta está creada y si coincide con el de la fila, o si no,
	 * 
	 * @param tabla
	 * @param r
	 * @return
	 */
	private Planta seleccionarPlanta(TablaModelo tabla, Edificio e, Registro r) {
		Planta p = null;
		List<Planta> listaPlanta = e.getPlantas();
		boolean encontrado = false;
		int contador = 0;
		// si el tamaño de la lista es igual a 0 -> se construye una planta
		while (listaPlanta.size() > 0 && !encontrado && contador < listaPlanta.size()) {
			// recorrer Plantas y comprobar sus nombres
			p = listaPlanta.get(contador);
			if (p.getNombrePlanta().contains(Long.toString(tabla.getNombrePlanta()))) {
				encontrado = true;
				// si el contador se pasa del tamaño de la lista -> crea una planta
			}
			contador++;
		}
		// si no se encuentra o el tamaño de la lista es 0
		if (!encontrado || listaPlanta.size() == 0) {
			p = this.buildPlanta(tabla, e, r);
			encontrado = true;
		}
		return p;
	}

	// Metodos auxiliares del Puesto

	private Puesto buildPuesto(TablaModelo tabla, Planta p, Registro r) {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Puesto puesto = new Puesto();
		puesto.setIdPuesto(tabla.getIdPuesto());
		puesto.setPlanta(p);
		puesto.setRegistro(r);
		puesto.setOcupado(false);
		return puesto;
	}

	// Métodos auxiliares del Empleado

	private Empleado buildEmpleado(TablaModelo tabla, Ue ue, Registro r) {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		if (tabla.isTeletrabajo()) {
			return null;
		}
		Empleado emp = new Empleado();
		emp.setNombre(tabla.getNombreEmpleado());
		emp.setApellido(tabla.getApellidosEmpleado());
		if (tabla.getNumeroEmpleado() != null) {
			emp.setNumeroEmpleado((tabla.getNumeroEmpleado().intValue()));
		}
		emp.setNick(tabla.getNick());
		emp.setUe(ue);
		emp.setRegistro(r);
		return emp;
	}

	private Ue buildUe(TablaModelo tabla, Registro r) {

		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Ue ue = new Ue();
		ue.setIdUe(tabla.getUe());
		ue.setNombreUe(tabla.getNombreUe());
		ue.setNombreUeRepercutible(tabla.getNombreUeRepercutible());
		ue.setUeRepercutible(tabla.getUeRepercutible());
		ue.setRegistro(r);
		return ue;
	}

	/**
	 * comprueba si el Planta está creado, sino, o si coincide con el de la fila
	 * 
	 * @param tabla
	 * @param r
	 * @return
	 */
	private Ue seleccionarUe(TablaModelo tabla, Registro r) throws Exception {
		try {
			Ue ueFound = null;
			List<Ue> ues = r.getUes();
			Iterator<Ue> ueIterator = ues.iterator();
			boolean encontrado = false;
			int contador = 0;
			if (tabla.getUe() == null) {
				return null;
			}
			// si el tamaño es 0 es que no hay ue
//			if (ues.size() == 0) {
//				ueFound = this.buildUe(tabla, r);
//				// ueFound = ueDao.save(ueFound);
//				encontrado = true;
//			}

			while (ueIterator.hasNext() && !encontrado) {
				// vamos a recorrer la lista y comprobar los nombres
				ueFound = ueIterator.next();
				// si es null es que no hay ue (aunque en ocasiones aunque no haya coge un null)
				if (ueFound == null) {
					ueFound = this.buildUe(tabla, r);
					encontrado = true;
					// si el id de la iteración es igual al id de la tabla -> no creamos ue
				} else if (ueFound.getNombreUe().contains(tabla.getNombreUe())) {
					encontrado = true;
					// si el contador se pasa del tamaño de la lista de ue -> se crea un ue
				} else {
					contador++;
				}
			}
			if (!encontrado || ues.size() == 0) {
				ueFound = this.buildUe(tabla, r);

				encontrado = true;
			}

			return ueFound;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}
}
