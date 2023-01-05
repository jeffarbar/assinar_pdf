package br.com.telefone.pdf.util;


import br.com.telefone.pdf.enums.PosicaoImagemEnum;
import br.com.telefone.pdf.service.PdfService;

public class PosicaoImagemUtil {

	public static float geraPosisaoY(PosicaoImagemEnum posicaoImagemEnum, float larguraPdf) {
		
		
		switch (posicaoImagemEnum) {
			case ESQUEDA:
				return PdfService.POSICAO_INICIAL_IMAGEM;
	
			case CENTRO:
				return (larguraPdf - PdfService.LARGURA_IMAGEM) / 2;
				
			case DIREITA:
				return larguraPdf - ( PdfService.LARGURA_IMAGEM + 2 );
				
			default:
				return PdfService.POSICAO_INICIAL_IMAGEM;
			}
		
	}
}
