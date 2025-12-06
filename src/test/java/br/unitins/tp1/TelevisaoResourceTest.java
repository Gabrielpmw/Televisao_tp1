//package br.unitins.tp1;
//
//import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
//import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
//import br.unitins.tp1.service.Auth.JwtServiceImpl;
//import br.unitins.tp1.service.Televisao.TelevisaoServiceImpl;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.Test;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//@QuarkusTest
//public class TelevisaoResourceTest {
//
//    @Inject
//    TelevisaoServiceImpl televisaoService;
//
//    @Inject
//    JwtServiceImpl jwtService;
//
//    @Test
//    void testIncluir_TELEVISAO(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        TelevisaoRequestDTO televisao = new TelevisaoRequestDTO(
//                "Gabriel",
//                "GabrielModelo",
//                1500.0,
//                1,
//                1,
//                10,
//                1L,
//                1L
//        );
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(televisao)
//                .when()
//                .post("/televisao")
//                .then()
//                .statusCode(201)
//                .body(
//                        "idTelevisao", notNullValue(),
//                        "marca", is("Gabriel"),
//                        "modelo", is("GabrielModelo")
//                );
//    }
//
//    @Test
//    void testAlterar_TELEVISAO(){
//        TelevisaoRequestDTO televisao = new TelevisaoRequestDTO(
//                "Gabriel",
//                "GabrielModelo",
//                2500.0,
//                1,
//                2,
//                10,
//                1L,
//                1L
//        );
//
//        long id = televisaoService.create(televisao).idTelevisao();
//
//        TelevisaoRequestDTO televisaoAlterado = new TelevisaoRequestDTO(
//                "felipe",
//                "FelipeModelo",
//                3000.0,
//                2,
//                1,
//                20,
//                1L,
//                1L
//        );
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(televisaoAlterado)
//                .when()
//                .put("/televisao/" + id + "/atualizar")
//                .then()
//                .statusCode(204);
//
//        TelevisaoResponseDTO response = TelevisaoResponseDTO.valueOf(televisaoService.findById(id));
//
//        assertThat(response.marca(), is("felipe"));
//        assertThat(response.modelo(), is("FelipeModelo"));
//    }
//
//    @Test
//    void testDeletar_TELEVISAO(){
//        TelevisaoRequestDTO televisao = new TelevisaoRequestDTO(
//                "Gabriel",
//                "GabrielModelo",
//                2500.0,
//                1,
//                2,
//                10,
//                1L,
//                1L
//        );
//
//        long id = televisaoService.create(televisao).idTelevisao();
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .delete("/televisao/" + id + "/apagar")
//                .then()
//                .statusCode(204);
//
//        assertNull(televisaoService.findById(id), "A televisão deveria estar deletada e não encontrada.");
//    }
//
//    @Test
//    void testBuscarPorId_TELEVISAO(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        TelevisaoRequestDTO televisao = new TelevisaoRequestDTO(
//                "Gabriel",
//                "GabrielModelo",
//                1500.0,
//                1,
//                2,
//                10,
//                1L,
//                1L
//        );
//
//        long id = televisaoService.create(televisao).idTelevisao();
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/televisao/buscar-por-id/" + id)
//                .then()
//                .statusCode(200)
//                .body(
//                        "idTelevisao", notNullValue(),
//                        "marca", is("Gabriel"),
//                        "modelo", is("GabrielModelo")
//                );
//    }
//
//    @Test
//    void testBuscarTodos_TELEVISAO(){
//        // Gera o token para autenticação
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        // Requisição GET autenticada para buscar todas as televisões
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/televisao")
//                .then()
//                .statusCode(200);
//    }
//
//
//    @Test
//    void testBuscarTelevisaoPorModelo_TELEVISAO(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//        String modelo = "Crystal HUD";
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/televisao/" + modelo + "/buscar-televisao-por-modelo")
//                .then()
//                .statusCode(200)
//                .body("modelo", is("Crystal HUD"));
//    }
//}
