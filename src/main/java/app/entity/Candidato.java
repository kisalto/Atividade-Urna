package app.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
	private int numCand;
	
	@NotNull(message = "funcao nao pode ser nulo")
	@Min(value = 0, message = "Funcao deve ser no mínimo 0")
    @Max(value = 1, message = "Funcao deve ser no máximo 1")
	private int funcao;
	
	@NotBlank(message = "Status não pode ser vazio")
	private String status = null;
	
	@Transient
	private int votos;
	
	@OneToMany
	private List<Voto> voto;
	
}
