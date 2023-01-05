package br.com.telefone.pdf.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import br.com.telefone.pdf.service.PdfService;
import springfox.documentation.service.ResponseMessage;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;



@RestController
@RequestMapping("/pdf")
public class PdfController {
	
	@Autowired
	private PdfService pdfService;
	
	
	@PostMapping("/assinar")
	public ResponseEntity<Resource> uploadFile(@RequestParam("file") MultipartFile file) {

	    try {
	    	
	    	Resource resource = pdfService.assinar(file);
	    	return ResponseEntity.ok()
	    		 .contentType(MediaType.parseMediaType("application/pdf"))
	    		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"assinado_" + file.getOriginalFilename() + "\"")
	    	    .body(resource);
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
	    }
	}
}
