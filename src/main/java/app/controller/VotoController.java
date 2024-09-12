package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.entity.Voto;
import app.service.VotoService;

@RestController
@RequestMapping("api/eleitor")
public class VotoController {

	@Autowired
	private VotoService votoService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Voto voto, @RequestParam Long id) {
		try {
			if (voto.getData() != null || voto.getComprovante() != null)
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "data e comprovante deve ser nulo");

			String mensagem = this.votoService.save(voto, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findById")
	public ResponseEntity<Voto> findById(@PathVariable Long id) {
		try {
			Voto voto = this.votoService.findById(id);
			return new ResponseEntity<>(voto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Voto>> findAll() {
		try {
			List<Voto> voto = this.votoService.findAll();
			return new ResponseEntity<>(voto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		try {
			String mensagem = this.votoService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
