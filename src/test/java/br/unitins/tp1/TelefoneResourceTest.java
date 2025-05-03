package br.unitins.tp1;

import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;
import br.unitins.tp1.service.Telefone.TelefoneServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class TelefoneResourceTest {

    @Inject
    TelefoneServiceImpl telefoneService;

    @Test
    void testIncluir_TELEFONE(){
        TelefoneRequestDTO telefone = new TelefoneRequestDTO("63", "992744773");

        given()
                .contentType(ContentType.JSON)
                .body(telefone)
                .when().post("/telefone")
                .then().statusCode(201)
                .body("id", notNullValue(),
                        "ddd", is("63"),
                        "numero", is("992744773"));
    }

    @Test
    void testAlterar_TELEFONE(){
        TelefoneRequestDTO telefone = new TelefoneRequestDTO("63", "992744773");
        long id = telefoneService.create(telefone).id();

        TelefoneRequestDTO telefoneAlterado = new TelefoneRequestDTO("63", "984853811");

        given()
                .contentType(ContentType.JSON)
                .body(telefoneAlterado)
                .when().put("telefone/" + id + "/atualizar")
                .then().statusCode(204);

        TelefoneResponseDTO response = telefoneService.findById(id);

        assertThat(response.ddd(), is("63"));
        assertThat(response.numero(), is("984853811"));
    }

    @Test
    void testDeletar_TELEFONE(){
        TelefoneRequestDTO telefone = new TelefoneRequestDTO("63", "992744773");
        long id = telefoneService.create(telefone).id();

        given()
                .when().delete("telefone/" + id + "/deletar")
                .then().statusCode(204);

        TelefoneResponseDTO response = telefoneService.findById(id);
        assertNull(response);
    }

    @Test
    void testBuscarPorId_TELEFONE(){
        TelefoneRequestDTO telefone = new TelefoneRequestDTO("63", "992744773");
        long id = telefoneService.create(telefone).id();

        given()
                .contentType(ContentType.JSON)
                .when().get("telefone/" + id + "/buscar-telefone-por-id")
                .then()
                .statusCode(200)
                .body("id", notNullValue(),
                        "ddd", is("63"),
                        "numero", is("992744773"));
    }

    @Test
    void testBuscarTodos_TELEFONE(){
        given()
                .when().get("/telefone/")
                .then().statusCode(200);
    }

    @Test
    void testBuscarTelefonePorDDD_TELEFONE(){
        given()
                .when().get("/telefone/")
                .then().statusCode(200)
                .body("findAll { it.ddd == '11' }.size()", greaterThan(0));
    }
}
