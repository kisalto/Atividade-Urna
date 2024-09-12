package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class Voto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "A data não pode ser vazia")
    private String data;
    
    @ManyToOne
    @JsonIgnoreProperties("voto")
    @NotNull(message = "Prefeito não pode ser nulo")
    @Pattern(regexp = "^\\d{2}000$", message = "O candidato escolhido para prefeito é um candidato a vereador. Refaça a requisição!")
    private Integer prefeito;
    
    @ManyToOne
    @JsonIgnoreProperties("voto")
    @NotNull(message = "Vereador não pode ser nulo")
    @Pattern(regexp = "^\\d{5}$", message = "O candidato escolhido para vereador é um candidato a prefeito. Refaça a requisição!")
    private Integer vereador;
    
    @NotBlank(message = "Comprovante não pode ser vazio")
    private String comprovante;
}
