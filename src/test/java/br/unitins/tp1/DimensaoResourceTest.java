package br.unitins.tp1;

import br.unitins.tp1.model.DTO.Televisao.DimensaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.DimensaoResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.service.Dimensao.DimensaoServiceImpl;
import br.unitins.tp1.service.Televisao.TelevisaoServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class DimensaoResourceTest {

    @Inject
    DimensaoServiceImpl dimensaoService;

    @Inject
    TelevisaoServiceImpl televisaoService;

    @Test
    void testIncluir_DIMENSAO() {
        DimensaoRequestDTO dto = new DimensaoRequestDTO(100, 100, 100);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/dimensao")
                .then().statusCode(201)
                .body("id", notNullValue(),
                        "comprimento", is(100),
                        "altura", is(100),
                        "polegada", is(100));
    }

    @Test
    void testAtualizar_DIMENSAO() {
        DimensaoRequestDTO dimensao = new DimensaoRequestDTO(100, 100, 100);
        long id = dimensaoService.create(dimensao).id();

        DimensaoRequestDTO dimensaoAlterado = new DimensaoRequestDTO(50, 50, 50);

        given()
                .contentType(ContentType.JSON)
                .body(dimensaoAlterado)
                .when().put("/dimensao/" + id + "/atualizar")
                .then().statusCode(204);

        DimensaoResponseDTO responseDTO = dimensaoService.findById(id);

        assertThat(responseDTO.comprimento(), is(50));
        assertThat(responseDTO.altura(), is(50));
        assertThat(responseDTO.polegada(), is(50));
    }

    @Test
    void testDeletar_DIMENSAO() {
        DimensaoRequestDTO dimensao = new DimensaoRequestDTO(100, 100, 100);
        long id = dimensaoService.create(dimensao).id();

        given()
                .contentType(ContentType.JSON)
                .body(dimensao)
                .when().delete("/dimensao/" + id + "/deletar")
                .then().statusCode(204);

        DimensaoResponseDTO responseDTO = dimensaoService.findById(id);
        assertNull(responseDTO);
    }

    @Test
    void testBuscarPorId_DIMENSAO() {
        DimensaoRequestDTO dimensao = new DimensaoRequestDTO(100, 100, 100);
        long id = dimensaoService.create(dimensao).id();

        given()
                .contentType(ContentType.JSON)
                .body(dimensao)
                .when().get("dimensao/" + id + "/buscar-por-id")
                .then().statusCode(200)
                .body("id", notNullValue(),
                        "comprimento", is(100),
                        "altura", is(100),
                        "polegada", is(100));
    }

    @Test
    void testBuscarTodos_DIMENSAO(){
        given()
                .when().get("/dimensao")
                .then().statusCode(200);
    }

    @Test
    void testBuscarTvPorIdDimensao_DIMENSAO() {
        DimensaoRequestDTO dimensao = new DimensaoRequestDTO(100, 100, 100);
        long idDimensao = dimensaoService.create(dimensao).id();

        TelevisaoRequestDTO tv1 = new TelevisaoRequestDTO("Samsung", "Neo QLED", 2, 1, idDimensao, 1);
        TelevisaoRequestDTO tv2 = new TelevisaoRequestDTO("LG", "OLED B2", 2, 2, idDimensao, 2);

        televisaoService.create(tv1);
        televisaoService.create(tv2);

        given()
                .when().get("/dimensao/" + idDimensao + "/buscar-televisao-por-id_dimensao")
                .then().statusCode(200);

        List<TelevisaoResponseDTO> lista = dimensaoService.findTelevisaoByDimensao(idDimensao);
        assertThat(lista.size(), is(2));

        for (TelevisaoResponseDTO tv : lista) {
            assertThat(tv.dimensao().id(), is(idDimensao));
        }
    }
}
