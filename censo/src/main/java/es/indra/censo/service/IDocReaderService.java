package es.indra.censo.service;

import java.util.Locale;

import org.springframework.web.multipart.MultipartFile;

public interface IDocReaderService {

	public void readDocument(MultipartFile file, String version, Locale locale) throws Exception;

}
