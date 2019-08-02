package es.indra.censo.controllers;

import java.io.File;
import java.io.InputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/doc")
public class UploadExcelController {
	
	@GetMapping("/upload")
	public String uploadExcel() {
		return "upload";
	}
	
	@PostMapping("/upload")
	public String uploadExcel(@RequestParam("file") MultipartFile file) {
		try {
		InputStream in = file.getInputStream();
	    File currDir = new File(".");
	    String path = currDir.getAbsolutePath();
	    return "redirect:/puesto/listar";
		} catch (Exception ex) {
			 return "redirect:/puesto/listar";
		}
	   
	}
}
