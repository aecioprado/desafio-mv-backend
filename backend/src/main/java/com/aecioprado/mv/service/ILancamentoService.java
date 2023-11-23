package com.aecioprado.mv.service;

import java.util.List;
import java.util.Optional;

import com.aecioprado.mv.entity.LancamentoEntity;

public interface ILancamentoService {
	
	// define quais métodos deverão ser implementados
	
	//salvar
	public LancamentoEntity salvar(LancamentoEntity lancamento);
	
	//atualizar
	public LancamentoEntity atualizar(LancamentoEntity lancamento);
	
	//consultar por Id
	Optional<LancamentoEntity> obterPorId(Long id);
	
	//excluir
	public boolean excluir(Long id); 
	
	
	// listar todos os lancamentos
	public List<LancamentoEntity> listarTodos();
	
	//validar se cpf existe
	public void validarCpf(String cpf);
	
	// validar se já existe o produto 
	public void validarProdutoExiste(String produtos);
	
	

}
