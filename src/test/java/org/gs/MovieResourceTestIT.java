package org.gs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import io.quarkus.test.junit.QuarkusTest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import org.junit.jupiter.api.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieResourceTestIT {

  @Test
  @Order(1)
  void getAll() {
    given()
      .when()
      .get("/movies")
      .then()
      .statusCode(200)
      .contentType(MediaType.APPLICATION_JSON)
      .body("size()", equalTo(2));
  }

  @Test
  @Order(1)
  void getById() {
    given()
      .when()
      .get("/movies/1")
      .then()
      .statusCode(200)
      .contentType(MediaType.APPLICATION_JSON)
      .body("id", equalTo(1))
      .body("title", equalTo("FirstMovie"))
      .body("director", equalTo("Me"));
  }

  @Test
  @Order(1)
  void getByIdKO() {
    given()
      .when()
      .get("/movies/100")
      .then()
      .statusCode(404);
  }

  @Test
  @Order(1)
  void getByTitle() {
    given()
      .when()
      .get("/movies/title/FirstMovie")
      .then()
      .statusCode(200)
      .contentType(MediaType.APPLICATION_JSON)
      .body("id", equalTo(1))
      .body("title", equalTo("FirstMovie"))
      .body("director", equalTo("Me"));
  }

  @Test
  @Order(1)
  void getByTitleKO() {
    given()
      .when()
      .get("/movies/title/NonExistingMovie")
      .then()
      .statusCode(404);
  }

  @Test
  @Order(2)
  void getByCountry() {
    given()
      .when()
      .get("/movies/country/Planet")
      .then()
      .statusCode(200)
      .contentType(MediaType.APPLICATION_JSON)
      .body("size()", equalTo(2));
  }

  @Test
  @Order(2)
  void getByCountryKO() {
    given()
      .when()
      .get("/movies/country/NonExistingCountry")
      .then()
      .statusCode(200)
      .contentType(MediaType.APPLICATION_JSON)
      .body("size()", equalTo(0));
  }

  @Test
  @Order(3)
  void create() {
    JsonObject movieJson = Json.createObjectBuilder()
      .add("title", "NewMovie")
      .add("director", "NewDirector")
      .add("country", "NewCountry")
      .build();

    given()
      .contentType(MediaType.APPLICATION_JSON)
      .body(movieJson.toString())
      .when()
      .post("/movies")
      .then()
      .statusCode(201)
      .header("Location", containsString("/movies/"));
  }

  @Test
  @Order(4)
  void updateById() {
    JsonObject movieJson = Json.createObjectBuilder()
      .add("title", "UpdatedMovie")
      .add("director", "UpdatedDirector")
      .add("country", "UpdatedCountry")
      .build();

    given()
      .contentType(MediaType.APPLICATION_JSON)
      .body(movieJson.toString())
      .when()
      .put("/movies/1")
      .then()
      .statusCode(200);
  }

  @Test
  @Order(4)
  void updateByIdKO() {
    JsonObject movieJson = Json.createObjectBuilder()
      .add("title", "UpdatedMovie")
      .add("director", "UpdatedDirector")
      .add("country", "UpdatedCountry")
      .build();

    given()
      .contentType(MediaType.APPLICATION_JSON)
      .body(movieJson.toString())
      .when()
      .put("/movies/100")
      .then()
      .statusCode(404);
  }

  @Test
  @Order(5)
  void deleteById() {
    given()
      .when()
      .delete("/movies/1")
      .then()
      .statusCode(204);
  }

  @Test
  @Order(5)
  void deleteByIdKO() {
    given()
      .when()
      .delete("/movies/100")
      .then()
      .statusCode(404);
  }
}
