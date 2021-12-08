package com.aecioprado.mv.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class LancamentoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private final HttpStatus httpStatus;
	

	public LancamentoException (final String mensagem, final HttpStatus httpStatus) {
		super(mensagem);
		this.httpStatus = httpStatus;
	}


	
	

}
