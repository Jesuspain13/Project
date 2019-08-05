package es.indra.censo.controllers;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.indra.censo.docreader.ExcelReader;
import es.indra.censo.docreader.ReaderFromView;
import es.indra.censo.service.IDocReaderService;

@Controller
@RequestMapping("/doc")
public class UploadExcelController {
	
	@Autowired
	private IDocReaderService docReaderSvc;
	
	@GetMapping("/upload")
	public String uploadExcel() {
		return "upload";
	}
	
	@PostMapping("/upload")
	public String uploadExcel(@RequestParam("file") MultipartFile file) {
		try {
			docReaderSvc.readDocument(file);
		    return "upload";
		} catch (Exception ex) {
			 return "upload";
		}
	   
	}
}
