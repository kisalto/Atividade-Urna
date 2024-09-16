package app.entity;

import java.util.List;

import lombok.Data;

@Data
public class Apuracao {

	private int total;
	
	private List<Candidato> prefeito;
	
	private List<Candidato> vereador;
	
}
