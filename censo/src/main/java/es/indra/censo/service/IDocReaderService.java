package es.indra.censo.service;

import org.springframework.web.multipart.MultipartFile;


public interface IDocReaderService {

	public void readDocument(MultipartFile file) throws Exception;
	
}
