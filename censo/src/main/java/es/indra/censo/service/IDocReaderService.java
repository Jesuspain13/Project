package es.indra.censo.service;

import java.util.Locale;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface IDocReaderService {

	public Map<String, Object> readDocument(MultipartFile file, String version, Locale locale, String nombreAutor) throws Exception;

}
