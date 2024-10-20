package app.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Apuracao;
import app.entity.Candidato;
import app.entity.Eleitor;
import app.entity.Voto;
import app.exception.CandidatoInvalido;
import app.exception.EleitorInvalido;
import app.exception.StatusInvalido;
import app.repository.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private EleitorService eleitorService;
	
	@Autowired
	private CandidatoService candidatoService;
	
	public String votar(Voto voto) { //long id

		//verificarEleitor(id);
		
		encontrarCandidatos(voto);
		
		voto.setData(dataLocal());
		
		this.votoRepository.save(voto);
		
		String hash = UUID.randomUUID().toString();
		
		return hash;
	}
	
	private String dataLocal() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);

        return formattedDate;
	}
	
	private void encontrarCandidatos(Voto voto) {
		Candidato prefeito = candidatoService.findByNumCand(voto.getNumPref());
		Candidato vereador = candidatoService.findByNumCand(voto.getNumVer());
		
		if (prefeito == null || vereador == null)
			throw new CandidatoInvalido("candidato inexistente");
		
		
		
		voto.setPrefeito(prefeito);
		voto.setVereador(vereador);
	}

//	private void verificarEleitor (Long id) {
//		
//		Eleitor eleitor = eleitorService.findById(id);
//		if (eleitor == null)
//			throw new EleitorInvalido("Eleitor invalido");
//		
//		else if (!eleitor.getStatus().equals("APTO")) {
//			eleitor.setStatus("BLOQUEADO");
//			eleitorService.save(eleitor);
//			
//			throw new StatusInvalido("Usuário com cadastro invalido tentou votar. O usuário será bloqueado!");
//		}
//		
//		eleitor.setStatus("VOTOU");
//		eleitorService.save(eleitor);
//	}
	
	public Apuracao realizarApuracao() {
		
		Apuracao apuracao = new Apuracao();
		
		List<Candidato> prefeitos = candidatoService.findAllPrefeito();
		
		List<Candidato> vereadores = candidatoService.findAllVereador();
		
		for (Candidato prefeito : prefeitos) {
		    int votos = votoRepository.countByPrefeitoId(prefeito.getId());
		    prefeito.setVotos(votos);
		}
		
		for (Candidato vereador : vereadores) {
			int votos = votoRepository.countByVereadorId(vereador.getId());
			vereador.setVotos(votos);
		};
		
		Collections.sort(prefeitos, (p1, p2) -> Integer.compare(p2.getVotos(), p1.getVotos()));

		Collections.sort(vereadores, (v1, v2) -> Integer.compare(v2.getVotos(), v1.getVotos()));
		
		apuracao.setPrefeito(prefeitos);
		apuracao.setVereador(vereadores);
		
		return apuracao;
		
	}

}
