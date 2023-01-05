package br.com.telefone.pdf.util;


import org.springframework.web.multipart.MultipartFile;

public class ValidaUtil {

	public static boolean isPdfValido(MultipartFile pdf) throws Exception{
		
		if(pdf.getContentType().contains("pdf") ) {
			return true;
		}
		throw new Exception( String.format("Arquivo de pdf inválido %s", pdf.getOriginalFilename() ) );
	}
	
    public static boolean isImagemValido(MultipartFile pdf) throws Exception{
		
		if(pdf.getContentType().contains("jpeg") || 
				pdf.getContentType().contains("png")|| 
				pdf.getContentType().contains("jpg") ) {
			return true;
		}
		throw new Exception( String.format("Arquivo de pdf inválido %s", pdf.getOriginalFilename() ) );
	}
}

