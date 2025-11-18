//package br.unitins.tp1;
//
//import br.unitins.tp1.model.DTO.Televisao.DimensaoRequestDTO;
//import br.unitins.tp1.model.DTO.Televisao.DimensaoResponseDTO;
//import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
//import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
//import br.unitins.tp1.service.Auth.JwtServiceImpl;
//import br.unitins.tp1.service.Dimensao.DimensaoServiceImpl;
//import br.unitins.tp1.service.Fabricante.FabricanteServiceImpl;
//import br.unitins.tp1.service.Televisao.TelevisaoServiceImpl;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@QuarkusTest
//public class DimensaoResourceTest {
//
//    @Inject
//    JwtServiceImpl jwtService;
//
//    @Inject
//    DimensaoServiceImpl dimensaoService;
//
//    @Inject
//    TelevisaoServiceImpl televisaoService;
//
//    @Inject
//    FabricanteServiceImpl fabricanteService;
//
//    @Test
//    void testIncluir_DIMENSAO() {
//        DimensaoRequestDTO dto = new DimensaoRequestDTO(100, 100, 100);
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(dto)
//                .when()
//                .post("/dimensao")
//                .then()
//                .statusCode(201)
//                .body("id", notNullValue(),
//                        "comprimento", is(100),
//                        "altura", is(100),
//                        "polegada", is(100));
//    }
//
//    @Test
//    void testAtualizar_DIMENSAO() {
//        long idExistente = 1L;
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        DimensaoRequestDTO dimensaoAlterada = new DimensaoRequestDTO(50, 50, 50);
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(dimensaoAlterada)
//                .when()
//                .put("/dimensao/" + idExistente + "/atualizar")
//                .then()
//                .statusCode(204);
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/dimensao/" + idExistente + "/buscar-por-id/")
//                .then()
//                .statusCode(200)
//                .body("comprimento", is(50))
//                .body("altura", is(50))
//                .body("polegada", is(50));
//    }
//
//    @Test
//    void testDeletar_DIMENSAO() {
//        DimensaoRequestDTO dimensaoOriginal = new DimensaoRequestDTO(100, 100, 100);
//        long id = dimensaoService.create(dimensaoOriginal).id();
//
//        DimensaoRequestDTO dimensaoAlterada = new DimensaoRequestDTO(50, 50, 50);
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(dimensaoAlterada)
//                .when()
//                .put("/dimensao/" + id + "/atualizar")
//                .then()
//                .statusCode(204);
//
//        DimensaoResponseDTO dimensaoAtualizada = dimensaoService.findById(id);
//        assertNotNull(dimensaoAtualizada);
//        assertEquals(50, dimensaoAtualizada.comprimento());
//        assertEquals(50, dimensaoAtualizada.altura());
//        assertEquals(50, dimensaoAtualizada.polegada());
//    }
//
//    @Test
//    void testBuscarPorId_DIMENSAO() {
//        DimensaoRequestDTO dimensao = new DimensaoRequestDTO(100, 100, 100);
//        long id = dimensaoService.create(dimensao).id();
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/dimensao/" + id + "/buscar-por-id")
//                .then()
//                .statusCode(200)
//                .body("id", notNullValue(),
//                        "comprimento", is(100),
//                        "altura", is(100),
//                        "polegada", is(100));
//    }
//
//    @Test
//    void testBuscarTodos_DIMENSAO(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/dimensao")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    void testBuscarTvPorIdDimensao_DIMENSAO() {
//        DimensaoRequestDTO dimensao = new DimensaoRequestDTO(100, 100, 100);
//        long idDimensao = dimensaoService.create(dimensao).id();
//
//        long idFabricanteExistente = 1L;
//
//        TelevisaoRequestDTO tv1 = new TelevisaoRequestDTO(
//                "Samsung", "Neo QLED", 2000.0, 1, 1, 10, idDimensao, idFabricanteExistente
//        );
//        TelevisaoRequestDTO tv2 = new TelevisaoRequestDTO(
//                "LG", "OLED B2", 3000.0, 1, 1, 15, idDimensao, idFabricanteExistente
//        );
//
//        televisaoService.create(tv1);
//        televisaoService.create(tv2);
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/dimensao/" + idDimensao + "/buscar-televisao-por-id_dimensao")
//                .then()
//                .statusCode(200)
//                .body("size()", is(2));
//
//        List<TelevisaoResponseDTO> lista = dimensaoService.findTelevisaoByDimensao(idDimensao);
//        assertThat(lista.size(), is(2));
//
//        for (TelevisaoResponseDTO tv : lista) {
//            assertThat(tv.dimensao().id(), is(idDimensao));
//        }
//    }
//}
