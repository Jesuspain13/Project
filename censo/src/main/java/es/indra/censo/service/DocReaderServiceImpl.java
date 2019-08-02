package es.indra.censo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.indra.censo.dao.IEmpleadoDao;
import es.indra.censo.dao.IRegistroDao;
import es.indra.censo.dao.IUeDao;
import es.indra.censo.docreader.ExcelReader;

@Service
public class DocReaderServiceImpl implements IDocReaderService {
	
	@Autowired
	private IRegistroDao rDao;
	
	@Autowired
	private IEmpleadoDao empDao;
//	
	@Autowired
	private IUeDao ueDao;

	@Override
	public void readDocument() {
		ExcelReader excelReader = new ExcelReader(rDao, ueDao, empDao);
		excelReader.reader();
		
	}

}
