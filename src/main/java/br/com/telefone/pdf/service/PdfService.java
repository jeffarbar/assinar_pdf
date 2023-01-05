package br.com.telefone.pdf.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

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

import br.com.telefone.pdf.enums.PosicaoImagemEnum;
import br.com.telefone.pdf.util.PosicaoImagemUtil;
import br.com.telefone.pdf.util.ValidaUtil;

@Service
public class PdfService {
	
	
	public static final float LARGURA_IMAGEM = 200; 
	
	public static final float ALTURA_IMAGEM = 200;	
	
	public static final float POSICAO_INICIAL_IMAGEM = 1;
	
	public static void main(String[] args) {
		
		PdfService pdf = new PdfService();
		
		try {
			
			final String caminho = "C:\\Users\\80845262\\Documents\\trabalho\\projetos\\pdf_editar\\arquivos\\";
			final String nome_entrada = "original.pdf";
		    final String nome_saida = "saida.pdf";
		    final String imagemAssinatura = "assinatura.png";
		    final int posicaoImagem = 2;
		    
		    
			ByteArrayOutputStream bao = pdf.assinar( caminho.concat(nome_entrada), caminho.concat(imagemAssinatura), posicaoImagem );
			
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

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
	}
	
	public Resource assinar(MultipartFile pdf, MultipartFile imagem, PosicaoImagemEnum posicaoImagem) throws Exception, IOException, BadElementException, DocumentException, URISyntaxException {
		
		try {
			
			if( ValidaUtil.isPdfValido(pdf) &&  ValidaUtil.isImagemValido(imagem) ) {
				
				ByteArrayOutputStream baos =  assinar( new PdfReader( pdf.getInputStream()), imagem.getBytes(), posicaoImagem);
				
				byte[] bytes = baos.toByteArray();
				InputStream inputStream = new ByteArrayInputStream(bytes);
				return new InputStreamResource(inputStream);
			}
		
			throw new Exception("Não foi possivel atender sua solicitação");
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	public ByteArrayOutputStream assinar(String pdf, String imagem, int posicaoImagem) throws Exception, IOException, BadElementException, DocumentException, URISyntaxException {
		
		 BufferedImage bImage = ImageIO.read(new File(imagem));
		 
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
	     ImageIO.write(bImage, "png", bos );
	     byte [] data = bos.toByteArray();
		
	     PosicaoImagemEnum posicaoImagemEnum = PosicaoImagemEnum.from(posicaoImagem);
	     
		 return assinar( new PdfReader(pdf), data, posicaoImagemEnum );
	}
		
	public ByteArrayOutputStream assinar(PdfReader reader, byte[] img, PosicaoImagemEnum posicaoImagemEnum) throws IOException, BadElementException, DocumentException, URISyntaxException {
				
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		PdfStamper stamper = new PdfStamper(reader, baos);
		
		final float posicaoY = POSICAO_INICIAL_IMAGEM;
		
		int qdtPage = reader.getNumberOfPages();
	    for (int i=1; i<=qdtPage; i++){
	    	
	    	PdfContentByte over = stamper.getOverContent(i);
	    	
	    	float posicaoX = PosicaoImagemUtil.geraPosisaoY(posicaoImagemEnum, 
	    			over.getPdfDocument().getPageSize().getHeight() );
	    	
	    	over.addImage(getAssinatura( img, posicaoX, posicaoY ));
	    }		
		stamper.close();
		baos.flush();
		baos.close();
		return baos;
	}
	
	private Image getAssinatura(byte[] img, float posicaoX, float posicaoY) throws BadElementException, IOException, URISyntaxException {
		
		Image image = Image.getInstance(img);
				
		image.scaleAbsolute(LARGURA_IMAGEM, ALTURA_IMAGEM);
        image.setAbsolutePosition(posicaoX, posicaoY); 
		
		return image;
		
	}
}
