package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Voto;
import app.repository.VotoRepository;

@Service
public class VotoService {
	
	@Autowired
	private VotoRepository votoRepository;
	
	public String save(Voto voto) {
		this.votoRepository.save(voto);
		return "Voto registrado com sucesso";
	}
	
	public Voto findById(Long id) {
		Optional<Voto> optional = this.votoRepository.findById(id);
		if (optional == null)
			return null;
		return optional.get();
	}
	
	public List<Voto> findAll() {
		return this.votoRepository.findAll();
	}
	
	public String delete(Long id) {
		Voto votoEx = this.findById(id);
		if (votoEx == null)
			return "Voto não existente";
		this.votoRepository.deleteById(id);
		return "Voto deletado";
	}
	
}
