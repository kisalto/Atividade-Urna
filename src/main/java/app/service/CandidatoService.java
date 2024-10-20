package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Candidato;
import app.repository.CandidatoRepository;

@Service
public class CandidatoService {
	
	@Autowired
	private CandidatoRepository candidatoRepository;
	
	public String save(Candidato candidato) {
		
		candidato.setStatus("APTO");
		
		this.candidatoRepository.save(candidato);
		return "Candidato cadastrado com sucesso";
	}
	
	public String update(Candidato candidato, Long id) {
		
		Candidato candidatoEx = this.findById(id);
		if (candidatoEx == null)
			return "candidato não existe";
		
		this.candidatoRepository.save(candidato);
		return "candidato salvo com sucesso";
	}
	
	public Candidato findById(Long id) {
	    Optional<Candidato> optional = this.candidatoRepository.findById(id);

	    return optional.orElse(null);
	}
	
	public List<Candidato> findAll() {
		return this.candidatoRepository.findAll();
	}
	
	public String delete(Long id) {
		
		Candidato candidatoEx = this.findById(id);
		if (candidatoEx == null)
			return "candidato não existe";
		
		candidatoEx.setStatus("INATIVO");
		this.update(candidatoEx, id);
		return "Candidato deletado com sucesso";
	}
	
	public List<Candidato> findAllPrefeito() {
		return this.candidatoRepository.findAllByFuncaoAndStatusNot(0, "INATIVO");
	}
	
	public List<Candidato> findAllVereador() {
		return this.candidatoRepository.findAllByFuncaoAndStatusNot(1, "INATIVO");
	}
	
	public Candidato findByNumCand(int numCand) {
		return this.candidatoRepository.findByNumCand(numCand);
	}
	
}
