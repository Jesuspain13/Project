package es.indra.censo.docreader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import es.indra.censo.dao.ITiposErroresDao;
import es.indra.censo.dao.IUsuarioDao;
import es.indra.censo.docreader.validator.ValidadorColumnasExcel;
import es.indra.censo.model.Complejo;
import es.indra.censo.model.Edificio;
import es.indra.censo.model.Empleado;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Registro;
import es.indra.censo.model.Ue;
import es.indra.censo.model.UeRepercutible;
import es.indra.censo.model.Usuario;
import es.indra.censo.model.errores.excel.ColumnaExcel;
import es.indra.censo.model.errores.excel.Fila;
import es.indra.censo.model.errores.excel.TipoError;
import es.indra.censo.model.wrapper.NoSorteableException;
import es.indra.censo.model.wrapper.PlantaBajaWrapper;
import es.indra.censo.model.wrapper.PlantaWrapper;
import es.indra.censo.model.wrapper.PlantaWrapperAbs;
import es.indra.censo.service.IRegistroService;

@Service
public class ExcelReader {

	private Logger log = LoggerFactory.getLogger(ExcelReader.class);

	@Autowired
	private IRegistroService rService;

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private ValidadorColumnasExcel validadorColumnas;

	@Autowired
	private IUsuarioDao uDao;

	@Autowired
	private BeanFactory beanFactory;
	
	@Autowired
	private ITiposErroresDao tiposErroresDao;
	
	private List<TipoError> tiposErroresExistentes;
	
	private List<ColumnaExcel> columnasExcel = new ArrayList<ColumnaExcel>();
	

	private Locale locale;

	private List<Ue> ue = new ArrayList<Ue>();
	private List<UeRepercutible> ueRep = new ArrayList<UeRepercutible>();
	private List<Planta> plantas = new ArrayList<Planta>();
	
	private List<Fila> filasConErrores = new ArrayList<Fila>();

	public ExcelReader() {
	};

