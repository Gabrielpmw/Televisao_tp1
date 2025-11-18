//package br.unitins.tp1;
//
//import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
//import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;
//import br.unitins.tp1.service.Auth.JwtServiceImpl;
//import br.unitins.tp1.service.Telefone.TelefoneServiceImpl;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import jakarta.annotation.security.RolesAllowed;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.Test;
//
//import java.util.Random;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@QuarkusTest
//public class TelefoneResourceTest {
//
//    @Inject
//    TelefoneServiceImpl telefoneService;
//
//    @Inject
//    JwtServiceImpl jwtService;
//
//    @Test
//    void testIncluir_TELEFONE(){
//        TelefoneRequestDTO telefone = new TelefoneRequestDTO("63", "992744773");
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(telefone)
//                .when()
//                .post("/telefone")
//                .then()
//                .statusCode(201)
//                .body("id", notNullValue(),
//                        "ddd", is("63"),
//                        "numero", is("992744773"));
//    }
//
//    @Test
//    void testAlterar_TELEFONE(){
//        TelefoneRequestDTO telefone = new TelefoneRequestDTO("63", "992744773");
//        long id = telefoneService.create(telefone).id();
//
//        TelefoneRequestDTO telefoneAlterado = new TelefoneRequestDTO("63", "984853811");
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(telefoneAlterado)
//                .when()
//                .put("/telefone/" + id + "/atualizar")
//                .then()
//                .statusCode(204);
//
//        TelefoneResponseDTO response = telefoneService.findById(id);
//
//        assertThat(response.ddd(), is("63"));
//        assertThat(response.numero(), is("984853811"));
//    }
//
//    @Test
//    void testBuscarPorId_TELEFONE(){
//        String numeroAleatorio = "9" + (10000000 + new Random().nextInt(90000000));
//        TelefoneRequestDTO telefone = new TelefoneRequestDTO("63", numeroAleatorio);
//        long id = telefoneService.create(telefone).id();
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/telefone/" + id + "/buscar-telefone-por-id")
//                .then()
//                .statusCode(200)
//                .body("id", equalTo((int) id),
//                        "ddd", is("63"),
//                        "numero", is(numeroAleatorio));
//    }
//
//    @Test
//    void testBuscarTodos_TELEFONE(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/telefone")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    void testBuscarTelefonePorDDD_TELEFONE(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//        String ddd = "11";
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/telefone/" + ddd + "/buscar-telefone-por-ddd")
//                .then()
//                .statusCode(200)
//                .body("size()", greaterThan(0))
//                .body("ddd", everyItem(is(ddd)));
//    }
//}
