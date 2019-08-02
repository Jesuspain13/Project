package es.indra.censo.docreader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public class ReaderFromView {
	
	public void readFromMultiparFile(MultipartFile file) {
		try {
			InputStream in = file.getInputStream();
		    File currDir = new File("datos_edificios");
		    String path = currDir.getAbsolutePath();
		    String fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
		    FileOutputStream f = new FileOutputStream(fileLocation);
		    int ch = 0;
		    while ((ch = in.read()) != -1) {
		        f.write(ch);
		    }
		    f.flush();
		    f.close();
		} catch (Exception ex) {
			System.out.println("ERROR EN LA OBTENCIÓN DE LOS DATOS DE LA TABLA EXCEL");
			ex.printStackTrace();
		}
		
	   

	}
	
}
