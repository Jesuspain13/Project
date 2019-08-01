package es.indra.censo.docreader;

import java.io.File;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import es.indra.censo.model.Complejo;
import es.indra.censo.model.Edificio;
import es.indra.censo.model.Empleado;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Registro;
import es.indra.censo.model.Ue;

public class ExcelReader {
	
	

	public static final String FILE_PATH = "D:/jjespana/Escritorio/CarpetaCompartida/"
			+ "ProyectoCenso/censo/src/main/resources/Sitios_Edificio.xlsx";
	
	public void reader() {
		try (Workbook workbook = WorkbookFactory.create(new File(FILE_PATH));) {
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			int contador = 0;
			while(rows.hasNext()) {
				//contador para empezar a guardar valores a partir de la primera linea (la primera no incluye datos solo titulos)
				
				if (contador > 0) {
					this.recorrerCeldasDeFila(workbook, rows.next());
				}
				else if (contador == 0) {
					//La primera fila son los titulos de las columnas
					rows.next();
					contador++;
				} else {
					break;
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * método para recorrer las celas de una fila
	 * @param rows
	 */
	private void recorrerCeldasDeFila(Workbook wb, Row row) {
		Iterator<Cell> cells = row.cellIterator();
		TablaModelo tabla = new TablaModelo();
		while(cells.hasNext()) {
			//método que recoge los valores de una fila
			tabla.asignarValores(wb, cells);
			System.out.println(tabla.getNombreComplejo());
			System.out.println(tabla.getNombreUe());
			System.out.println(tabla.getNombreUe());

			break;
		}

	}
	
	
	/**
	 * Método para creación de todas las entidades
	 * @param tabla
	 */
	private void constructorEntidades(TablaModelo tabla) {
		Registro r = this.buildRegistro();
		Complejo c = this.buildComplejo(tabla, r);
		Edificio e = this.buildEdificio(tabla, c, r);
		Planta p = this.buildPlanta(tabla, r);
		Puesto puesto = this.buildPuesto(p, r);
		Ue ue = this.buildUe(tabla, r);
		Empleado emp = this.buildEmpleado(tabla, ue, r);
		
		
	}

	//MÉTODOS PARA CONSTRUIR CADA ENTIDADES CON LOS DATOS RECOGIDOS DE CADA CELDA
	
	private Registro buildRegistro() {
		Registro reg = new Registro();
		reg.setVersion("1.0.0");
		return reg;
	}
	
	private Complejo buildComplejo(TablaModelo tabla, Registro r) {
		//los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Complejo comp = new Complejo();
		comp.setIdComplejo(tabla.getIdComplejo());
		comp.setNombreComplejo(tabla.getNombreComplejo());
		comp.setRegistro(r);
		return comp;
	}
	
	private Edificio buildEdificio(TablaModelo tabla, Complejo c, Registro r) {
		//los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Edificio edificio = new Edificio();
		edificio.setNombreEdificio(tabla.getNombreEdificio());
		edificio.setComplejo(c);
		edificio.setRegistro(r);
		return edificio;
	}
	
	private Planta buildPlanta(TablaModelo tabla, Registro r) {
		//los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Planta planta = new Planta();
		planta.setNombrePlanta(Long.toString(tabla.getNombrePlanta()));
		planta.setRegistro(r);
		return planta;
	}
	
	private Puesto buildPuesto(Planta p, Registro r) {
		//los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Puesto puesto = new Puesto();
		puesto.setPlanta(p);
		puesto.setRegistro(r);
		puesto.setOcupado(false);
		return puesto;
	}
	
	private Empleado buildEmpleado(TablaModelo tabla, Ue ue, Registro r) {
		//los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Empleado emp = new Empleado();
		emp.setNombre(tabla.getNombreEmpleado());
		emp.setApellido(tabla.getApellidosEmpleado());
		emp.setNumeroEmpleado(Integer.parseInt(tabla.getNumeroEmpleado()));
		emp.setNick(tabla.getNick());
		emp.setUe(ue);
		emp.setRegistro(r);
		return emp;
	}
	
	private Ue buildUe(TablaModelo tabla, Registro r) {
		//los dos primeros campos (1 y 2) de la tabla deben ser los datos del complejo
		Ue ue = new Ue();
		ue.setIdUe(tabla.getUe());
		ue.setNombreUe(tabla.getNombreUe());
		ue.setNombreUeRepercutible(tabla.getNombreUeRepercutible());
		ue.setUeRepercutible(tabla.getUeRepercutible());
		ue.setRegistro(r);
		return ue;
	}
}
