package es.indra.censo.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.indra.censo.dao.IEmpleadoDao;
import es.indra.censo.dao.IRegistroDao;
import es.indra.censo.dao.IUeDao;
import es.indra.censo.docreader.ExcelReader;
import es.indra.censo.docreader.ReaderFromView;

@Service
public class DocReaderServiceImpl implements IDocReaderService {
	
	@Autowired
	private IRegistroDao rDao;
	
	@Autowired
	private IEmpleadoDao empDao;
//	
	@Autowired
	private IUeDao ueDao;
	
	private ReaderFromView readerFromUpload = new ReaderFromView();
	
	@Autowired
	private ExcelReader reader;

	@Override
	public void readDocument(MultipartFile file) {
		Workbook wb = readerFromUpload.readFromMultiparFile(file);
		reader.reader(wb);
	}

}
