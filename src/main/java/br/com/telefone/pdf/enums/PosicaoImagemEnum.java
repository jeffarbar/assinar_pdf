package br.com.telefone.pdf.enums;

import java.io.IOException;

public enum PosicaoImagemEnum {
	
	ESQUEDA(0 , "Posição inferior esquerdo imagem"),
	CENTRO(1, "Posição centrao imagem"),
	DIREITA(2 , "Posição inferior direito imagem");
	
	
	private PosicaoImagemEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static PosicaoImagemEnum from(int codigo)  throws Exception{
		
		for( int i = 0; i < values().length ;i++ ) {
			
			if(values()[i].codigo == codigo ) {
				return values()[i];
			}	
		}
		
		throw new Exception(String.format("Não foi possivel encontrar o código da posição %s.", codigo));
	}
	
	private int codigo;
	private String descricao;
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
