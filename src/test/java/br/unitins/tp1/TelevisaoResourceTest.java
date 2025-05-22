package br.unitins.tp1;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.service.Televisao.TelevisaoServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class TelevisaoResourceTest {

    @Inject
    TelevisaoServiceImpl televisaoService;

    @Test
    void testIncluir_TELEVISAO(){
        TelevisaoRequestDTO televisao = new TelevisaoRequestDTO("Gabriel", "GabrielModelo", 1, 2, 1L, 1L);

        given()
                .contentType(ContentType.JSON)
                .body(televisao)
                .when().post("/televisao")
                .then().statusCode(201)
                .body("idTelevisao", notNullValue(),
                        "marca", is("Gabriel"),
                        "modelo", is("GabrielModelo"));
    }

    @Test
    void testAlterar_TELEVISAO(){
        TelevisaoRequestDTO televisao = new TelevisaoRequestDTO("Gabriel", "GabrielModelo", 1, 2, 1L, 1L);

        long id = televisaoService.create(televisao).idTelevisao();

        TelevisaoRequestDTO televisaoAlterado = new TelevisaoRequestDTO("felipe", "FelipeModelo", 1, 2, 1L, 1L);

        given()
                .contentType(ContentType.JSON)
                .body(televisaoAlterado)
                .when().put("/televisao/" + id + "/atualizar")
                .then().statusCode(204);

        TelevisaoResponseDTO response = TelevisaoResponseDTO.valueOf(televisaoService.findById(id));

        assertThat(response.marca(), is("felipe"));
        assertThat(response.modelo(), is("FelipeModelo"));
    }

    @Test
    void testDeletar_TELEVISAO(){
        TelevisaoRequestDTO televisao = new TelevisaoRequestDTO("Gabriel", "GabrielModelo", 1, 2, 1L, 1L);

        long id = televisaoService.create(televisao).idTelevisao();

        given()
                .when().delete("/televisao/" + id + "/apagar")
                .then().statusCode(204);

        TelevisaoResponseDTO response = TelevisaoResponseDTO.valueOf(televisaoService.findById(id));
        assertNull(response);
    }

    @Test
    void testBuscarPorId_TELEVISAO(){
        TelevisaoRequestDTO televisao = new TelevisaoRequestDTO("Gabriel", "GabrielModelo", 1, 2, 1L, 1L);

        long id = televisaoService.create(televisao).idTelevisao();

        given()
                .contentType(ContentType.JSON)
                .when().get("/televisao/" + id +"/buscar-televisao-por-id")
                .then().statusCode(200)
                .body("idTelevisao", notNullValue(),
                        "marca", is("Gabriel"),
                        "modelo", is("GabrielModelo"));
    }

    @Test
    void testBuscarTodos_TELEVISAO(){
        given()
                .when().get("televisao")
                .then().statusCode(200);
    }


    @Test
    void testBuscarTelevisaoPorModelo_TELEVISAO(){
        String modelo = "Crystal HUD";

        given()
                .when()
                .get("/televisao/" + modelo + "/buscar-televisao-por-modelo")
                .then().statusCode(200)
                .body("modelo", is("Crystal HUD"));
    }
}
