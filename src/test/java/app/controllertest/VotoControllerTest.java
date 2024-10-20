//package app.controllertest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
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
//
//import app.controller.VotoController;
//import app.entity.Apuracao;
//import app.entity.Candidato;
//import app.entity.Voto;
//import app.service.VotoService;
//
//@SpringBootTest
//public class VotoControllerTest {
//
//    @Autowired
//    VotoController votoController;
//
//    @MockBean
//    VotoService votoService;
//
//    private Voto voto;
//    private Apuracao apuracao;
//
//    @BeforeEach
//    void setup() {
//        voto = criarVoto();
//        apuracao = new Apuracao();
//        configurarMocks("sucesso");
//    }
//
//    private Voto criarVoto() {
//        Voto voto = new Voto();
//        voto.setPrefeito(criarPrefeito());
//        voto.setVereador(criarVereador());
//        
//        return voto;
//    }
//    
//    private Candidato criarVereador() {
//    	return new Candidato(1L, "João", "12345678900", 10111, 0, null, 0, null);
//    }
//    
//    private Candidato criarPrefeito() {
//    	return new Candidato(1L, "Paulo", "98765432100", 10000, 0, null, 0, null);
//    }
//
//    private void configurarMocks(String tipo) {
//        if ("sucesso".equals(tipo)) {
//            Mockito.when(votoService.votar(Mockito.any(Voto.class), Mockito.anyLong()))
//                .thenReturn("Voto registrado com sucesso!");
//            Mockito.when(votoService.realizarApuracao()).thenReturn(apuracao);
//        } else if ("erro".equals(tipo)) {
//            Mockito.when(votoService.votar(Mockito.any(Voto.class), Mockito.anyLong()))
//                .thenThrow(new RuntimeException("Erro ao registrar voto"));
//            Mockito.when(votoService.realizarApuracao()).thenThrow(new RuntimeException("Erro ao realizar apuração"));
//        }
//    }
//
//    private void verificarResposta(ResponseEntity<?> retorno, Object esperado, HttpStatus statusEsperado) {
//        assertEquals(esperado, retorno.getBody());
//        assertEquals(statusEsperado, retorno.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - votar com sucesso")
//    void cenario01() {
//        ResponseEntity<String> retorno = votoController.votar(voto, 1L);
//        verificarResposta(retorno, "Voto registrado com sucesso!", HttpStatus.OK);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - erro ao votar")
//    void cenario02() {
//        configurarMocks("erro");
//        ResponseEntity<String> retorno = votoController.votar(voto, 1L);
//        verificarResposta(retorno, "Erro ao registrar voto", HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - realizar apuração com sucesso")
//    void cenario03() {
//        ResponseEntity<Apuracao> retorno = votoController.realizarApuracao();
//        verificarResposta(retorno, apuracao, HttpStatus.OK);
//    }
//
//    @Test
//    @DisplayName("INTEGRAÇÃO - erro ao realizar apuração")
//    void cenario04() {
//        configurarMocks("erro");
//        ResponseEntity<Apuracao> retorno = votoController.realizarApuracao();
//        verificarResposta(retorno, null, HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    @DisplayName("ERRO - votar com data ou comprovante não nulos")
//    void cenario05() {
//        Voto votoComDados = new Voto();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        String dataFormatada = LocalDateTime.now().format(formatter);
//        votoComDados.setData(dataFormatada);
//
//        votoComDados.setComprovante("Comprovante123");
//
//        ResponseEntity<String> retorno = votoController.votar(votoComDados, 1L);
//
//        verificarResposta(retorno, "data e comprovante deve ser nulo", HttpStatus.FORBIDDEN);
//    }
//
//}
