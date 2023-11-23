package com.aecioprado.mv.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "tb_cafe_da_manha")

public class LancamentoEntity implements Serializable{
	
	private static final long serialVersionUID = 7235967612451452483L;
	
	
	// Construtores
	public LancamentoEntity() {
		super();
	}

	public LancamentoEntity(Long id, String colaborador, String cpf, String produtos) {
		super();
		this.id = id;
		this.colaborador = colaborador;
		this.cpf = cpf;
		this.produtos = produtos;
	}

	
	
	@JsonInclude(Include.NON_NULL)
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "id")
	private Long id;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name = "colaborador")
	private String colaborador;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name = "cpf")
	private String cpf;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name = "produtos")
	private String produtos;
	
	@Override
	public String toString() {
		return "LancamentoEntity [id=" + id + ", nome=" + colaborador + ", cpf=" + cpf + ", produtos=" + produtos + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, id, colaborador, produtos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LancamentoEntity other = (LancamentoEntity) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(id, other.id) && Objects.equals(colaborador, other.colaborador)
				&& Objects.equals(produtos, other.produtos);
	}
	

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

	
	

}
