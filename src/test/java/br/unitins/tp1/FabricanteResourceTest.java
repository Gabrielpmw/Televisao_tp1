//package br.unitins.tp1;
//
//import br.unitins.tp1.model.DTO.Fabricante.FabricanteRequestDTO;
//import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
//import br.unitins.tp1.service.Auth.JwtServiceImpl;
//import br.unitins.tp1.service.Fabricante.FabricanteServiceImpl;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import jakarta.annotation.security.RolesAllowed;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//@QuarkusTest
//public class FabricanteResourceTest {
//
//    @Inject
//    FabricanteServiceImpl fabricanteService;
//
//    @Inject
//    JwtServiceImpl jwtService;
//
//    @Test
//    void testIncluir_FABRICANTE(){
//        List<Long> telefone = new ArrayList<>();
//        telefone.add(1L);
//        telefone.add(2L);
//
//        FabricanteRequestDTO fabricante = new FabricanteRequestDTO(
//                "Gabriel",
//                "12345678901234",
//                "Brasil",
//                telefone
//        );
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(fabricante)
//                .when()
//                .post("/fabricante")
//                .then()
//                .statusCode(201)
//                .body("id", notNullValue())
//                .body("nome", is("Gabriel"))
//                .body("cnpj", is("12345678901234"))
//                .body("paisSede", is("Brasil"));
//    }
//
//    @Test
//    void testAlterar_FABRICANTE(){
//        List<Long> telefone_fabricante = new ArrayList<>();
//        telefone_fabricante.add(1L);
//        telefone_fabricante.add(2L);
//
//        FabricanteRequestDTO fabricante = new FabricanteRequestDTO("Gabriel", "123", "Brasil", telefone_fabricante);
//
//        long id = fabricanteService.create(fabricante).id();
//
//        List<Long> telefone_alterado = new ArrayList<>();
//        telefone_alterado.add(1L);
//        telefone_alterado.add(2L);
//
//        FabricanteRequestDTO fabricanteAlterado = new FabricanteRequestDTO("Milena", "321", "Japão", telefone_alterado);
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(fabricanteAlterado)
//                .when()
//                .put("/fabricante/" + id + "/atualizar")
//                .then()
//                .statusCode(204);
//
//        FabricanteResponseDTO response = fabricanteService.findById(id);
//
//        assertThat(response.nome(), is("Milena"));
//        assertThat(response.cnpj(), is("321"));
//        assertThat(response.paisSede(), is("Japão"));
//    }
//
//    @Test
//    void testDeletar_FABRICANTE(){
//        List<Long> telefone_fabricante = new ArrayList<>();
//        telefone_fabricante.add(1L);
//        telefone_fabricante.add(2L);
//
//        FabricanteRequestDTO fabricante = new FabricanteRequestDTO("Gabriel", "123", "Brasil", telefone_fabricante);
//
//        long id = fabricanteService.create(fabricante).id();
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .delete("/fabricante/" + id + "/apagar")
//                .then()
//                .statusCode(204);
//
//        FabricanteResponseDTO response = fabricanteService.findById(id);
//        assertNull(response);
//    }
//
//    @Test
//    void testBuscarPorId_FABRICANTE(){
//        List<Long> telefone_fabricante = new ArrayList<>();
//        telefone_fabricante.add(1L);
//        telefone_fabricante.add(2L);
//
//        FabricanteRequestDTO fabricante = new FabricanteRequestDTO("Gabriel", "123", "Brasil", telefone_fabricante);
//
//        long id = fabricanteService.create(fabricante).id();
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/fabricante/" + id + "/buscar-fabricante-por-id")
//                .then()
//                .statusCode(200)
//                .body("id", notNullValue(),
//                        "nome", is("Gabriel"),
//                        "cnpj", is("123"),
//                        "paisSede", is("Brasil"));
//    }
//
//    @Test
//    void buscarTodos_FABRICANTE(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/fabricante")
//                .then()
//                .statusCode(200)
//                .body("$.size()", greaterThan(0));
//    }
//
//    @Test
//    void buscarTelevisaoPorFabricante_FABRICANTE(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/fabricante/1/buscar-televisao-por-id_fabricante")
//                .then()
//                .statusCode(200)
//                .body("size()", greaterThan(0));
//    }
//
//    @Test
//    void buscarFabricantePorNome_FABRICANTE(){
//        String nome = "Samsung";
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/fabricante/" + nome + "/buscar-fabricante-por-nome")
//                .then()
//                .statusCode(200)
//                .body("nome", is("Samsung"))
//                .body("cnpj", is("12345678000199"));
//    }
//}
