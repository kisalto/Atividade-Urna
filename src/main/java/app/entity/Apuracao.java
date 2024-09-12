package app.entity;

import java.util.List;

import lombok.Data;

@Data
public class Apuracao {

	private Integer total;
	
	private List<Candidato> prefeito;
	
	private List<Candidato> vereador;
	
}
