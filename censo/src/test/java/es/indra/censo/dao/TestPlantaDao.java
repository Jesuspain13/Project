package es.indra.censo.dao;

import static org.junit.Assert.assertEquals;
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

import es.indra.censo.controllers.ComplejoController;
import es.indra.censo.docreader.ExcelReader;
import es.indra.censo.model.Planta;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPlantaDao {
	
	private File censoTest;
	private Logger log = LoggerFactory.getLogger(ComplejoController.class);
	
	@Autowired(required = true)
	private IPlantaDao pDao;
	
	@Autowired
	private ExcelReader reader;

	@Before
	public void before() {

		censoTest = new File("./src/main/resources/SitiosEdificioTest.xlsx");

		try {
			FileInputStream excelFile = new FileInputStream(censoTest);
			Workbook workbook = WorkbookFactory.create(excelFile);
			reader.reader(workbook, "1.0.2", new Locale("es", "ES"));
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
	}
	
	@Test
	public void TestPlantafindByIdAndRegistro() {
		

		List<Planta> plantas = (List<Planta>) pDao.findAll();
		Planta plantaParaTestear = plantas.get(0);
		Planta plantaATestear = pDao.findByIdPlantaAndRegistro(plantaParaTestear.getId(),
				plantaParaTestear.getRegistro().getIdRegistro());

		assertNotNull(plantaATestear);
		assertEquals(plantaATestear.getId(), plantaParaTestear.getId());

	}

	@Test
	public void TestPlantaNombrePlantaAndRegistro() {

		List<Planta> plantas = (List<Planta>) pDao.findAll();
		Planta plantaParaTestear = plantas.get(0);
		Planta plantaATestear = pDao.findByNombrePlantaAndRegistro(plantaParaTestear.getNombrePlanta(),
				plantaParaTestear.getRegistro().getIdRegistro());

		assertNotNull(plantaATestear);
		assertEquals(plantaATestear.getNombrePlanta(), plantaParaTestear.getNombrePlanta());


	}
	
	

}
