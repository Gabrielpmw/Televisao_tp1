package br.unitins.tp1;

import br.unitins.tp1.model.DTO.Fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.model.DTO.Fornecedor.FornecedorResponseDTO;
import br.unitins.tp1.service.Fornecedor.FornecedorServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class FornecedorResourceTest {

    @Inject
    FornecedorServiceImpl fornecedorService;

    @Test
    void incluirTest_FORNECEDOR(){
        List<Long> telefone_fornecedor = new ArrayList<>();
        telefone_fornecedor.add(6L);
        telefone_fornecedor.add(5L);

        List<Long> televisao_fornecedor = new ArrayList<>();
        televisao_fornecedor.add(1L);
        televisao_fornecedor.add(2L);
        televisao_fornecedor.add(5L);

        FornecedorRequestDTO fornecedor = new FornecedorRequestDTO("Gabriel", "12345678000190" ,telefone_fornecedor, televisao_fornecedor);

        given()
                .contentType(ContentType.JSON)
                .body(fornecedor)
                .when().post("/fornecedor")
                .then().statusCode(201)
                .body("id", notNullValue(),
                        "nome", is("Gabriel"),
                        "cnpj", is("12345678000190"));

    }

    @Test
    void testAlterar_FORNECEDOR(){
        List<Long> telefone_fornecedor = new ArrayList<>();
        telefone_fornecedor.add(6L);
        telefone_fornecedor.add(5L);

        List<Long> televisao_fornecedor = new ArrayList<>();
        televisao_fornecedor.add(1L);
        televisao_fornecedor.add(2L);
        televisao_fornecedor.add(5L);

        FornecedorRequestDTO fornecedor = new FornecedorRequestDTO("Gabriel", "123" ,telefone_fornecedor, televisao_fornecedor);

        long id = fornecedorService.create(fornecedor).id();

        List<Long> telefoneAlterado = new ArrayList<>();
        telefoneAlterado.add(6L);

        List<Long> televisaoAlterado = new ArrayList<>();
        televisaoAlterado.add(1L);
        televisaoAlterado.add(2L);

        FornecedorRequestDTO fornecedorAlterado = new FornecedorRequestDTO("Milena", "321" ,telefoneAlterado, televisaoAlterado);

        given()
                .contentType(ContentType.JSON)
                .body(fornecedorAlterado)
                .when().put("/fornecedor/" + id + "/atualizar")
                .then().statusCode(204);

        FornecedorResponseDTO response = fornecedorService.findById(id);

        assertThat(response.nome(), is("Milena"));
        assertThat(response.cnpj(), is("321"));
    }

    @Test
    void testDelete_FORNECEDOR(){
        List<Long> telefone_fornecedor = new ArrayList<>();
        telefone_fornecedor.add(6L);
        telefone_fornecedor.add(5L);

        List<Long> televisao_fornecedor = new ArrayList<>();
        televisao_fornecedor.add(1L);
        televisao_fornecedor.add(2L);
        televisao_fornecedor.add(5L);

        FornecedorRequestDTO fornecedor = new FornecedorRequestDTO("Gabriel", "12345678000190" ,telefone_fornecedor, televisao_fornecedor);

        long id = fornecedorService.create(fornecedor).id();

        given()
                .when().delete("/fornecedor/" + id + "/deletar")
                .then().statusCode(204);

        FornecedorResponseDTO response = fornecedorService.findById(id);
        assertNull(response);
    }

    @Test
    void testBuscarPorId_FORNECEDOR(){
        List<Long> telefone_fornecedor = new ArrayList<>();
        telefone_fornecedor.add(6L);
        telefone_fornecedor.add(5L);

        List<Long> televisao_fornecedor = new ArrayList<>();
        televisao_fornecedor.add(1L);
        televisao_fornecedor.add(2L);
        televisao_fornecedor.add(5L);

        FornecedorRequestDTO fornecedor = new FornecedorRequestDTO("Gabriel", "12345678000190" ,telefone_fornecedor, televisao_fornecedor);

        long id = fornecedorService.create(fornecedor).id();

        given()
                .contentType(ContentType.JSON)
                .when().get("/fornecedor/" + id + "/buscar-fornecedor-por-id")
                .then().statusCode(200)
                .body("id", notNullValue(),
                        "nome", is("Gabriel"),
                        "cnpj", is("12345678000190"));
    }

    @Test
    void testBuscarTodos_FORNECEDOR(){
       given()
               .when().get("/fornecedor/")
               .then().statusCode(200);
    }

    @Test
    void testBuscarTelevisaoPorFornecedor_FORNECEDOR(){
        long fornecedor = 1L;

        given()
                .when()
                .get("/fornecedor/" + fornecedor + "/buscar-televisao-por-id_fornecedor")
                .then().statusCode(200);
    }

    @Test
    void testBuscarFornecedorPorTelefone_FORNECEDOR(){
        long telefone = 1L;

        given()
                .when()
                .get("/fornecedor/" + telefone + "/buscar-fornecedor-por-id_telefone")
                .then().statusCode(200);
    }
}
