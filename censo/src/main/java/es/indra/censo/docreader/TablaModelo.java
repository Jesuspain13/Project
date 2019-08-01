package es.indra.censo.docreader;

import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;

public class TablaModelo {

	private String idComplejo;
	private String nombreComplejo;
	private String nombreEdificio;
	private Long nombrePlanta;
	private Long idPuesto;
	private String numeroEmpleado;
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

	public Long getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(Long idPuesto) {
		this.idPuesto = idPuesto;
	}

	public String getNumeroEmpleado() {
		return numeroEmpleado;
	}

	public void setNumeroEmpleado(String numeroEmpleado) {
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
		return nombreUeRepercutible;
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

	public void asignarValores(Workbook wb, Iterator<Cell> cells) {
		int i = 0;
		while(cells.hasNext()) {
			
			switch(i){
			case 0:
				setIdComplejo(cells.next().getStringCellValue());
				break;
			case 1:
				setNombreComplejo(cells.next().getStringCellValue());
				break;
			case 2:
				setNombreEdificio(cells.next().getStringCellValue());
				break;
			case 3:
				setNombrePlanta(Math.round(cells.next().getNumericCellValue()));
				break;
			case 4:
				setIdPuesto(Math.round(cells.next().getNumericCellValue()));
				break;
			case 5:
				setNumeroEmpleado(cells.next().getStringCellValue());
				break;
			case 6:
				setNick(cells.next().getStringCellValue());
				break;
			case 7:
				setNombreEmpleado(cells.next().getStringCellValue());
				break;
			case 8:
				setApellidosEmpleado(cells.next().getStringCellValue());
				break;
			case 9:
				setUe(cells.next().getStringCellValue());
				break;
			case 10:
				setNombreUe(cells.next().getStringCellValue());
				break;
			case 11:
				setUeRepercutible(cells.next().getStringCellValue());
				break;
			case 12:
				setNombreUeRepercutible(cells.next().getStringCellValue());
				break;
			case 14:
				setWpStd(cells.next().getStringCellValue());
				break;
//			case 16:
//				setFechaCenso(new Date(this.dateCellFormatter(wb, cells.next())));
//				break;
			default:
				break;
			}
			i++;
			
		}
			
		
	}
		private String dateCellFormatter(Workbook workbook, Cell cell) {
			//poder extraer las fechas (sin que ejecuten matemáticamente)
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			CellValue cv = evaluator.evaluate(cell);
			double dv = cv.getNumberValue();
			Date date = HSSFDateUtil.getJavaDate(dv);

		    String df = cell.getCellStyle().getDataFormatString();

		    return new CellDateFormatter(df).format(date); 
		}
		
	}


