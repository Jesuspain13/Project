package es.indra.censo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.indra.censo.docreader.ExcelReader;

@SpringBootApplication
public class CensoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CensoApplication.class, args);
		ExcelReader reader = new ExcelReader();
		reader.reader();
	}

}
