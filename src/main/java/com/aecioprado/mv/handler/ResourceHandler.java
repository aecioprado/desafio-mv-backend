package com.aecioprado.mv.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.aecioprado.mv.exception.LancamentoException;
import com.aecioprado.mv.model.ErrorResponse;
import com.aecioprado.mv.model.ErrorResponse.ErrorResponseBuilder;

//centraliza o tratamento das excecoes personalizadas

@ControllerAdvice
public class ResourceHandler {

	@ExceptionHandler(LancamentoException.class)
	public ResponseEntity<ErrorResponse> handlerLancamentoException(LancamentoException lancamento) {
		
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a").format(new Date());
		ErrorResponseBuilder erro = ErrorResponse.builder();
		erro.httpStatus(lancamento.getHttpStatus().value());
		erro.mensagem(lancamento.getMessage());
		erro.timeStamp(timeStamp);
		return ResponseEntity.status(lancamento.getHttpStatus()).body(erro.build());
	}
}
