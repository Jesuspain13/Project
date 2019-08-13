package es.indra.censo.docreader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

@Component
public class ValidadorColumnasExcel {

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
		boolean condiciónFinal = true;
		int i = 0;
		while (i<13 && condiciónFinal) {
			condiciónFinal = this.comprobarOrden(firstRow.getCell(i));
			i++;
		}
		return condiciónFinal;
	}
	
	private boolean comprobarOrden(Cell cell) {
		int i = cell.getColumnIndex();
		boolean condiciónFinal = true;
		if (condiciónFinal) {
			switch(i) {
			case 0:
				condiciónFinal = this.checkIdComplejo(cell.getStringCellValue());
				break;
			case 1:
				condiciónFinal = this.checkNombreComplejo(cell.getStringCellValue());
				break;
			case 2:
				condiciónFinal = this.checknombreEdificio(cell.getStringCellValue());
				break;
			case 3:
				condiciónFinal = this.checkNombrePlanta(cell.getStringCellValue());
				break;
			case 4:
				condiciónFinal = this.checkIdPuesto(cell.getStringCellValue());
				break;
			case 5:
				condiciónFinal = this.checkNumeroEmpleado(cell.getStringCellValue());
				break;
			case 6:
				condiciónFinal = this.checkNick(cell.getStringCellValue());
				break;
			case 7:
				condiciónFinal = this.checkNombreEmpleado(cell.getStringCellValue());
				break;
			case 8:
				condiciónFinal = this.checkApellidoEmpleado(cell.getStringCellValue());
				break;
			case 9:
				condiciónFinal = this.checkUe(cell.getStringCellValue());
				break;
			case 10:
				condiciónFinal = this.checkNombreUe(cell.getStringCellValue());
				break;
			case 11:
				condiciónFinal = this.checkUeRepercutible(cell.getStringCellValue());
				break;
			case 12:
				condiciónFinal = this.checkNombreUeRepercutible(cell.getStringCellValue());
				break;
				
			}
		}
		return condiciónFinal;
	}

	
}
