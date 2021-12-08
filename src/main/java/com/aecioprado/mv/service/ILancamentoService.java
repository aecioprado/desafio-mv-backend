package com.aecioprado.mv.service;

import java.util.List;
import java.util.Optional;

import com.aecioprado.mv.dto.LancamentoDto;
import com.aecioprado.mv.entity.LancamentoEntity;

public interface ILancamentoService {
	
	//CRUD
	
	//salvar
	public Boolean salvar(LancamentoDto lancamento);
	
	//atualizar
	public Boolean atualizar(LancamentoDto lancamento);
	
	//consultar por Id
	public LancamentoEntity obterPorId(Long id);
	
	//excluir
	public boolean excluir(Long id); 
	
	// listar todos os lancamentos
	public List<LancamentoEntity> listarTodos();
	
	
	//métodos de validação:
	
	//validar se cpf existe
	public void validarCpf(String cpf);
	
	// validar se já existe o produto 
	public void validarProdutoExiste(String produtos);
	
	

}
