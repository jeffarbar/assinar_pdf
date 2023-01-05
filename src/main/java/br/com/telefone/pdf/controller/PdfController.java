package br.com.telefone.pdf.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.telefone.pdf.enums.PosicaoImagemEnum;
import br.com.telefone.pdf.service.PdfService;



@RestController
@RequestMapping("/pdf")
public class PdfController extends Controller {
	
	@Autowired
	private PdfService pdfService;
	
	
	@PostMapping("/addImagem")
	public ResponseEntity<Resource> uploadFile(
			@RequestParam("pdf") MultipartFile pdf, 
			@RequestParam("png/jpeg/jpg") MultipartFile png, 
			@RequestParam PosicaoImagemEnum posicaoImagem) {

	    try {

	    	Resource resource = pdfService.assinar(pdf, png, posicaoImagem);
	    	return ResponseEntity.ok()
	    		 .contentType(MediaType.parseMediaType("application/pdf"))
	    		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"imagem_" + pdf.getOriginalFilename() + "\"")
	    	    .body(resource);
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
	    }
	}
}