	/**
	 * Se encarga de recorrer y leer los valores del workbook seleccionado
	 * 
	 * @param workbook
	 * @param version
	 * @param locale
	 * @param nombreAutor
	 * @throws Exception
	 */
	public Integer reader(Workbook workbook, String version, Locale locale, String nombreAutor) throws Exception {
		boolean censoEncontrado = false;
		try {
			this.locale = locale;
			Sheet sheet = null;
			Iterator<Sheet> sheetsIterator = workbook.sheetIterator();
			Usuario autor = uDao.findByUsername(nombreAutor);
			Integer idRegistro = 0;
			// buscar la hoja de calculo que coincida con el nombre (es la que contiene los
			// datos)
			while (sheetsIterator.hasNext() && !censoEncontrado) {
				sheet = sheetsIterator.next();
				if (sheet.getSheetName().contains("Censo")) {
					censoEncontrado = true;
				}
			}
			if (sheet != null && censoEncontrado && (autor != null || autor.getUsername() != null)) {
				Iterator<Row> rows = sheet.rowIterator();
				Registro r = new Registro(version);
				this.recorrerFilas(workbook, rows, r);
				r.setAutorSubida(autor);
				this.ordenarPlanta(r);
				r.setFilasErroneas(filasConErrores);
				Registro registroGuardado = rService.save(r);
				
				idRegistro = registroGuardado.getIdRegistro();
			} else if (!censoEncontrado) {
				// si no encuentra la hoja de censo en el excel
				throw new Exception("Hoja del censo no encontrada en el archivo .xlsx");
			}
			return idRegistro;
		} catch (OrdenColumnasException ex) {
			log.error(ex.getMessage());
			throw new OrdenColumnasException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	/**
	 * limpia las listas para evitar errores durante la persistencia.
	 */
	private void limpiarListas() {
		this.ue.clear();
		this.plantas.clear();
		this.ueRep.clear();
		this.filasConErrores.clear();

		this.filasConErrores.clear();
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
			this.limpiarListas();
			while (rows.hasNext() && !filasVacias) {
				// primera fila -> comprobar orden de las columnas
				row = rows.next();
				if (contador == 0) {
					comprobarOrdenColumnas(row);
					contador++;

				} else {
					// comprobación para evitar leer filas que esten vacías
					if (row.getCell(0) == null) {
						filasVacias = true;
					} else {
						recogerResultadoYAsignarARegistro(workbook, row, registroGuardado);
					}
				}
			}
		} catch (OrdenColumnasException ex) {
			ex.printStackTrace();
			log.error(ex.getStackTrace().toString());
			throw new Exception(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getStackTrace().toString());
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
			
		} catch (Exception ex) {

			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new Exception(ex.getMessage());

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

		List<ColumnaExcel> columnasExcelCreadas = validadorColumnas.iterarCeldadYComprobar(row);
		if (columnasExcelCreadas.isEmpty() || columnasExcelCreadas.size() < 1) {
			String msg = msgSource.getMessage("text.orden.columnas.exception.mensaje", null, this.locale);
			log.error(msg);
			throw new OrdenColumnasException(msg);

		} else {
			if (columnasExcel.size() != columnasExcelCreadas.size()) {
				this.columnasExcel.clear();
				this.columnasExcel = columnasExcelCreadas;
			} 
			
			this.tiposErroresExistentes = tiposErroresDao.findAll();
		}
	}

	/**
	 * método para recorrer las celas de una fila
	 * 
	 * @param rows
	 */
	private Map<String, Object> recorrerCeldasDeFila(Workbook wb, Row row, Registro registroGuardado) throws Exception {
		try {

			TablaModelo tabla = new TablaModelo(this.columnasExcel, this.tiposErroresExistentes);
			// método que recoge los valores de una fila
			Fila filaConErrores = tabla.asignarValores(row);
			if (filaConErrores != null) {
				filaConErrores.setRegistro(registroGuardado);
				this.filasConErrores.add(filaConErrores);
			}
			

			Map<String, Object> res = this.constructorEntidades(tabla, registroGuardado);

			return res;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getStackTrace().toString());
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
			UeRepercutible ueRep = this.seleccionarUeRepercutible(tabla, registroGuardado);
			Empleado emp = this.buildEmpleado(tabla, ue, registroGuardado);
			// comprobar si el empleado creado es null

			if (emp != null) {
				puesto.setOcupado(true);
				puesto.setEmpleado(emp);

			} else if (emp == null) {
				puesto.setEmpleado(null);
			}
			p.addPuesto(puesto);
			if (!c.getEdificios().contains(e)) {
				c.addEdificio(e);
			}
			if (ue != null && ue.getUeRepercutible() == null) {
				ue.setUeRepercutible(ueRep);
				ueRep.addUe(ue);
			}

			// mapa para devolver resultados fuera del método
			Map<String, Object> res = new HashedMap<String, Object>();
			res.put("complejo", c);
			res.put("ue", ue);
			res.put("ueRep", ueRep);
			res.put("planta", p);
			return res;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}

	// MÉTODOS PARA CONSTRUIR CADA ENTIDADES CON LOS DATOS RECOGIDOS DE CADA CELDA

	// Métodos auxiliares para complejo

	/**
	 * Instancia un complejo con los datos extraídos de la fila de la tabla
	 * 
	 * @param tabla
	 * @param r
	 * @return
	 * @throws Exception
	 */
	private Complejo buildComplejo(TablaModelo tabla, Registro r) throws Exception {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		try {
			Complejo comp = new Complejo();
			comp.setIdComplejo(tabla.getIdComplejo());
			comp.setNombreComplejo(tabla.getNombreComplejo());
			comp.setRegistro(r);
			return comp;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}

	/**
	 * comprueba si el complejo está creado, sino, o si coincide con el de la fila
	 * 
	 * @param tabla
	 * @param r
	 * @return
	 */
	private Complejo seleccionarComplejo(TablaModelo tabla, Registro registroGuardado) throws Exception {
		try {
			Complejo c = null;
			boolean complejoEncontrado = false;
			List<Complejo> complejosGuardados = registroGuardado.getComplejos();
			if (complejosGuardados.size() > 0) {
				Iterator<Complejo> complejosGuardadosIter = complejosGuardados.iterator();
				while (!complejoEncontrado && complejosGuardadosIter.hasNext()) {
					c = complejosGuardadosIter.next();
					if (!c.getNombreComplejo().isEmpty() && c.getNombreComplejo().equals(tabla.getNombreComplejo())) {
						// si esta creado y coincide en nombre lo usamos
						complejoEncontrado = true;
					} else if ((!c.getNombreComplejo().isEmpty()
							&& !c.getNombreComplejo().equals(tabla.getNombreComplejo()))) {
						c = this.buildComplejo(tabla, registroGuardado);
						complejoEncontrado = true;
					}
				}
			} else {
				// si no tiene nombre es que no hay ninguno, se crea
				c = this.buildComplejo(tabla, registroGuardado);
			}
			return c;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}

	// Métodos auxiliares de edificio

	/**
	 * Instancia un edificio con los datos de la fila de la tabla
	 * 
	 * @param tabla
	 * @param c
	 * @param r
	 * @return
	 * @throws Exception
	 */
	private Edificio buildEdificio(TablaModelo tabla, Complejo c, Registro r) throws Exception {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		try {
			Edificio edificio = new Edificio();
			edificio.setNombreEdificio(tabla.getNombreEdificio());
			edificio.setComplejo(c);
			edificio.setRegistro(r);
			return edificio;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}

	/**
	 * comprueba si el Edificio está creado, sino, o si coincide con el de la fila
	 * 
	 * @param tabla
	 * @param r
	 * @return
	 */
	private Edificio seleccionarEdificio(TablaModelo tabla, Complejo c, Registro r) throws Exception {
		try {
			Edificio e = null;
			List<Edificio> listaEdificiosGuardados = c.getEdificios();
			boolean encontrado = false;
			int contador = 0;

			while (!encontrado && contador < listaEdificiosGuardados.size()) {
				e = listaEdificiosGuardados.get(contador);
				// vamos a recorrer la lista de edificos y comprobar los nombres
				if (e.getNombreEdificio().contains(tabla.getNombreEdificio())) {
					encontrado = true;
				} else {
					contador++;
				}
			}
			if (listaEdificiosGuardados.size() == 0 || !encontrado) {
				e = this.buildEdificio(tabla, c, r);
			}
			return e;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}

	// Métodos auxiliares para Planta

	/**
	 * Instancioa una planta con los datos extraídos de la fila de la tabla
	 * 
	 * @param tabla
	 * @param e
	 * @param r
	 * @return
	 * @throws Exception
	 */
	private Planta buildPlanta(TablaModelo tabla, Edificio e, Registro r) throws Exception {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		try {
			Planta planta = new Planta();
			planta.setNombrePlanta(Long.toString(tabla.getNombrePlanta()));
			planta.setEdificio(e);
			planta.setRegistro(r);
			return planta;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}

	/**
	 * comprueba si la Planta está creada y si coincide con el de la fila, o si no,
	 * 
	 * @param tabla
	 * @param r
	 * @return
	 */
	private Planta seleccionarPlanta(TablaModelo tabla, Edificio e, Registro r) throws Exception {
		try {
			Planta p = null;
			boolean encontrado = false;
			int contador = 0;
			// si el tamaño de la lista es igual a 0 -> se construye una planta
			while (this.plantas.size() > 0 && !encontrado && contador < this.plantas.size()) {
				// recorrer Plantas y comprobar sus nombres
				p = this.plantas.get(contador);
				if (p.getNombrePlanta().contains(Long.toString(tabla.getNombrePlanta()))) {
					encontrado = true;
					// si el contador se pasa del tamaño de la lista -> crea una planta
				}
				contador++;
			}
			// si no se encuentra o el tamaño de la lista es 0
			if (!encontrado || this.plantas.size() == 0) {
				p = this.buildPlanta(tabla, e, r);
				this.plantas.add(p);
				encontrado = true;
			}
			return p;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}

	// Metodos auxiliares del Puesto

	/**
	 * Instancia un puesto con los datos extraídos de la fila de la tabla
	 * 
	 * @param tabla
	 * @param p
	 * @param r
	 * @return
	 * @throws Exception
	 */
	private Puesto buildPuesto(TablaModelo tabla, Planta p, Registro r) throws Exception {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		try {
			Puesto puesto = new Puesto();
			puesto.setIdPuesto(tabla.getIdPuesto());
			puesto.setPlanta(p);
			puesto.setRegistro(r);
			puesto.setOcupado(false);
			return puesto;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}

	// Métodos auxiliares del Empleado

	/**
	 * Instancia un empleado con los datos extraídos de la fila de la tabla
	 * 
	 * @param tabla
	 * @param ue
	 * @param r
	 * @return
	 * @throws Exception
	 */
	private Empleado buildEmpleado(TablaModelo tabla, Ue ue, Registro r) throws Exception {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		try {
			// siendo la columna de apellidos el mínimo de datos para reconocer que existe
			// un usuario
			if (tabla.getApellidosEmpleado() == null) {
				return null;
			}
			Empleado emp = new Empleado();
			emp.setNombre(tabla.getNombreEmpleado());
			emp.setApellido(tabla.getApellidosEmpleado());
			if (tabla.getNumeroEmpleado() != null) {
				emp.setNumeroEmpleado((tabla.getNumeroEmpleado().intValue()));
			} else {
				emp.setNumeroEmpleado(null);
			}
			emp.setNick(tabla.getNick());
			emp.setUe(ue);
			emp.setRegistro(r);
			return emp;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}

	/**
	 * Instancia un ue con los datos de la fila de la tabla
	 * 
	 * @param tabla
	 * @param r
	 * @return
	 * @throws Exception
	 */
	private Ue buildUe(TablaModelo tabla, Registro r) throws Exception {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		try {
			Ue ue = new Ue();
			ue.setIdUe(tabla.getUe());
			ue.setNombreUe(tabla.getNombreUe());

			ue.setRegistro(r);
			return ue;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
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
			int contador = 0;
			boolean encontrado = false;
			if (tabla.getUe() == null) {
				return null;
			}
			while (this.ue.size() > 0 && !encontrado && contador < this.ue.size()) {
				// recorrer Plantas y comprobar sus nombres
				ueFound = this.ue.get(contador);
				if (ueFound.getNombreUe().contains(tabla.getNombreUe())) {
					encontrado = true;

				}
				contador++;
			}
			if (!encontrado || this.ue.size() == 0) {
				ueFound = this.buildUe(tabla, r);
				this.ue.add(ueFound);

			}
			return ueFound;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	/**
	 * Crea un ueRepercutible con los datos extraídos de la fila del excel
	 * 
	 * @param tabla
	 * @param r
	 * @return
	 * @throws Exception
	 */
	private UeRepercutible buildUeRepercituble(TablaModelo tabla, Registro r) throws Exception {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		try {
			UeRepercutible ue = new UeRepercutible();

			ue.setNombreUeRepercutible(tabla.getNombreUeRepercutible());
			ue.setIdUeRepercutible(tabla.getUeRepercutible());
			ue.setRegistro(r);
			return ue;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}

	/**
	 * Comprueba si el ueRepercutible de la fila actual se encuentra en la lista si
	 * está, lo utiliza, si no, crea un ueRepercutible con los datos
	 * 
	 * @param tabla
	 * @param r
	 * @return
	 * @throws Exception
	 */
	private UeRepercutible seleccionarUeRepercutible(TablaModelo tabla, Registro r) throws Exception {
		try {
			UeRepercutible ueRepFound = null;
			int contador = 0;
			boolean encontrado = false;
			if (tabla.getUeRepercutible() == null) {
				return null;
			}
			while (this.ueRep.size() > 0 && !encontrado && contador < this.ueRep.size()) {
				// recorrer Plantas y comprobar sus nombres
				ueRepFound = this.ueRep.get(contador);
				if (ueRepFound.getNombreUeRepercutible().contains(tabla.getNombreUeRepercutible())) {
					encontrado = true;
				}
				contador++;
			}
			if (!encontrado || this.ueRep.size() == 0) {
				ueRepFound = this.buildUeRepercituble(tabla, r);
				this.ueRep.add(ueRepFound);
			}
			return ueRepFound;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	/**
	 * metodo para ordenar los puestos de las plantas extraídas del censo y
	 * asignarlas al registro
	 * 
	 * @param r Registro
	 * @throws NoSorteableException
	 */
	private void ordenarPlanta(Registro r) throws NoSorteableException {
		try {
			this.recorrerPlantasDelRegistro(r);
			r.setPlantas(this.plantas);
			r.setUes(this.ue);
			r.setUesRep(this.ueRep);

		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new NoSorteableException(ex.getCause().toString());
		}
	}

	/**
	 * recorre las plantas y ordena los puestos. Si hay alguna planta incluida en
	 * alguna de ellas la separa y crea una planta nueva (como la azahara)
	 * 
	 * @param r
	 * @return
	 * @throws Exception
	 */
	private void recorrerPlantasDelRegistro(Registro r) throws Exception {
		try {
			List<Planta> plantasnuevas = new ArrayList<Planta>();
			PlantaWrapperAbs pWrapper = null;
			for (Planta p : this.plantas) {
				if (p.getNombrePlanta().contains("0")) {
					pWrapper = beanFactory.getBean(PlantaBajaWrapper.class);
					List<Puesto> plantaBaja = pWrapper.ordenarPuesto(p.getNombrePlanta(), p.getPuestos());
					p.setPuestos(plantaBaja);
					// si la planta a buscar es azahar es encesario un método de la clase
					// PlantaBajaWrapper
					PlantaBajaWrapper pBajaWrapper = (PlantaBajaWrapper) pWrapper;
					Planta plantaAzahar = this.crearPlantaNueva(p, "Azahar", pBajaWrapper.getPuestosAzahar());
					plantasnuevas.add(plantaAzahar);

				} else if (p.getNombrePlanta().contains("1")) {
					pWrapper = beanFactory.getBean(PlantaWrapper.class);

					List<Puesto> plantaPrimera = pWrapper.ordenarPuesto(p.getNombrePlanta(), p.getPuestos());
					p.setPuestos(plantaPrimera);
				}
			}
			this.añadirPlantasNuevasALista(plantasnuevas);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}

	/**
	 * Añadir las plantas creadas a la lista de plantas
	 * 
	 * @param plantasOrdenadas
	 */
	private void añadirPlantasNuevasALista(List<Planta> plantasnuevas) {
		for (Planta p : plantasnuevas) {
			this.plantas.add(p);
		}
	}

	/**
	 * crea una planta nueva para cuando haya que puestos de otra planta en una de
	 * las existentes en el censo (ejemplo planta azahar)
	 * 
	 * @param p
	 * @param nuevoNombre
	 * @param nuevosPuestos
	 * @return
	 * @throws Exception
	 */
	private Planta crearPlantaNueva(Planta p, String nuevoNombre, List<Puesto> nuevosPuestos) throws Exception {
		try {
			Planta plantaNueva = new Planta(nuevoNombre, p.getRegistro(), p.getEdificio());
			plantaNueva.setPuestos(nuevosPuestos);

			// asignamos la planta nueva a sus puestos
			for (Puesto pl : plantaNueva.getPuestos()) {
				pl.setPlanta(plantaNueva);
			}

			return plantaNueva;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	/**
	 * Recorre los puestos de la lista
	 * 
	 * @param p
	 * @param nuevosPuestos
	 * @return
	 */
	private List<Puesto> cambiarPlantaDePuestos(Planta p, List<Puesto> nuevosPuestos) {
		for (Puesto pl : nuevosPuestos) {
			pl.setPlanta(p);
		}
		return nuevosPuestos;
	}
	
	
}