package es.indra.censo.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.indra.censo.docreader.ExcelReader;
import es.indra.censo.docreader.ReaderFromView;
import es.indra.censo.model.errores.excel.Fila;
import es.indra.censo.service.errorexcel.IFilasExcelSvc;

@Service
public class DocReaderServiceImpl implements IDocReaderService {

	private Logger log = LoggerFactory.getLogger(DocReaderServiceImpl.class);

	private ReaderFromView readerFromUpload = new ReaderFromView();

	@Autowired
	private ExcelReader reader;
	
	@Autowired
	private IFilasExcelSvc filasExcelSvc;

	@Override
	public Map<String, Object> readDocument(MultipartFile file, String version, Locale locale, String nombreAutor) throws Exception {
		try (Workbook wb = readerFromUpload.readFromMultiparFile(file)) {
			Map<String, Object> result = new HashMap<String, Object>();
			Integer idRegistro = reader.reader(wb, version, locale, nombreAutor);
			result.put("idRegistro", idRegistro);
			Page<Fila> page =  filasExcelSvc.encontrarErroresDelRegistro(idRegistro, 0);
			result.put("page", page);
			return result;
		} catch (Exception ex) {
			log.error(ex.getCause().toString());
			throw new Exception(ex);
		}
	}


}