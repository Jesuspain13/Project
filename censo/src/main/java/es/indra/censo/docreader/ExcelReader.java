package es.indra.censo.docreader;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IEmpleadoDao;
import es.indra.censo.dao.IRegistroDao;
import es.indra.censo.dao.IUeDao;
import es.indra.censo.model.Complejo;
import es.indra.censo.model.Edificio;
import es.indra.censo.model.Empleado;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Registro;
import es.indra.censo.model.Ue;

@Component
public class ExcelReader {

	private IRegistroDao rDao;

	private IUeDao ueDao;

	private IEmpleadoDao empDao;

	public ExcelReader() {
	};

	public ExcelReader(IRegistroDao rDao, IUeDao ueDao, IEmpleadoDao empDao) {
		this.rDao = rDao;
		this.ueDao = ueDao;
		this.empDao = empDao;

	}

	public static final String FILE_PATH = "D:/vmramon/Escritorio/excel/Sitios_Edificio.xlsx";

	private Registro r;

	@Transactional
	public void reader() {
		try (Workbook workbook = WorkbookFactory.create(new File(FILE_PATH));) {
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			int contador = 0;
			r = this.buildRegistro();
			Registro registroGuardado = rDao.save(r);
			
			registroGuardado = this.recorrerFilas(workbook, rows, rDao, registroGuardado);
//			Complejo c = null;
//			while (rows.hasNext()) {
//				// contador para empezar a guardar valores a partir de la primera linea (la
//				// primera no incluye datos solo titulos)
//
//				if (contador > 0) {
//					if (c == null) {
//						c = this.recorrerCeldasDeFila(workbook, rows.next(), rDao, registroGuardado);
//						registroGuardado.addComplejo(c);
//					} else {
//						this.recorrerCeldasDeFila(workbook, rows.next(), rDao, registroGuardado);
//					}
//
//				} else if (contador == 0) {
//					// La primera fila son los titulos de las columnas
//					rows.next();
//
//				}
//				contador++;
//			}
			rDao.save(registroGuardado);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private Registro recorrerFilas(Workbook workbook, Iterator<Row> rows, IRegistroDao rDao,
			Registro registroGuardado) {
		int contador = 0;
		Complejo c = null;
		while (rows.hasNext()) {
			// contador para empezar a guardar valores a partir de la primera linea (la
			// primera no incluye datos solo titulos)

			if (contador > 0) {
				if (c == null) {
					c = this.recorrerCeldasDeFila(workbook, rows.next(), rDao, registroGuardado);
					registroGuardado.addComplejo(c);
				} else {
					this.recorrerCeldasDeFila(workbook, rows.next(), rDao, registroGuardado);
				}

			} else if (contador == 0) {
				// La primera fila son los titulos de las columnas
				rows.next();

			}
			contador++;
		}
		return registroGuardado;
	}

	/**
	 * método para recorrer las celas de una fila
	 * 
	 * @param rows
	 */
	private Complejo recorrerCeldasDeFila(Workbook wb, Row row, IRegistroDao rDao, Registro registroGuardado) {
		Iterator<Cell> cells = row.cellIterator();
		TablaModelo tabla = new TablaModelo();

		// método que recoge los valores de una fila
		tabla.asignarValores(cells);

		// }
		Complejo c = this.constructorEntidades(tabla, rDao, registroGuardado);
		return c;

	}

	/**
	 * Método para creación de todas las entidades
	 * 
	 * @param tabla
	 */
	@Transactional
	private Complejo constructorEntidades(TablaModelo tabla, IRegistroDao rDao, Registro registroGuardado) {
		// si el nombre del complejo del registro en la posición 1 no está vacío y
		// coincide con el de la tabla
		Complejo c = seleccionarComplejo(tabla, registroGuardado);
		Edificio e = seleccionarEdificio(tabla, c, registroGuardado);
		Planta p = seleccionarPlanta(tabla, e, registroGuardado);
		Puesto puesto = this.buildPuesto(tabla, p, registroGuardado);
		Ue ue = seleccionarUe(tabla, registroGuardado);
		Empleado emp = this.buildEmpleado(tabla, ue, registroGuardado);
		//comprobar si el empleado creado es null
		if (emp != null && ue != null) {
			Empleado empSaved = empDao.save(emp);
			puesto.setOcupado(true);
			puesto.setEmpleado(empSaved);
			ue.addEmpleado(empSaved);
		} else if (emp == null) {
			puesto.setEmpleado(null);
		}
		p.addPuesto(puesto);
		e.addPlanta(p);
		c.addEdificio(e);
		return c;
	}

	// MÉTODOS PARA CONSTRUIR CADA ENTIDADES CON LOS DATOS RECOGIDOS DE CADA CELDA

	private Registro buildRegistro() {
		Registro reg = new Registro();
		reg.setVersion("1.0.0");
		return reg;
	}

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
		if (registroGuardado.getComplejos().size() > 0) {
			if (!registroGuardado.getComplejos().get(0).getNombreComplejo().isEmpty()
					&& registroGuardado.getComplejos().get(0).getNombreComplejo().contains(tabla.getNombreComplejo())) {
				// si esta creado y coincide en nombre lo usamos
				c = registroGuardado.getComplejos().get(0);
			} else if (!registroGuardado.getComplejos().get(0).getNombreComplejo().isEmpty() && !registroGuardado
					.getComplejos().get(0).getNombreComplejo().contains(tabla.getNombreComplejo())) {
				c = this.buildComplejo(tabla, registroGuardado);
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
		// si la lista de edificios del complejo está vacía

		boolean encontrado = false;
		int contador = 0;
		//si el tamaño de la lista es igual a 0 -> se construye una planta
		if (listaPlanta.size() == 0) {
			p = this.buildPlanta(tabla, e, r);
			encontrado = true;
		}
		while (!encontrado) {
			// vamos a recorrer la lista y comprobar los nombres (para ver si está creada la planta
			if (listaPlanta.get(contador).getNombrePlanta().contains(Long.toString(tabla.getNombrePlanta()))) {
				p = listaPlanta.get(contador);
				encontrado = true;
			// si el contador se pasa del tamaño de la lista -> crea una planta
			} else if (contador == listaPlanta.size() - 1) {
				p = this.buildPlanta(tabla, e, r);
				encontrado = true;
			} else {
				contador++;
			}
		}

		return p;
	}
	
	//Metodos auxiliares del Puesto

	private Puesto buildPuesto(TablaModelo tabla, Planta p, Registro r) {
		// los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Puesto puesto = new Puesto();
		puesto.setIdPuesto(tabla.getIdPuesto());
		puesto.setPlanta(p);
		puesto.setRegistro(r);
		puesto.setOcupado(false);
		return puesto;
	}
	
	//Métodos auxiliares del Empleado

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
	private Ue seleccionarUe(TablaModelo tabla, Registro r) {
		Ue ueFound = null;
		List<Ue> ue = (List<Ue>) ueDao.findAll();
		Iterator<Ue> ueIterator = ue.iterator();
		boolean encontrado = false;
		int contador = 0;
		if (tabla.getUe() == null) {
			return null;
		}
		//si el tamaño es 0 es que no hay ue
		if (ue.size() == 0) {
			ueFound = this.buildUe(tabla, r);
			encontrado = true;
		}
		
		while (ueIterator.hasNext() && !encontrado) {
			// vamos a recorrer la lista y comprobar los nombres
			Ue iter = ueIterator.next();
			//si es null es que no hay ue (aunque en ocasiones aunque no haya coge un null)
			if (iter == null) {
				ueFound = this.buildUe(tabla, r);
				encontrado = true;
			// si el id de la iteración es igual al id de la tabla -> no creamos ue
			} else if (iter.getIdUe().contains(tabla.getUe())) {
				ueFound = iter;
				encontrado = true;
			// si el contador se pasa del tamaño de la lista de ue -> se crea un ue
			} else if (contador == ue.size() - 1) {
				ueFound = this.buildUe(tabla, r);
				encontrado = true;
			} else {
				contador++;
			}
		}
		//guardamos ue en la base de datos
		ueFound = ueDao.save(ueFound);
		return ueFound;
	}
}
