//package br.unitins.tp1;
//
//import br.unitins.tp1.model.DTO.Funcionario.FuncionarioRequestDTO;
//import br.unitins.tp1.service.Auth.JwtServiceImpl;
//import br.unitins.tp1.service.Funcionario.FuncionarioServiceImpl;
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
//import static org.hamcrest.Matchers.greaterThan;
//
//@QuarkusTest
//public class FuncionarioResourceTest {
//
//    @Inject
//    JwtServiceImpl jwtService;
//
//    @Inject
//    FuncionarioServiceImpl funcionarioService;
//
//    @Test
//    void testIncluir_FUNCIONARIO() {
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        FuncionarioRequestDTO funcionario = new FuncionarioRequestDTO(
//                "João Silva",
//                "98765432199",
//                "joaosilva_test2",
//                "senha123"
//        );
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(funcionario)
//                .when()
//                .post("/funcionario")
//                .then()
//                .statusCode(201)
//                .body("nome", is("João Silva"))
//                .body("cpf", is("98765432199"))
//                .body("username", is("joaosilva_test2"));
//    }
//
//    @Test
//    void testAtualizar_FUNCIONARIO() {
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        FuncionarioRequestDTO funcionarioCriado = new FuncionarioRequestDTO(
//                "Maria Oliveira",
//                "98765432100",
//                "mariaoliveira",
//                "senha123"
//        );
//
//        long id = funcionarioService.create(funcionarioCriado).id();
//
//        FuncionarioRequestDTO funcionarioAtualizado = new FuncionarioRequestDTO(
//                "Maria Oliveira Silva",
//                "98765432100",
//                "mariaoliveira",
//                "novaSenha456"
//        );
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .body(funcionarioAtualizado)
//                .when()
//                .put("/funcionario/" + id + "/atualizar")
//                .then()
//                .statusCode(204);
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/funcionario/" + id + "/procurar-por-id")
//                .then()
//                .statusCode(200)
//                .body("nome", is("Maria Oliveira Silva"))
//                .body("cpf", is("98765432100"))
//                .body("username", is("mariaoliveira"));
//    }
//
//    @Test
//    void testDeletar_FUNCIONARIO() {
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        FuncionarioRequestDTO funcionarioCriado = new FuncionarioRequestDTO(
//                "Carlos Pereira",
//                "11122233344",
//                "carlospereira",
//                "senha123"
//        );
//
//        long id = funcionarioService.create(funcionarioCriado).id();
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .delete("/funcionario/" + id + "/deletar")
//                .then()
//                .statusCode(204);
//    }
//
//    @Test
//    void testProcurarPorId_FUNCIONARIO() {
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        FuncionarioRequestDTO funcionarioCriado = new FuncionarioRequestDTO(
//                "Ana Souza",
//                "55566677788",
//                "anasouza",
//                "senha123"
//        );
//
//        long id = funcionarioService.create(funcionarioCriado).id();
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/funcionario/" + id + "/procurar-por-id")
//                .then()
//                .statusCode(200)
//                .body("nome", is("Ana Souza"))
//                .body("cpf", is("55566677788"))
//                .body("username", is("anasouza"));
//    }
//
//    @Test
//    void testProcurarTodos_FUNCIONARIO() {
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        FuncionarioRequestDTO funcionarioCriado = new FuncionarioRequestDTO(
//                "Lucas Martins",
//                "99988877766",
//                "lucasmartins",
//                "senha123"
//        );
//        funcionarioService.create(funcionarioCriado);
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/funcionario")
//                .then()
//                .statusCode(200)
//                .body("size()", greaterThan(0));
//    }
//
//    @Test
//    void testBuscarFuncionarioPorUsername() {
//        FuncionarioRequestDTO funcionario = new FuncionarioRequestDTO(
//                "João da Silva",
//                "12345678999",
//                "joaosilva_test",
//                "senha123"
//        );
//
//        funcionarioService.create(funcionario);
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/funcionario/joaosilva_test/procurar-por-username")
//                .then()
//                .statusCode(200)
//                .body("nome", is("João da Silva"))
//                .body("username", is("joaosilva_test"))
//                .body("cpf", is("12345678999"));
//    }
//}
