package es.indra.censo.docreader;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class TablaModelo {

	private boolean teletrabajo;

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

	public boolean isTeletrabajo() {
		return teletrabajo;
	}

	public void setTeletrabajo(boolean teletrabajo) {
		this.teletrabajo = teletrabajo;
	}

	/**
	 * Método que recorre un iterador de celdas y asigna los valores a los atributos
	 * de clase
	 * 
	 * @param wb
	 * @param cells
	 */
	public void asignarValores(Row row) {
		try {
			int i = 0;
			this.teletrabajo = false;
			boolean teletrab = this.teletrabajo;
			boolean finTesteo = false;
			Cell cell;
			while (i < 14 && !teletrab && row != null) {
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
					// Cell cell = cells.next();
	
					// identificador alfanumerico
					if (cell.getCellType().toString().contains("STRING")) {
						
						setIdPuesto(cell.getStringCellValue());
					} else {
						long res = (Math.round(cell.getNumericCellValue()));
						setIdPuesto(Long.toString(res));
	
					}
					break;
				// NUMERO EMPLEADO
				case 5:
					// Cell cell5 = cells.next();
					// si la celda es un string va a ser teletrabajo
					if (cell == null) {
						setNumeroEmpleado(null);
					} else if (cell.getCellType().toString().contains("STRING")
							&& cell.getStringCellValue().contains("TELETRAB")) {
						this.teletrabajo = true;
						teletrab = true;
					} else if (cell.getCellType().toString().contains("STRING")) {
						setNumeroEmpleado(null);
					} else {
						// sino va a ser el numero de empleado
	
						setNumeroEmpleado((long) cell.getNumericCellValue());
	
					}
					break;
				// NICK
				case 6:
					// Cell cell6 = cells.next();
					if (cell == null) {
						setNick(null);
					} else if (cell.getStringCellValue().length() > 1) {
						setNick(cell.getStringCellValue());
					} else {
						setNick(null);
					}
					break;
				// NOMBRE EMPLEADO
				case 7:
					if (cell == null) {
						setNombreEmpleado(null);
					} else {
						setNombreEmpleado(cell.getStringCellValue());
					}
	
					break;
				// APELLIDOS EMPLEADO
				case 8:
					if (cell == null) {
						setApellidosEmpleado(null);
					} else {
						setApellidosEmpleado(cell.getStringCellValue());
					}
	
					break;
				// UE
				case 9:
					if (cell == null) {
						setUe(null);
					} else {
						setUe(cell.getStringCellValue());
					}
	
					break;
				// NOMBRE UE
				case 10:
					if (cell == null) {
						setNombreUe(null);
					} else {
						setNombreUe(cell.getStringCellValue());
					}
	
					break;
				// UE REPERCUTIBLE
				case 11:
					if (cell == null) {
						setUeRepercutible(null);
					} else {
						setUeRepercutible(cell.getStringCellValue());
					}
	
					break;
				// NOMBRE UE REPERCUTIBLE
				case 12:
					if (cell == null) {
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
	//			case 16:
	//				setFechaCenso(new Date(this.dateCellFormatter(wb, cells.next())));
	//				break;
				default:
					break;
				}
				i++;
	
			}
			
			
		}catch (Exception ex)
		{
			ex.printStackTrace();
			
		}
	

}
}
