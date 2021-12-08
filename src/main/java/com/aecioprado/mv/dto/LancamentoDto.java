package com.aecioprado.mv.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class LancamentoDto{
	
	private Long id;
	
	@NotBlank(message = "Informe o nome do colaborador.")
	private String colaborador;
	
	@NotBlank(message = "Informe o cpf do colaborador.")
	private String cpf;
	
	@NotBlank(message = "Informe os itens do cafe da manha.")
	private String produtos;
	
}
