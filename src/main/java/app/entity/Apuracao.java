package app.entity;

import java.util.List;

import lombok.Data;

@Data
public class Apuracao {

	private Integer total;
	
	private List<Object> prefeito;
	
	private List<Object> vereador;
	
}
