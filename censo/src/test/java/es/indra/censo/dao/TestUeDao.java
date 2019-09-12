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
import es.indra.censo.model.Registro;
import es.indra.censo.model.Ue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUeDao {
	
	private File censoTest;
	private Logger log = LoggerFactory.getLogger(ComplejoController.class);

	@Autowired(required = true)
	private IUeDao ueDao;
	
	
	@Autowired(required = true)
	private IRegistroDao rDao;
	

	@Autowired
	private ExcelReader reader;
	
	private Registro registroParaTestear;
	private Integer idRegistro;
	private List<Ue> ueParaTestear;
	

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
		
		List<Registro> registros =(List<Registro>) rDao.findAll();
		
		registroParaTestear = registros.get(0);
		this.idRegistro = registroParaTestear.getIdRegistro();
		this.ueParaTestear = registroParaTestear.getUes();
		
			
		
	}

	@Test
	public void TestUefindByIdUe() {
		
		List<Ue> ues = (List<Ue>) ueDao.findAll();
		Ue ueParaTestear = ues.get(0);
		Integer idRegistro = ueParaTestear.getRegistro().getIdRegistro();
		Ue ueATestear = ueDao.findByIdUe(ueParaTestear.getId());
		
		assertNotNull(ueATestear);
		assertEquals(ueATestear.getIdUe(), ueParaTestear.getIdUe());
		

	}
	
	@Test
	@Transactional
	public void TestfindAllByIdRegistro() {
		List<Ue> uesATestear = ueDao.findAllByIdRegistro(registroParaTestear.getIdRegistro());
		
		assertNotNull(uesATestear);
		assertTrue(uesATestear.get(0).getRegistro().getIdRegistro().equals(registroParaTestear.getIdRegistro()));
		
		
		
	}

}


