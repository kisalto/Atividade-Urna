package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Eleitor;
import app.entity.Voto;
import app.exception.EleitorInvalido;
import app.exception.StatusInvalido;
import app.repository.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private EleitorService eleitorService;
	
	public String save(Voto voto, Long id) {

		verificarEleitor(id);
		
		this.votoRepository.save(voto);
		return "Voto registrado com sucesso";
	}

	private void verificarEleitor (Long id) {
		
		Eleitor eleitor = eleitorService.findById(id);
		if (eleitor == null)
			throw new EleitorInvalido("Eleitor invalido");
		
		else if (!eleitor.getStatus().equals("APTO")) {
			eleitor.setStatus("BLOQUEADO");
			eleitorService.save(eleitor);
			
			throw new StatusInvalido("Usuário com cadastro invalido tentou votar. O usuário será bloqueado!");
		}
		
		eleitor.setStatus("VOTOU");
		eleitorService.save(eleitor);
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
