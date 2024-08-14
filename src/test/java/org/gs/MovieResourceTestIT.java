package org.gs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.common.constraint.Assert;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieResourceTestIT {

  // @Test
  // @Order(1)
  // void getAll() {
  // }

  // @Test
  // @Order(1)
  // void getById() {
  //   Assert.assertFalse(false);
  // }

  // @Test
  // @Order(1)
  // void getByIdKO() {}

  // @Test
  // @Order(1)
  // void getByTitle() {}

  // @Test
  // @Order(1)
  // void getByTitleKO() {}

  // @Test
  // @Order(2)
  // void getByCountry() {}

  // @Test
  // @Order(2)
  // void getByCountryKO() {}

  // @Test
  // @Order(3)
  // void create() {}

  // @Test
  // @Order(4)
  // void updateById() {}

  // @Test
  // @Order(4)
  // void updateByIdKO() {}

  // @Test
  // @Order(5)
  // void deleteById() {}

  // @Test
  // @Order(5)
  // void deleteByIdKO() {}
}
