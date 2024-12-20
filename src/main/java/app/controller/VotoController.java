package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Apuracao;
import app.entity.Voto;
import app.service.VotoService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/votar")
@Validated
public class VotoController {

	@Autowired
	private VotoService votoService;
	
	@PostMapping("/votar/{id}")
	public ResponseEntity<String> votar(@RequestBody Voto voto, @PathVariable Long id) {
		try {
			if (voto.getData() != null)
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("data deve ser nulo");

			String mensagem = this.votoService.votar(voto, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/realizarApuracao")
	public ResponseEntity<Apuracao> realizarApuracao() {
		try {
			Apuracao mensagem = this.votoService.realizarApuracao();
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

}
