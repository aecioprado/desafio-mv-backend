package com.aecioprado.mv.service;

import java.util.List;
import java.util.Objects;

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
		return this.lancamentoRepo.save(lancamento);
	}

	// atualizar lancamento
	@Override
	@Transactional
	public LancamentoEntity atualizar(LancamentoEntity lancamento) {
		Objects.requireNonNull(lancamento.getId());
		return this.lancamentoRepo.save(lancamento);
	}

	// excluir lancamento
	@Override
	@Transactional
	public boolean excluir(Long id) {
		try {
			this.lancamentoRepo.deleteById(id);
			return true;
			
		}catch (LancamentoException e) {
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
	public void validarProdutoExiste(String produtos) {
		// TODO Auto-generated method stub

	}

}
