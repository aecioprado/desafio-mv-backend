package com.aecioprado.mv.dto;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


public class LancamentoDto implements Serializable {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5224811368963241639L;
	public LancamentoDto(Long id, String colaborador, String cpf, String produtos) {
		this.id = id;
		this.colaborador = colaborador;
		this.cpf = cpf;
		this.produtos = produtos;
	}
	
	public LancamentoDto() {
	}
	
	private Long id;
	private String colaborador;
	private String cpf;
	private String produtos;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColaborador() {
		return colaborador;
	}

	public void setColaborador(String colaborador) {
		this.colaborador = colaborador;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getProdutos() {
		return produtos;
	}

	public void setProdutos(String produtos) {
		this.produtos = produtos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(colaborador, cpf, id, produtos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LancamentoDto other = (LancamentoDto) obj;
		return Objects.equals(colaborador, other.colaborador) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(id, other.id) && Objects.equals(produtos, other.produtos);
	}

	@Override
	public String toString() {
		return "LancamentoDto [id=" + id + ", colaborador=" + colaborador + ", cpf=" + cpf + ", produtos=" + produtos
				+ "]";
	}
	
	
	

}
