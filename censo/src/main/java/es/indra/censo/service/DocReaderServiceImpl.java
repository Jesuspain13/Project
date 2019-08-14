package es.indra.censo.service;

import java.util.Locale;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.indra.censo.docreader.ExcelReader;
import es.indra.censo.docreader.ReaderFromView;

@Service
public class DocReaderServiceImpl implements IDocReaderService {
	
	private ReaderFromView readerFromUpload = new ReaderFromView();
	
	@Autowired
	private ExcelReader reader;

	@Override
	public void readDocument(MultipartFile file, String version, Locale locale)  throws Exception {
		try {
			Workbook wb = readerFromUpload.readFromMultiparFile(file);
			reader.reader(wb, version, locale);
		}catch (Exception ex) {
			throw new Exception(ex);
		}
	}

}
