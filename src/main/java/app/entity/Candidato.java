package app.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Candidato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome não pode ser vazio")
	private String nome;
	
	@NotBlank(message = "CPF não pode ser vazio")
	private String CPF;
	
	@NotNull(message = "Numero de candidato não pode ser nulo")
	@Column(unique = true)
	private Integer numCand;
	
	@NotNull(message = "funcao nao pode ser nulo")
	private Integer funcao;
	
	@NotBlank(message = "Status não pode ser vazio")
	private String status = null;
	
	@Transient
	private Integer votos;
	
	@OneToMany(mappedBy = "candidato")
	private List<Voto> voto;
	
}
