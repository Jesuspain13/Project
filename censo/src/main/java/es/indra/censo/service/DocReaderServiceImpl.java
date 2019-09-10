package es.indra.censo.service;

import java.util.Locale;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.indra.censo.docreader.ExcelReader;
import es.indra.censo.docreader.ReaderFromView;

@Service
public class DocReaderServiceImpl implements IDocReaderService {

	private Logger log = LoggerFactory.getLogger(ComplejoServiceImpl.class);

	private ReaderFromView readerFromUpload = new ReaderFromView();

	@Autowired
	private ExcelReader reader;

	@Override
	public void readDocument(MultipartFile file, String version, Locale locale, String nombreAutor) throws Exception {
		try (Workbook wb = readerFromUpload.readFromMultiparFile(file)) {
			
			reader.reader(wb, version, locale, nombreAutor);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

}
