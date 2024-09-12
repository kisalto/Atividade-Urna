package app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class Eleitor {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Campo nome nao pode ser vazio")
	private String nome;
	
	@org.hibernate.validator.constraints.br.CPF
	private String CPF;
	
	@NotBlank(message = "Profissão é um campo obrigatorio")
	private String profissao;
	
	@NotBlank(message = "celular é um campo obrigatorio")
	@Pattern(regexp = "^\\(\\d{2}\\) 9\\d{4}-\\d{4}$", message = "Formato errado para telefone celular")
	private String celular;
	
	@Pattern(regexp = "^\\(\\d{2}\\) \\d{4}-\\d{4}$", message = "Formato errado para telefone fixo")
	private String telefone;
	
	@Email
	private String email;
	
	@NotBlank(message = "status é um campo obrigatorio")
	private String status;
}
