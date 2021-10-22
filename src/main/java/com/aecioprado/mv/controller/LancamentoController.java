package com.aecioprado.mv.controller;

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
import com.aecioprado.mv.service.ILancamentoService;

@RestController
public class LancamentoController {

	@Autowired
	private ILancamentoService Lancamentoservice;

	// salvar
	@PostMapping("/salvar")
	public ResponseEntity salvar(@RequestBody LancamentoDto dto) {

		LancamentoEntity lancamento = new LancamentoEntity();
		lancamento.setColaborador(dto.getColaborador());
		lancamento.setCpf(dto.getCpf());
		lancamento.setProdutos(dto.getProdutos());

		try {
			LancamentoEntity lancamentoSalvo = Lancamentoservice.salvar(lancamento);
			return new ResponseEntity(lancamentoSalvo, HttpStatus.CREATED);
		} catch (LancamentoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// atualizar
	@PutMapping("/atualizar")
	public ResponseEntity atualizar(@RequestBody LancamentoEntity lancamento) {

		return ResponseEntity.status(HttpStatus.OK).body(this.Lancamentoservice.atualizar(lancamento));

	}

	// listar
	@GetMapping("/listar")
	public ResponseEntity listar() {

		return ResponseEntity.status(HttpStatus.OK).body(this.Lancamentoservice.listarTodos());
	}

	// listar por Id
	@GetMapping("/listar/{id}")
	public ResponseEntity listarPorId(@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(this.Lancamentoservice.obterPorId(id));
	}

	// deletar
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity delete(@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(this.Lancamentoservice.excluir(id));

	}

}
