package org.gs;

import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

@QuarkusTest
class MovieResourceTest {

  @InjectMock
  MovieRepository movieRepository;

  @Inject
  MovieResource movieResource;

  private Movie movie;

  // @BeforeEach
  // void setUp() {}

  // @Test
  // void getAll() {}

  // @Test
  // void getByIdOK() {}

  // @Test
  // void getByIdKO() {}

  // @Test
  // void getByTitleOK() {}

  // @Test
  // void getByTitleKO() {}

  // @Test
  // void getByCountry() {}

  // @Test
  // void createOK() {}

  // @Test
  // void createKO() {}

  // @Test
  // void updateByIdOK() {}

  // @Test
  // void updateByIdKO() {}

  // @Test
  // void deleteByIdOK() {}

  // @Test
  // void deleteByIdKO() {}
}
