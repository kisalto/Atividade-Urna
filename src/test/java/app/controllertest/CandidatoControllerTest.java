//package app.controllertest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.server.ResponseStatusException;
//
//import app.controller.CandidatoController;
//import app.entity.Candidato;
//import app.service.CandidatoService;
//
//@SpringBootTest
//public class CandidatoControllerTest {
//
//    @Autowired
//    CandidatoController candidatoController;
//
//    @MockBean
//    CandidatoService candidatoService;
//
//    private Candidato candidato;
//    private List<Candidato> candidatos;
//
//    @BeforeEach
//    void setup() {
//        candidato = criarCandidato();
//        candidatos = List.of(candidato);
//        configurarMocks("sucesso");
//    }
//
//    private Candidato criarCandidato() {
//        return new Candidato(1L, "João", "12345678900", 100, 0, null, 0, null);
//    }
//
//    private void configurarMocks(String tipo) {
//        if ("sucesso".equals(tipo)) {
//            Mockito.when(candidatoService.save(Mockito.any())).thenReturn("Candidato cadastrado com sucesso");
//            Mockito.when(candidatoService.update(Mockito.any(), Mockito.anyLong())).thenReturn("Candidato atualizado com sucesso");
//            Mockito.when(candidatoService.findById(Mockito.anyLong())).thenReturn(candidato);
//            Mockito.when(candidatoService.findAll()).thenReturn(candidatos);
//            Mockito.when(candidatoService.delete(Mockito.anyLong())).thenReturn("Candidato deletado com sucesso");
//        } else if ("erro".equals(tipo)) {
//            Mockito.when(candidatoService.save(Mockito.any())).thenThrow(new RuntimeException("Erro ao cadastrar candidato"));
//            Mockito.when(candidatoService.update(Mockito.any(), Mockito.anyLong())).thenThrow(new RuntimeException("Erro ao atualizar candidato"));
//            Mockito.when(candidatoService.findById(Mockito.anyLong())).thenThrow(new RuntimeException("Erro ao buscar candidato"));
//            Mockito.when(candidatoService.findAll()).thenThrow(new RuntimeException("Erro ao buscar todos os candidatos"));
//            Mockito.when(candidatoService.delete(Mockito.anyLong())).thenThrow(new RuntimeException("Erro ao deletar candidato"));
//        }
//    }
//
//    private void verificarResposta(ResponseEntity<?> retorno, Object esperado, HttpStatus statusEsperado) {
//        assertEquals(esperado, retorno.getBody());
//        assertEquals(statusEsperado, retorno.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - cadastrar candidato com sucesso")
//    void cenario01() {
//        ResponseEntity<String> retorno = candidatoController.save(candidato);
//        verificarResposta(retorno, "Candidato cadastrado com sucesso", HttpStatus.OK);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - erro ao cadastrar candidato")
//    void cenario02() {
//        configurarMocks("erro");
//        ResponseEntity<String> retorno = candidatoController.save(candidato);
//        verificarResposta(retorno, "Erro ao cadastrar candidato", HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - atualizar candidato com sucesso")
//    void cenario03() {
//        candidato.setVotos(10); // Modificar algum atributo para simular a atualização
//        ResponseEntity<String> retorno = candidatoController.update(candidato, 1L);
//        verificarResposta(retorno, "Candidato atualizado com sucesso", HttpStatus.OK);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - erro ao atualizar candidato")
//    void cenario04() {
//        configurarMocks("erro");
//        ResponseEntity<String> retorno = candidatoController.update(candidato, 1L);
//        verificarResposta(retorno, "Erro ao atualizar candidato", HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - buscar candidato por ID com sucesso")
//    void cenario05() {
//        ResponseEntity<Candidato> retorno = candidatoController.findById(1L);
//        verificarResposta(retorno, candidato, HttpStatus.OK);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - erro ao buscar candidato por ID")
//    void cenario06() {
//        configurarMocks("erro");
//        ResponseEntity<Candidato> retorno = candidatoController.findById(1L);
//        verificarResposta(retorno, null, HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - buscar todos os candidatos com sucesso")
//    void cenario07() {
//        ResponseEntity<List<Candidato>> retorno = candidatoController.findAll();
//        verificarResposta(retorno, candidatos, HttpStatus.OK);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - erro ao buscar todos os candidatos")
//    void cenario08() {
//        configurarMocks("erro");
//        ResponseEntity<List<Candidato>> retorno = candidatoController.findAll();
//        verificarResposta(retorno, null, HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - deletar candidato com sucesso")
//    void cenario09() {
//        ResponseEntity<String> retorno = candidatoController.delete(1L);
//        verificarResposta(retorno, "Candidato deletado com sucesso", HttpStatus.OK);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - erro ao deletar candidato")
//    void cenario10() {
//        configurarMocks("erro");
//        ResponseEntity<String> retorno = candidatoController.delete(1L);
//        verificarResposta(retorno, "Erro ao deletar candidato", HttpStatus.BAD_REQUEST);
//    }
//    
//    @Test
//    @DisplayName("ERRO - salvar candidato com status não nulo")
//    void cenario11() {
//        Candidato candidatoComStatus = new Candidato();
//        candidatoComStatus.setStatus("ATIVO");
//
//        Mockito.doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "status deve ser nulo"))
//               .when(candidatoService).save(Mockito.any(Candidato.class));
//
//        ResponseEntity<String> retorno = candidatoController.save(candidatoComStatus);
//
//        verificarResposta(retorno, "status deve ser nulo", HttpStatus.FORBIDDEN);
//    }
//}
