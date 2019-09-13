package es.indra.censo.docreader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.indra.censo.model.errores.excel.Celda;
import es.indra.censo.model.errores.excel.ColumnaExcel;
import es.indra.censo.model.errores.excel.Fila;
import es.indra.censo.model.errores.excel.TipoError;

public class TablaModelo {

	public TablaModelo(List<ColumnaExcel> columnasExcel, List<TipoError> tiposErrores) {
		this.columnasExcelExistentes = columnasExcel;
		this.tiposErroresExistentes = tiposErrores;
	}

	private Fila erroresFila = null;

	// nombre de los campos para la busqueda
	private List<String> camposConErrores = new ArrayList<String>();

	// campos que tienen errores en esta fila
	private List<ColumnaExcel> columnasExcelExistentes = new ArrayList<ColumnaExcel>();

	// tipos de errores
	private List<TipoError> tiposErroresExistentes;

	private Logger log = LoggerFactory.getLogger(TablaModelo.class);

	private String idComplejo;
	private String nombreComplejo;
	private String nombreEdificio;
	private Long nombrePlanta;
	private String idPuesto;
	private Long numeroEmpleado;
	private String nick;
	private String nombreEmpleado;
	private String apellidosEmpleado;
	private String ue;
	private String nombreUe;
	private String ueRepercutible;
	private String nombreUeRepercutible;
	private String wpStd;
	private String grupoFlexible;
	private Date fechaCenso;

	public String getIdComplejo() {
		return idComplejo;
	}

	public void setIdComplejo(String idComplejo) {
		this.idComplejo = idComplejo;
	}

	public String getNombreComplejo() {
		return nombreComplejo;
	}

	public void setNombreComplejo(String nombreComplejo) {
		this.nombreComplejo = nombreComplejo;
	}

