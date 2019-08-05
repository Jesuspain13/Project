package es.indra.censo.docreader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.monitorjbl.xlsx.StreamingReader;

public class ReaderFromView {
	
	private Logger log = LoggerFactory.getLogger(ReaderFromView.class);
	
	/**
	 * recibe el archivo, obtiene un stream de datos y convierte este a un workbook
	 * @param file
	 * @return
	 */
	public Workbook readFromMultiparFile(MultipartFile file) {
		try {
			
			InputStream is = file.getInputStream();
			Workbook workbook = StreamingReader.builder()
			        .rowCacheSize(100) 
			        .bufferSize(4096)
			        .open(is); 
			return workbook;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return null;
		}
	}	
	
}
