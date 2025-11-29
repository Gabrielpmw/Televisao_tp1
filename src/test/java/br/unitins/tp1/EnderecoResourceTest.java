//package br.unitins.tp1;
//
//
//import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoRequestDTO;
//import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoResponseDTO;
//import br.unitins.tp1.service.Auth.JwtServiceImpl;
//import br.unitins.tp1.service.Endereco.EnderecoServiceImpl;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.Test;
//
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.jupiter.api.Assertions.fail;
//
//
//@QuarkusTest
//public class EnderecoResourceTest {
//
//    @Inject
//    EnderecoServiceImpl enderecoService;
//
//    @Inject
//    JwtServiceImpl jwtService;
//
//    @Test
//    void testIncluir_ENDERECO() {
//        String token = jwtService.generateJwt("italo", "cliente"); // ou "adm", conforme o perfil
//
//        EnderecoRequestDTO endereco = new EnderecoRequestDTO(
//                "77023-032",
//                "Alameda 4",
//                24,
//                "Rua da torre",
//                1L
//        );
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(endereco)
//                .when()
//                .post("/endereco")
//                .then()
//                .statusCode(201)
//                .body("idEndereco", notNullValue(),
//                        "cep", is("77023-032"),
//                        "bairro", is("Alameda 4"),
//                        "complemento", is("Rua da torre"),
//                        "municipio.municipio", is("São Paulo"),
//                        "municipio.estado.idEstado", is(1));
//    }
//
//    @Test
//    void testAlterar_ENDERECO() {
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        EnderecoRequestDTO endereco = new EnderecoRequestDTO(
//                "77023-032",
//                "Alameda 4",
//                24,
//                "Rua da torre",
//                1L
//        );
//
//        Integer idInteger = given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(endereco)
//                .when()
//                .post("/endereco")
//                .then()
//                .statusCode(201)
//                .extract()
//                .path("idEndereco");
//
//        Long id = idInteger.longValue();
//
//        EnderecoRequestDTO enderecoAlterado = new EnderecoRequestDTO(
//                "77023-032",
//                "Alameda 10",
//                22,
//                "Rua da torre",
//                1L
//        );
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(enderecoAlterado)
//                .when()
//                .put("/endereco/" + id + "/atualizar")
//                .then()
//                .statusCode(204);
//
//        EnderecoResponseDTO response = enderecoService.findById(id);
//
//        assertThat(response.cep(), is("77023-032"));
//        assertThat(response.bairro(), is("Alameda 10"));
//        assertThat(response.numero(), is(22L));
//        assertThat(response.complemento(), is("Rua da torre"));
//        assertThat(response.municipio().municipio(), is("São Paulo"));
//        assertThat(response.municipio().estado().idEstado(), is(1L));
//    }
//
//    @Test
//    void testDeletar_ENDERECO(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        EnderecoRequestDTO endereco = new EnderecoRequestDTO(
//                "77023-032",
//                "Alameda 4",
//                24,
//                "Rua da torre",
//                1L
//        );
//
//        Integer idInteger = given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(endereco)
//                .when()
//                .post("/endereco")
//                .then()
//                .statusCode(201)
//                .extract()
//                .path("idEndereco");
//
//        Long id = idInteger.longValue();
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .delete("/endereco/" + id + "/deletar")
//                .then()
//                .statusCode(204);
//
//        try {
//            EnderecoResponseDTO responseDTO = enderecoService.findById(id);
//            fail("Esperava que o endereço não fosse encontrado, mas encontrou: " + responseDTO);
//        } catch (Exception e) {
//
//        }
//    }
//
//    @Test
//    void testBuscarTodos_ENDERECO(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/endereco")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    void testBuscarPorId_ENDERECO(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        EnderecoRequestDTO endereco = new EnderecoRequestDTO(
//                "77023-032",
//                "Alameda 4",
//                24,
//                "Rua da torre",
//                1L
//        );
//
//        Integer idInteger = given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(endereco)
//                .when()
//                .post("/endereco")
//                .then()
//                .statusCode(201)
//                .extract()
//                .path("idEndereco");
//
//        Long id = idInteger.longValue();
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/endereco/" + id + "/buscar-id")
//                .then()
//                .statusCode(200)
//                .body("idEndereco", notNullValue(),
//                        "cep", is("77023-032"),
//                        "bairro", is("Alameda 4"),
//                        "complemento", is("Rua da torre"),
//                        "municipio.municipio", is("São Paulo"),
//                        "municipio.estado.idEstado", is(1));
//    }
//}