	public String getNombreEdificio() {
		return nombreEdificio;
	}

	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
	}

	public Long getNombrePlanta() {
		return nombrePlanta;
	}

	public void setNombrePlanta(Long nombrePlanta) {
		this.nombrePlanta = nombrePlanta;
	}

	public String getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(String idPuesto) {
		this.idPuesto = idPuesto;
	}

	public Long getNumeroEmpleado() {
		return numeroEmpleado;
	}

	public void setNumeroEmpleado(Long numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getApellidosEmpleado() {
		return apellidosEmpleado;
	}

	public void setApellidosEmpleado(String apellidosEmpleado) {
		this.apellidosEmpleado = apellidosEmpleado;
	}

	public String getUe() {
		return ue;
	}

	public void setUe(String ue) {
		this.ue = ue;
	}

	public String getUeRepercutible() {
		return ueRepercutible;
	}

	public void setUeRepercutible(String ueRepercutible) {
		this.ueRepercutible = ueRepercutible;
	}

	public String getNombreUeRepercutible() {
		if (this.nombreUeRepercutible != null) {
			return nombreUeRepercutible;
		} else {
			return "";
		}

	}

	public void setNombreUeRepercutible(String nombreUeRepercutible) {
		this.nombreUeRepercutible = nombreUeRepercutible;
	}

	public String getWpStd() {
		return wpStd;
	}

	public void setWpStd(String wpStd) {
		this.wpStd = wpStd;
	}

	public Date getFechaCenso() {
		return fechaCenso;
	}

	public void setFechaCenso(Date fechaCenso) {
		this.fechaCenso = fechaCenso;
	}

	public String getNombreUe() {
		return nombreUe;
	}

	public void setNombreUe(String nombreUe) {
		this.nombreUe = nombreUe;
	}

	/**
	 * Método que recorre un iterador de celdas y asigna los valores a los atributos
	 * de clase
	 * 
	 * @param wb
	 * @param cells
	 */
	public Fila asignarValores(Row row) throws Exception {
		try {

			int i = 0;
			String campo;
			Cell cell;
			while (i < 14 && row != null) {
				cell = row.getCell(i);

				switch (i) {
				// ID COMPLEJO
				case 0:
					setIdComplejo(cell.getStringCellValue());
					break;
				// NOMBRE COMPLEJO
				case 1:
					setNombreComplejo(cell.getStringCellValue());
					break;
				// NOMBRE EDIFICIO
				case 2:
					setNombreEdificio(cell.getStringCellValue());
					break;
				// NOMBRE PLANTA
				case 3:
					setNombrePlanta(Math.round(cell.getNumericCellValue()));
					break;
				// ID PUESTO
				case 4:
					campo = "IDPUESTO";
					// identificador alfanumerico
					if (cell == null) {
						this.camposConErrores.add(campo);
						setNumeroEmpleado(null);
					} else if (cell.getCellType().toString().contains("STRING")) {
						setIdPuesto(cell.getStringCellValue());
					} else {
						long res = (Math.round(cell.getNumericCellValue()));
						setIdPuesto(Long.toString(res));

					}
					break;
				// NUMERO EMPLEADO
				case 5:
					campo = "NUMERO_EMPLEADO";
					// si la celda es un string va a ser teletrabajo

					if (cell == null) {
						this.camposConErrores.add(campo);
						setNumeroEmpleado(null);
					} else if (cell.getCellType().toString().contains("STRING")) {
						setNumeroEmpleado(null);
					} else {
						// sino va a ser el numero de empleado
						setNumeroEmpleado((long) cell.getNumericCellValue());

					}
					break;
				// NICK
				case 6:

					campo = "NICK";
					// identificador alfanumerico
					if (cell == null) {
						this.camposConErrores.add(campo);
						setNick(null);
					} else if (cell.getStringCellValue().length() > 1) {
						setNick(cell.getStringCellValue());
					} else {
						setNick(null);
					}
					break;
				// NOMBRE EMPLEADO
				case 7:
					campo = "NOMBRE";
					// identificador alfanumerico
					if (cell == null) {
						this.camposConErrores.add(campo);
						setNombreEmpleado(null);
					} else {
						setNombreEmpleado(cell.getStringCellValue());
					}

					break;
				// APELLIDOS EMPLEADO
				case 8:
					campo = "APELLIDOS";
					// identificador alfanumerico
					if (cell == null) {
						this.camposConErrores.add(campo);
						setApellidosEmpleado(null);
					} else {
						setApellidosEmpleado(cell.getStringCellValue());
					}

					break;
				// UE
				case 9:
					campo = "UE";
					// identificador alfanumerico
					if (cell == null) {
						this.camposConErrores.add(campo);
						setUe(null);
					} else {
						setUe(cell.getStringCellValue());
					}

					break;
				// NOMBRE UE
				case 10:
					campo = "NOMBRE_UE";
					// identificador alfanumerico
					if (cell == null) {
						this.camposConErrores.add(campo);
						setNombreUe(null);
					} else {
						setNombreUe(cell.getStringCellValue());
					}

					break;
				// UE REPERCUTIBLE
				case 11:
					campo = "UE_REPERCUTIBLE";
					// identificador alfanumerico
					if (cell == null) {
						this.camposConErrores.add(campo);
						setUeRepercutible(null);
					} else {
						setUeRepercutible(cell.getStringCellValue());
					}

					break;
				// NOMBRE UE REPERCUTIBLE
				case 12:
					campo = "NOMBRE_UE_REPERCUTIBLE";
					// identificador alfanumerico
					if (cell == null) {
						this.camposConErrores.add(campo);
						setNombreUeRepercutible(null);
					} else {
						setNombreUeRepercutible(cell.getStringCellValue());
					}

					break;
				// WP STD
				case 14:
					if (cell == null) {
						setWpStd(null);
					} else {
						setWpStd(cell.getStringCellValue());
					}

					break;

				default:
					break;
				}
				i++;

			}
			this.crearErrorDato(row.getRowNum());
			return this.erroresFila;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage().toString());
			throw new Exception(ex);

		}
	}

	private void crearErrorDato(int fila) {

		if (this.camposConErrores.size() < 1) {
			this.erroresFila = null;
		} else {
			this.erroresFila = new Fila(fila);
			List<Celda> celdasConFallos = this.comprobarErrores();
			this.erroresFila.setCeldas(celdasConFallos);
		}
	}

	private List<Celda> comprobarErrores() {
		List<Celda> celdasErroneas = new ArrayList<Celda>();
		ColumnaExcel columna;
		for (String s : this.camposConErrores) {
			Celda celdaErronea = new Celda();
			if (celdaErronea != null) {
				columna = this.comprobarNombreColumna(s);
				celdaErronea.setColumna(columna);
				celdaErronea.setError(this.tiposErroresExistentes.get(0));
				celdasErroneas.add(celdaErronea);
			}

		}
		return celdasErroneas;
	}

	/**
	 * recorre las columnas posibles y si alguna coincide en nombre la retorna
	 * 
	 * @return
	 */
	private ColumnaExcel comprobarNombreColumna(String nombreColumna) {
		ColumnaExcel columnaEncontrada = null;
		for (ColumnaExcel c : this.columnasExcelExistentes) {
			if (c.getNombreCampo().equals(nombreColumna)) {
				columnaEncontrada = c;
			}
		}
		return columnaEncontrada;
	}

}