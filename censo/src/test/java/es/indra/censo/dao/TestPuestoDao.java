package es.indra.censo.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import es.indra.censo.docreader.ExcelReader;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Registro;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPuestoDao {
	
	private File censoTest;
	private Logger log = LoggerFactory.getLogger(ComplejoController.class);

	@Autowired(required = true)
	private IPuestoDao pDao;
	
	@Autowired
	private ExcelReader reader;
	
	@Autowired
	private IPlantaDao plaDao;

	@Before
	public void before() {

		censoTest = new File("./src/main/resources/Test.xlsx");

		try {
			FileInputStream excelFile = new FileInputStream(censoTest);
			Workbook workbook = WorkbookFactory.create(excelFile);
			reader.reader(workbook, "1.0.2", new Locale("es", "ES"), "admin");
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		
		
	}
	
	@Test
	@Transactional
	public void TestPuestofindByPlantaAndRegistro() {
		List<Planta> plantas = (List<Planta>) plaDao.findAll();
		Planta plantaParaTestear = plantas.get(0);
		Registro registroABuscar = plantaParaTestear.getRegistro();
		List<Puesto> puestosParaTestear = plantaParaTestear.getPuestos();
		
		List<Puesto> puestoATestear = pDao.findByPlantaAndRegistro(plantaParaTestear.getNombrePlanta(),
				registroABuscar.getIdRegistro());
		
		assertNotNull(puestoATestear);
		assertTrue(puestoATestear.containsAll(puestosParaTestear));

	}

}
