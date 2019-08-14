package es.indra.censo.docreader;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidadorColumnasExcel {
	
	Logger log = LoggerFactory.getLogger(ValidadorColumnasExcel.class);
	
	public ValidadorColumnasExcel() {
		
	}

	private boolean checkIdComplejo(String idComplejo) {
		if (idComplejo.contains("IDCOMPLEJO")) {
			return true;
		}
		return false;
	}
	
	private boolean checkNombreComplejo(String nombreComplejo) {
		if (nombreComplejo.contains("NOMBRECOMPLEJO")) {
			return true;
		}
		return false;
	}
	
	private boolean checknombreEdificio(String nombreEdificio) {
		if (nombreEdificio.contains("NOMBREEDIFICIO")) {
			return true;
		}
		return false;
	}
	
	private boolean checkNombrePlanta(String nombrePlanta) {
		if (nombrePlanta.contains("NOMBREPLANTA")) {
			return true;
		}
		return false;
	}
	
	private boolean checkIdPuesto(String idPuesto) {
		if (idPuesto.contains("IDPUESTO")) {
			return true;
		}
		return false;
	}
	private boolean checkNumeroEmpleado(String numeroEmpleado) {
		if (numeroEmpleado.contains("NUMERO_EMPLEADO")) {
			return true;
		}
		return false;
	}
	
	private boolean checkNick(String nick) {
		if (nick.contains("NICK")) {
			return true;
		}
		return false;
	}
	
	private boolean checkNombreEmpleado(String nombreEmpleado) {
		if (nombreEmpleado.contains("NOMBRE")) {
			return true;
		}
		return false;
	}
	
	private boolean checkApellidoEmpleado(String apellidoEmpleado) {
		if (apellidoEmpleado.contains("APELLIDOS")) {
			return true;
		}
		return false;
	}
	
	private boolean checkUe(String ue) {
		if (ue.contains("UE")) {
			return true;
		}
		return false;
	}
	
	private boolean checkNombreUe(String nombreUe) {
		if (nombreUe.contains("NOMBRE_UE")) {
			return true;
		}
		return false;
	}
	
	private boolean checkUeRepercutible(String ueRepercutible) {
		if (ueRepercutible.contains("UE_REPERCUTIBLE")) {
			return true;
		}
		return false;
	}
	
	private boolean checkNombreUeRepercutible(String nombreUeRepercutible) {
		if (nombreUeRepercutible.contains("NOMBRE_UE_REPERCUTIBLE")) {
			return true;
		}
		return false;
	}
	
	public boolean iterarCeldadYComprobar(Row firstRow) {
		try {
			boolean condicionFinal = true;
			int i = 0;
			Iterator<Cell> cells = firstRow.cellIterator();
			Cell cell;
			while (i< 13&& condicionFinal) {
				cell = cells.next();
				condicionFinal = this.comprobarOrden(cell, i);
				i++;
			}
			return condicionFinal;
		} catch (OrdenColumnasException ex) {
			return false;
		}catch (Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
	}
	
	private boolean comprobarOrden(Cell cell, int indice) throws Exception {
		try {
			int i = indice;
			boolean condicionFinal = true;
			
			String value;
			
			if (condicionFinal) {
				value = cell.getStringCellValue();
				switch(i) {
				
				case 0:
					condicionFinal = this.checkIdComplejo(value);
					break;
				case 1:
					condicionFinal = this.checkNombreComplejo(value);
					break;
				case 2:
					condicionFinal = this.checknombreEdificio(value);
					break;
				case 3:
					condicionFinal = this.checkNombrePlanta(value);
					break;
				case 4:
					condicionFinal = this.checkIdPuesto(value);
					break;
				case 5:
					condicionFinal = this.checkNumeroEmpleado(value);
					break;
				case 6:
					condicionFinal = this.checkNick(value);
					break;
				case 7:
					condicionFinal = this.checkNombreEmpleado(value);
					break;
				case 8:
					condicionFinal = this.checkApellidoEmpleado(value);
					break;
				case 9:
					condicionFinal = this.checkUe(value);
					break;
				case 10:
					condicionFinal = this.checkNombreUe(value);
					break;
				case 11:
					condicionFinal = this.checkUeRepercutible(value);
					break;
				case 12:
					condicionFinal = this.checkNombreUeRepercutible(value);
					break;
					
				}
			}
			return condicionFinal;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
	}

	
}
