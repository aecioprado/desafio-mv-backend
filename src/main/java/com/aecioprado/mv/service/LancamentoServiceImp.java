package com.aecioprado.mv.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aecioprado.mv.dto.LancamentoDto;
import com.aecioprado.mv.entity.LancamentoEntity;
import com.aecioprado.mv.exception.LancamentoException;
import com.aecioprado.mv.repository.ILancamentoRepository;

@CacheConfig(cacheNames = "lancamento")
@Service
public class LancamentoServiceImp implements ILancamentoService {
	
	private static final String MSG_ELEMENTO_NAO_EXISTE = "O item nao existe no sistema: ";
	private static final String MSG_CPF_JA_CADASTRADO = "O CPF informado já está presente na lista do café da manha: ";
	private static final String MENSAGEM_ERRO_INTERNO = "Erro interno identificado. Contate o suporte";
	

	private ILancamentoRepository lancamentoRepository;
	private ModelMapper mapper;
	
	@Autowired
	public LancamentoServiceImp(ILancamentoRepository lancamentoRepository) {
		this.mapper = new ModelMapper();
		this.lancamentoRepository = lancamentoRepository;
		
	}

	// salvar
	@Override
	public Boolean salvar(LancamentoDto lancamentoDto) {

		try {
			validarCpf(lancamentoDto.getCpf());
			validarProdutoExiste(lancamentoDto.getProdutos());
			
			LancamentoEntity lancamentoEntity = this.mapper.map(lancamentoDto, LancamentoEntity.class);
			this.lancamentoRepository.save(lancamentoEntity);
			return true;
		} catch (Exception e) {
			throw new LancamentoException(e.getMessage(),HttpStatus.OK);
	}
	}

	// atualizar
	@CacheEvict(key="#lancamentoDto.id")
	@Override
	public Boolean atualizar(LancamentoDto lancamentoDto) {

		try {
				this.lancamentoRepository.findById(lancamentoDto.getId()).get();

				LancamentoEntity lancamentoEntityAtualizado = this.mapper.map(lancamentoDto, LancamentoEntity.class);
				this.lancamentoRepository.save(lancamentoEntityAtualizado);
				return true;
		}catch (LancamentoException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}


	// excluir
	@Override
	public boolean excluir(Long id) {
		try {
			this.lancamentoRepository.deleteById(id);
			return true;

		} catch (Exception e) {
			throw new LancamentoException(MSG_ELEMENTO_NAO_EXISTE + id, HttpStatus.NOT_FOUND);
		}
	}

	// listar Todos
	@Override
	public List<LancamentoEntity> listarTodos() {
		return this.lancamentoRepository.findAll();
}

	// listar por id
	@Cacheable(key="#id")
	@Override
	public LancamentoEntity obterPorId(Long id) { //refatorar
		try {
		return this.lancamentoRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new LancamentoException(MSG_ELEMENTO_NAO_EXISTE + id, HttpStatus.NOT_FOUND);
		}

	}
	
	@Override
	public void validarCpf(String cpf) {
		
		boolean existe = this.lancamentoRepository.existsByCpf(cpf);
		if (existe) {
			throw new LancamentoException(MSG_CPF_JA_CADASTRADO + cpf, HttpStatus.OK);
		}
	}

	@Override
	public void validarProdutoExiste(String produtos) {
		
		//transforma a String em tokens
		String stringTemp = produtos;
		
		//adicionar regex no split para remover os espacos em branco
		//String[] produtosTokens = stringTemp.split(",");
		String[] produtosTokens = stringTemp.split(",\\s+");
		
		List array_temp = Arrays.asList(produtosTokens);
		
		System.out.println(array_temp);
		
		//recupera uma lista com todos os registros do banco
		List<LancamentoEntity> todosLancamentos = new ArrayList<LancamentoEntity>();
		
		//criar um uma nova consulta para retornar os produtos direto do banco.
		todosLancamentos = lancamentoRepository.findAll();
		
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
					throw new LancamentoException("Um ou mais produtos da sua lista já foi cadastrado", HttpStatus.OK);
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
