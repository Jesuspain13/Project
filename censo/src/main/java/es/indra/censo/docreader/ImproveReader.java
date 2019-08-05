//package es.indra.censo.docreader;
//
//import java.util.Iterator;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import es.indra.censo.dao.IComplejoDao;
//import es.indra.censo.dao.IEmpleadoDao;
//import es.indra.censo.dao.IRegistroDao;
//import es.indra.censo.dao.IUeDao;
//import es.indra.censo.model.Complejo;
//import es.indra.censo.model.Registro;
//
//@Service
//public class ImproveReader {
//
//	@Autowired
//	private IRegistroDao rDao;
//
//	@Autowired
//	private IUeDao ueDao;
//
//	@Autowired
//	private IEmpleadoDao empDao;
//	
//	@Autowired
//	private IComplejoDao complejoDao;
//	
//	public void reader(Workbook workbook) {
//		try {
//			Sheet sheet = workbook.getSheetAt(0);
//			Iterator<Row> rows = sheet.rowIterator();
//			int contador = 0;
//			Registro r;
//			r = this.buildRegistro();
//			//Registro registroGuardado = rDao.save(r);
//
//			this.recorrerFilas(workbook, rows, rDao, r);
//
//			workbook.close();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//	
//	private Registro buildRegistro() {
//		Registro reg = new Registro();
//		reg.setVersion("1.0.0");
//		return reg;
//	}
//	
//	private Registro recorrerFilas(Workbook workbook, Iterator<Row> rows, IRegistroDao rDao,
//			Registro registroGuardado) {
//		int contador = 0;
//		Complejo c = null;
//		rDao.save(registroGuardado);
//		while (rows.hasNext()) {
//			// contador para empezar a guardar valores a partir de la primera linea (la
//			// primera no incluye datos solo titulos)
//			
//			if (contador > 0) {
//				if (c == null) {
//					c = this.recorrerCeldasDeFila(workbook, rows.next(), rDao, registroGuardado);
//					
//					registroGuardado.addComplejo(c);
//				} else {
//					this.recorrerCeldasDeFila(workbook, rows.next(), rDao, registroGuardado);
//				}
//			} else if (contador == 0) {
//				// La primera fila son los titulos de las columnas
//				rows.next();
//
//			}
//			contador++;
//		}
//
//		return registroGuardado;
//	}
//	
//	private 
//	
//	private Complejo recorrerCeldasDeFila(Workbook wb, Row row, IRegistroDao rDao, Registro registroGuardado) {
//		Iterator<Cell> cells = row.cellIterator();
//		TablaModelo tabla = new TablaModelo();
//
//		// método que recoge los valores de una fila
//		tabla.asignarValores(cells);
//
//		// }
//		Complejo c = this.constructorEntidades(tabla, rDao, registroGuardado);
//		return c;
//
//	}
//
//}
