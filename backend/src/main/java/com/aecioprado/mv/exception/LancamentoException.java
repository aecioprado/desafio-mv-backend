package com.aecioprado.mv.exception;

public class LancamentoException extends RuntimeException {
	
	// Exception especifica para operacoes da entidade Lancamento
	public LancamentoException (String msg) {
		super(msg);
	}

}
