//package br.unitins.tp1;
//
//import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioRequestDTO;
//import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioResponseDTO;
//import br.unitins.tp1.service.Auth.JwtServiceImpl;
//import br.unitins.tp1.service.Municipio.MunicipioServiceImpl;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import jakarta.annotation.security.RolesAllowed;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.Test;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//
//@QuarkusTest
//public class MunicipioResourceTest {
//
//    @Inject
//    MunicipioServiceImpl municipioService;
//
//    @Inject
//    JwtServiceImpl jwtService;
//
//    @Test
//    void testIncluir_MUNICIPIO(){
//        MunicipioRequestDTO requestDTO = new MunicipioRequestDTO("Palmas", 1L);
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .contentType(ContentType.JSON)
//                .header("Authorization", "Bearer " + token)
//                .body(requestDTO)
//                .when().post("municipio")
//                .then().statusCode(201)
//                .body("idMunicipio", notNullValue(),
//                        "municipio", is("Palmas"),
//                        "estado.idEstado", is(1),
//                        "estado.nome", is("São Paulo"),
//                        "estado.sigla", is("SP"));
//    }
//
//    @Test
//    void testAlterar_MUNICIPIO(){
//        MunicipioRequestDTO municipio = new MunicipioRequestDTO("Gurupi", 1L);
//
//        Long id = municipioService.create(municipio).idMunicipio();
//
//        MunicipioRequestDTO municipioAlterado = new MunicipioRequestDTO("Guarai", 1L);
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .contentType(ContentType.JSON)
//                .header("Authorization", "Bearer " + token)
//                .body(municipioAlterado)
//                .when().put("municipio/" + id + "/atualizar")
//                .then()
//                .statusCode(204);
//
//        MunicipioResponseDTO response = municipioService.findById(id);
//        assertThat(response.municipio(), is("Guarai"));
//        assertThat(response.estado().idEstado(), is(1L));
//    }
//
//    @Test
//    void testDeletar_MUNICIPIO(){
//        MunicipioRequestDTO dto = new MunicipioRequestDTO("Araguaína", 1L);
//        Long id = municipioService.create(dto).idMunicipio();
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when().delete("municipio/" + id + "/deletar")
//                .then().statusCode(204);
//
//        MunicipioResponseDTO responseDTO = municipioService.findById(id);
//        assertNull(responseDTO);
//    }
//
//    @Test
//    void testBuscarTodos_MUNICIPIO(){
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .when().get("/municipio")
//                .then().statusCode(200);
//    }
//
//    @Test
//    void testBucarPorId_MUNICIPIO(){
//        MunicipioRequestDTO municipioRequestDTO = new MunicipioRequestDTO("Palmares", 1L);
//        Long id = municipioService.create(municipioRequestDTO).idMunicipio();
//
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .contentType(ContentType.JSON)
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/municipio/" + id + "/procurar-id")
//                .then()
//                .statusCode(200)
//                .body("idMunicipio", is(id.intValue()),
//                        "municipio", is("Palmares"),
//                        "estado.idEstado", is(1),
//                        "estado.nome", is("São Paulo"),
//                        "estado.sigla", is("SP"));
//    }
//
//    @Test
//    void testBuscarEnderecoPorMunicipio_MUNICIPIO(){
//        long id = 1L;
//        String token = jwtService.generateJwt("gabriel", "adm");
//
//        given()
//                .contentType(ContentType.JSON)
//                .header("Authorization", "Bearer " + token)
//                .when().get("municipio/" + id + "/procurar-endereco")
//                .then().statusCode(200)
//                .body("$", not(empty()))
//                .body("municipio.municipio", everyItem(equalTo("São Paulo")));
//    }
//}
