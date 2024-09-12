package app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Voto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "A data não pode ser vazia")
	private String data;
	
	@NotNull(message = "Prefeito não pode ser nulo")
	private Integer prefeito;
	
	@NotNull(message = "Vereador não pode ser nulo")
	private Integer vereador;
	
	@NotBlank(message = "Comprovante não pode ser vazio")
	private String comprovante;
	
}
