package es.indra.censo.dao;

import static org.junit.Assert.assertEquals;
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
import es.indra.censo.model.Edificio;
import es.indra.censo.model.Planta;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPlantaDao {
	
	private File censoTest;
	private Logger log = LoggerFactory.getLogger(TestPlantaDao.class);
	
	@Autowired(required = true)
	private IPlantaDao pDao;
	
	@Autowired(required = true)
	private IEdificioDao eDao;
	
	@Autowired
	private ExcelReader reader;
	
	private Edificio edificioParaTestear;
	private Integer idRegistro;
	
	private List<Planta> plantasParaTestear;

	@Before
	@Transactional
	public void before() {

		censoTest = new File("./src/main/resources/Test.xlsx");

		try {
			FileInputStream excelFile = new FileInputStream(censoTest);
			Workbook workbook = WorkbookFactory.create(excelFile);
			reader.reader(workbook, "1.0.2", new Locale("es", "ES"), "admin");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getCause().toString());
		}
		
		List <Edificio> edificios = (List<Edificio>) eDao.findAll();
		edificioParaTestear = edificios.get(0);
		this.idRegistro = edificioParaTestear.getRegistro().getIdRegistro();
		this.plantasParaTestear = edificioParaTestear.getPlantas();
	}
	
	@Test
	public void TestPlantafindByIdPlantaAndRegistro() {
		

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
	
	@Test
	@Transactional
	public void TestPlantafindByIdEdificioAndRegistro() {
		

		
		List<Planta> plantasATestear = pDao.findByIdEdificioAndRegistro(edificioParaTestear.getIdEdificio(),
				this.idRegistro);
		
		for (int i=0; i< this.plantasParaTestear.size(); i++) {
			assertEquals(plantasATestear.get(i).getEdificio().getIdEdificio(), this.plantasParaTestear.get(i).getEdificio().getIdEdificio());
		}
		

		assertNotNull(plantasATestear);
		assertTrue(plantasATestear.get(0).getEdificio().getIdEdificio().equals(this.edificioParaTestear.getIdEdificio()));
		
		
	
	}
	
	

}
