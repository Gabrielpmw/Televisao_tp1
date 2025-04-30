package br.unitins.tp1;

import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoRequestDTO;
import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoResponseDTO;
import br.unitins.tp1.repository.EstadoRepository;
import br.unitins.tp1.service.Estado.EstadoServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class EstadoResourceTest {

    @Inject
    EstadoServiceImpl estadoService;



    @Test
    void testIncluir(){
        EstadoRequestDTO estadoRequestDTO = new EstadoRequestDTO("Tocantins 2", "TO-2");

        given()
                .contentType(ContentType.JSON)
                .body(estadoRequestDTO)
                .when().post("/Estado")
                .then()
                    .statusCode(201).body("idEstado", notNullValue(),
                        "nome", is("Tocantins 2"),
                        "sigla", is("TO-2"));
    }

    static Long id = null;

    @Test
    void testAlterar(){
        EstadoRequestDTO estadoRequestDTO = new EstadoRequestDTO("Tocantins 3", "TO-2");

        id = estadoService.create(estadoRequestDTO).idEstado();

        EstadoRequestDTO estadoAlterado = new EstadoRequestDTO("Tocantins - Alterado", "TO - Alterado");

        given()
                .contentType(ContentType.JSON)
                .body(estadoAlterado)
                .when().put("/Estado/" + id + "/atualizar")
                .then()
                .statusCode(204);

        EstadoResponseDTO responseDTO = estadoService.findById(id);
        assertThat(responseDTO.nome(), is("Tocantins - Alterado"));
        assertThat(responseDTO.sigla(), is("TO - Alterado"));
    }

    @Test
    void testDeletar(){
        given()
                .when()
                .delete("/Estado/" + id + "/deletar")
                .then().statusCode(204);

        EstadoResponseDTO responseDTO = estadoService.findById(id);
        assertNull(responseDTO);
    }

    @Test
    void testBuscarTodos(){
        given()
                .when()
                .get("/Estado")
                .then()
                .statusCode(200).body("$.size()", greaterThanOrEqualTo(2));
    }


    @Test
    void testBucarPorId(){
        EstadoRequestDTO estadoRequestDTO = new EstadoRequestDTO("Bahia", "BA");
        id = estadoService.create(estadoRequestDTO).idEstado();

        given()
                .when()
                .get("/Estado/" + id + "/buscar-id")
                .then()
                .statusCode(200)
                .body("idEstado", is(id.intValue()),
                        "nome", is("Bahia"),
                        "sigla", is("BA"),
                        "$", aMapWithSize(3));

    }

    @Test
    void testBuscarPorNome(){

        EstadoRequestDTO estadoRequestDTO = new EstadoRequestDTO("Acre-test", "AC");
        Long id = estadoService.create(estadoRequestDTO).idEstado();

        given()
                .when()
                .get("/Estado/Acre-test/buscar-nome")
                .then()
                .statusCode(200)
                .body("idEstado", is(id.intValue()))
                .body("nome", is("Acre-test"))
                .body("sigla", is("AC"));
    }

    @Test
    void testOrdenarEstado(){

        given()
                .when()
                .get("Estado/ordenar")
                .then()
                .statusCode(200)
                .body("nome[0]", is("Bahia"),
                        "nome[1]", is("Minas Gerais"),
                        "nome[2]", is("Paraná"));
    }
}
