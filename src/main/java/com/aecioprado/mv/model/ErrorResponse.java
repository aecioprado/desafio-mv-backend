package com.aecioprado.mv.model;

import lombok.Builder;
import lombok.Getter;

//Essa classe cria um objeto de erro

@Builder // usar em classes que não usam serializable
@Getter
public class ErrorResponse {
	
	private String mensagem;
	private int httpStatus;
	private String timeStamp;

}
