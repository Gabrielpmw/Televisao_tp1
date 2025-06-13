package br.unitins.tp1;

import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoRequestDTO;
import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoResponseDTO;
import br.unitins.tp1.service.Estado.EstadoServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
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
    void testIncluir_ESTADO(){
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
    void testAlterar_ESTADO(){
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
    void testDeletar_ESTADO(){
        given()
                .when()
                .delete("/Estado/" + id + "/deletar")
                .then().statusCode(204);

        EstadoResponseDTO responseDTO = estadoService.findById(id);
        assertNull(responseDTO);
    }

    @Test
    void testBuscarTodos_ESTADO(){
        given()
                .when().get("/Estado")
                .then().statusCode(200);
    }


    @Test
    void testBucarPorId_ESTADO(){
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
    void testBuscarPorNome_ESTADO(){

        EstadoRequestDTO estadoRequestDTO = new EstadoRequestDTO("Acre-test", "AC");
        Long id = estadoService.create(estadoRequestDTO).idEstado();

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/Estado/Acre-test/buscar-nome")
                .then()
                .statusCode(200)
                .body("idEstado", is(id.intValue()))
                .body("nome", is("Acre-test"))
                .body("sigla", is("AC"));
    }

    @Test
    void testBuscarMunicipioPorEstado_ESTADO(){
        Long id = 1L;

        given()
                .contentType(ContentType.JSON)
                .when().get("/Estado/" + id + "/buscar-municipio")
                .then().statusCode(200)
                .body("$", not(empty()))
                .body("municipio", everyItem(notNullValue()))
                .body("estado.nome", everyItem(equalTo("São Paulo")))
                .body("estado.sigla", everyItem(equalTo("SP")));
    }
}
