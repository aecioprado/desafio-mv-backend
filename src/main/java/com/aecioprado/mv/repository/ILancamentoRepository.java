package com.aecioprado.mv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aecioprado.mv.entity.LancamentoEntity;

@Repository
public interface ILancamentoRepository extends JpaRepository<LancamentoEntity, Long> {
	
	//verifica se email já existe
	boolean existsByCpf (String cpf);




}
