package com.aecioprado.mv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aecioprado.mv.entity.LancamentoEntity;
import com.aecioprado.mv.exception.LancamentoException;
import com.aecioprado.mv.repository.ILancamentoRepository;

@Service
public class LancamentoServiceImp implements ILancamentoService {

	@Autowired
	private ILancamentoRepository lancamentoRepo;

	// salvar lancamento
	@Override
	@Transactional
	public LancamentoEntity salvar(LancamentoEntity lancamento) {
		validarCpf(lancamento.getCpf());
		validarProdutoExiste(lancamento.getProdutos());
		return this.lancamentoRepo.save(lancamento);
	}

	// atualizar lancamento
	@Override
	@Transactional
	public LancamentoEntity atualizar(LancamentoEntity lancamento) {
		Objects.requireNonNull(lancamento.getId());
		validarProdutoExiste(lancamento.getProdutos());
		return this.lancamentoRepo.save(lancamento);
	}

	// excluir lancamento
	@Override
	@Transactional
	public boolean excluir(Long id) {
		try {
			this.lancamentoRepo.deleteById(id);
			return true;

		} catch (LancamentoException e) {
			return false;
		}
	}

	@Override
	public List<LancamentoEntity> listarTodos() {
		return this.lancamentoRepo.findAll();

	}

	@Override
	public void validarCpf(String cpf) {
		boolean existe = lancamentoRepo.existsByCpf(cpf);
		if (existe) {
			throw new LancamentoException("Atenção: O CPF informado já está presente na lista do café da manha");
		}
	}

	@Override
	public Optional<LancamentoEntity> obterPorId(Long id) {
		return this.lancamentoRepo.findById(id);

	}

	@Override
	public void validarProdutoExiste(String produtos) {
		
		//transforma a String em tokens
		String stringTemp = produtos;
		String[] produtosTokens = stringTemp.split(",");
		
		//recupera uma lista com todos os registros do banco
		List<LancamentoEntity> todosLancamentos = new ArrayList<LancamentoEntity>();
		todosLancamentos = lancamentoRepo.findAll();
		
		//adiciona numa nova lista apenas os produtos
		List<String> listaProdutos = new ArrayList<String>();
		
		for(LancamentoEntity lancamentos: todosLancamentos) {
			listaProdutos.add(lancamentos.getProdutos());
		}
		
		//compara a array de tokens com todas as strings da listaProdutos
		//para cada elemento do array de tokens, comparar com cada elemento da lista de produtos
		//por fim, verificar se um elemento da lista de strings contém um elemento do array de tokens
		for(int i=0; i<produtosTokens.length; i++) {
			for(int j=0; j<listaProdutos.size(); j++) {
				if (listaProdutos.get(j) != null && listaProdutos.get(j).contains(produtosTokens[i])) {
					//Lança exception assim que houve o primeiro "match"
					throw new LancamentoException("Um ou mais produtos da sua lista já foi cadastrado");
				}
			}
		}
		/*
		//verifica se a lista de produtos atual existe na lista de produtos já cadastrada
		for(String produto: listaProdutos) {
			
			if(produto.contains(produtos)) {
				throw new LancamentoException("Um ou mais produtos da sua lista já foi cadastrado");
			}
		}
		*/
		
		
	}


}
