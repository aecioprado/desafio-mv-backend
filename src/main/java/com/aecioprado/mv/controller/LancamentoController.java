package com.aecioprado.mv.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aecioprado.mv.dto.LancamentoDto;
import com.aecioprado.mv.entity.LancamentoEntity;
import com.aecioprado.mv.exception.LancamentoException;
import com.aecioprado.mv.repository.ILancamentoRepository;
import com.aecioprado.mv.service.ILancamentoService;

@RestController
public class LancamentoController {

	@Autowired
	private ILancamentoService Lancamentoservice;
	

	// salvar
	@PostMapping("/salvar")
	public ResponseEntity<Boolean> salvar(@Valid @RequestBody LancamentoDto lancamentoDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.Lancamentoservice.salvar(lancamentoDto));

	}

	// atualizar
	@PutMapping("/atualizar")
	public ResponseEntity<Boolean> atualizar(@Valid @RequestBody LancamentoDto lancamentoDto) {
		return ResponseEntity.status(HttpStatus.OK).body(this.Lancamentoservice.atualizar(lancamentoDto));
		
	}

	// listar
	@GetMapping("/listar")
	public ResponseEntity<List<LancamentoEntity>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(this.Lancamentoservice.listarTodos());
	}

	// listar por Id
	@GetMapping("/listar/{id}")
	public ResponseEntity<LancamentoEntity> listarPorId(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.Lancamentoservice.obterPorId(id));
	}

	// deletar
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.Lancamentoservice.excluir(id));

	}

}
