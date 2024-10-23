//package app.servicetest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import app.entity.Candidato;
//import app.repository.CandidatoRepository;
//import app.service.CandidatoService;
//
//public class CandidatoServiceTest {
//
//	@InjectMocks
//	private CandidatoService candidatoService;
//
//	@Mock
//	private CandidatoRepository candidatoRepository;
//
//	private Candidato candidato;
//
//	@BeforeEach
//	public void setUp() {
//		MockitoAnnotations.openMocks(this);
//
//		candidato = new Candidato();
//		candidato.setNome("João");
//		candidato.setCPF("12345678900");
//		candidato.setNumCand(1);
//		candidato.setFuncao(0);
//
//		when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));
//
//		when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidato);
//
//	}
//
//	@Test
//	public void testSave() {
//
//		String result = candidatoService.save(candidato);
//
//		assertEquals("Candidato cadastrado com sucesso", result);
//		assertEquals("APTO", candidato.getStatus());
//		verify(candidatoRepository).save(candidato);
//	}
//
//	@Test
//	public void testUpdateCandidatoExistente() {
//
//		Candidato candidatoAtualizado = new Candidato();
//		candidatoAtualizado.setNome("João Atualizado");
//		candidatoAtualizado.setFuncao(1);
//
//		String resultado = candidatoService.update(candidatoAtualizado, 1L);
//
//		assertEquals("candidato salvo com sucesso", resultado);
//	}
//
//	@Test
//	public void testUpdateCandidatoNaoExistente() {
//		when(candidatoRepository.findById(1L)).thenReturn(Optional.empty());
//
//		Candidato candidato = new Candidato();
//		candidato.setNome("Novo Candidato");
//
//		String resultado = candidatoService.update(candidato, 1L);
//
//		assertEquals("candidato não existe", resultado);
//	}
//
//	@Test
//	public void testFindByIdCandidatoExistente() {
//
//		Candidato resultado = candidatoService.findById(1L);
//
//		assertEquals(candidato, resultado);
//	}
//
//	@Test
//	public void testFindByIdCandidatoNaoExistente() {
//		when(candidatoRepository.findById(1L)).thenReturn(Optional.empty());
//
//		Candidato resultado = candidatoService.findById(1L);
//
//		assertNull(resultado);
//	}
//
//	@Test
//	public void testDeleteCandidatoExistente() {
//
//		String resultado = candidatoService.delete(1L);
//
//		assertEquals("Candidato deletado com sucesso", resultado);
//		assertEquals("APTO", candidato.getStatus());
//
//		verify(candidatoRepository).save(candidato);
//	}
//
//	@Test
//	public void testDeleteCandidatoNaoExistente() {
//		when(candidatoRepository.findById(1L)).thenReturn(Optional.empty());
//
//		String resultado = candidatoService.delete(1L);
//
//		assertEquals("candidato não existe", resultado);
//
//		verify(candidatoRepository, never()).save(any(Candidato.class));
//	}
//
//	@Test
//	public void testFindAllVereador() {
//		List<Candidato> candidatosVereadores = List.of(
//				new Candidato(null, "Candidato X", "11111111111", 1, 0, "APTO", 0, null),
//				new Candidato(null, "Candidato Y", "22222222222", 1, 0, "APTO", 0, null));
//
//		when(candidatoRepository.findAllByFuncaoAndStatusNot(1, "INATIVO")).thenReturn(candidatosVereadores);
//
//		List<Candidato> resultado = candidatoService.findAllVereador();
//
//		assertEquals(2, resultado.size());
//		assertEquals(candidatosVereadores, resultado);
//	}
//
//	@Test
//	public void testFindAllPrefeito() {
//		List<Candidato> candidatosPrefeitos = List.of(
//				new Candidato(null, "Candidato A", "12345678900", 0, 0, "APTO", 0, null),
//				new Candidato(null, "Candidato B", "98765432100", 0, 0, "APTO", 0, null));
//
//		when(candidatoRepository.findAllByFuncaoAndStatusNot(0, "INATIVO")).thenReturn(candidatosPrefeitos);
//
//		List<Candidato> resultado = candidatoService.findAllPrefeito();
//
//		assertEquals(2, resultado.size());
//		assertEquals(candidatosPrefeitos, resultado);
//	}
//
//	@Test
//	public void testFindAll() {
//		List<Candidato> candidatos = List.of(
//				new Candidato(null, "Candidato A", "12345678900", 0, 0, "APTO", 0, null),
//				new Candidato(null, "Candidato B", "98765432100", 0, 1, "APTO", 0, null));
//		
//		when(candidatoRepository.findAll()).thenReturn(candidatos);
//		
//		List<Candidato> resultado = candidatoService.findAll();
//		
//		assertEquals(2, resultado.size());
//		assertEquals(candidatos, resultado);
//	}
//
//}
