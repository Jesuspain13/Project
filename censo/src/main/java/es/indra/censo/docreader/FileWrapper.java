package es.indra.censo.docreader;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import es.indra.censo.docreader.validator.ValidadorArchivo;

public class FileWrapper {
	
	@NotNull
	@NotEmpty
	public String version;
	
	@ValidadorArchivo
	public MultipartFile file;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	

}
