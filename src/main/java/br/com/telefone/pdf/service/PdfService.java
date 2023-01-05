package br.com.telefone.pdf.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

@Service
public class PdfService {
	
	
	private static final float larguraAssinatura = 200; 
	
	private static final float alturaAssinatura = 200;	
	
	private static final float posicaoX = 2;
	
	private static final  float posicaoY = 2;
	
	
	public static void main(String[] args) {
		
		PdfService pdf = new PdfService();
		
		try {
			
			final String caminho = "C:\\Users\\80845262\\Documents\\trabalho\\projetos\\pdf_editar\\arquivos\\";
			final String nome_entrada = "original.pdf";
		    final String nome_saida = "saida.pdf";
			
			ByteArrayOutputStream bao = pdf.assinar( caminho.concat(nome_entrada) );
			
			Files.write(bao.toByteArray(), Paths.get(caminho, nome_saida).toFile());
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Resource assinar(MultipartFile pdf) throws IOException, BadElementException, DocumentException, URISyntaxException {
		
		ByteArrayOutputStream baos =  assinar( new PdfReader( pdf.getInputStream()));
		
		byte[] bytes = baos.toByteArray();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		return new InputStreamResource(inputStream);

	}

	
	public ByteArrayOutputStream assinar(String pdf) throws IOException, BadElementException, DocumentException, URISyntaxException {
		
		 return assinar( new PdfReader(pdf) );
	}
		
	public ByteArrayOutputStream assinar(PdfReader reader) throws IOException, BadElementException, DocumentException, URISyntaxException {
				
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		PdfStamper stamper = new PdfStamper(reader, baos);
		
		int qdtPage = reader.getNumberOfPages();
	    for (int i=1; i<=qdtPage; i++){
	    	
	    	PdfContentByte over = stamper.getOverContent(i);
	    	over.addImage(getAssinatura( ));
	    }		
		stamper.close();
		baos.flush();
		baos.close();
		return baos;
	}
	
	private Image getAssinatura() throws BadElementException, IOException, URISyntaxException {
		
		Path path = Paths.get(ClassLoader.getSystemResource("assinatura.png").toURI());
		
		Image image = Image.getInstance(path.toAbsolutePath().toString());
				
		image.scaleAbsolute(larguraAssinatura, alturaAssinatura);
        image.setAbsolutePosition(posicaoX, posicaoY); 
		
		return image;
		
	}
}
