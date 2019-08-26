package es.indra.censo.docreader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.controllers.ComplejoController;
import es.indra.censo.dao.IRegistroDao;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Registro;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestExcelReader {
	
	private Logger log = LoggerFactory.getLogger(ComplejoController.class);
	
	@Autowired
	private ExcelReader reader;
	
	@Autowired
	private IRegistroDao rDao;
	
	private Workbook workbook;
	
	@Before
	public void before() {
		File censoTest = new File("./src/main/resources/Test.xlsx");

		try {
			FileInputStream excelFile = new FileInputStream(censoTest);
			this.workbook = WorkbookFactory.create(excelFile);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		
	}

/**
 * testeo de guardado de filas de la tabla excel
 */
	@Test
	@Transactional
	public void testReader() {
		try {
			int contador = 0;
			boolean puestoOcupado = false;
			this.reader.reader(this.workbook, "1.0.2", new Locale("es", "ES"), "Jesus");
			List<Registro> registro = (List<Registro>) rDao.findAll();
			assertNotNull(registro);
			assertFalse((registro.isEmpty()));
			assertNotNull((registro.get(0)));
			assertNotNull((registro.get(0).getComplejos().get(0)
					.getEdificios().get(0)
					.getPlantas().get(0).getPuestos().get(0)));
			List<Puesto> puestos = registro.get(0).getComplejos().get(0)
					.getEdificios().get(0)
					.getPlantas().get(0).getPuestos();
			Puesto p = null;
			while(!puestoOcupado && puestos.size() > contador) {
				p = puestos.get(contador);
				if (!p.isOcupado()) {
					contador++;
				} else {
					puestoOcupado = true;
				}
				
			}
			assertNotNull(p);
			assertNotNull(p.getEmpleado());
			assertNotNull((registro.get(0).getUes().get(0)));
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		
	}

}
