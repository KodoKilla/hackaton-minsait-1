package org.gs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class MovieResourceTest {

  @InjectMock
  MovieRepository movieRepository;

  @Inject
  MovieResource movieResource;

  @Test
  void getAll() {
    // Arrange
    List<Movie> movies = new ArrayList<>();
    movies.add(new Movie(1L, "Movie 1", "Country 1", "Description 1", "Director 1"));
    movies.add(new Movie(2L, "Movie 2", "Country 2", "Description 2", "Director 2"));
    Mockito.when(movieRepository.listAll()).thenReturn(movies);

    // Act
    Response response = movieResource.getAll();

    // Assert
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    assertEquals(movies, response.getEntity());
  }

  @Test
  void getByIdOK() {
    // Arrange
    Long id = 1L;
    Movie movie = new Movie(id, "Movie 1", "Country 1", "Description 1", "Director 1");
    Mockito.when(movieRepository.findByIdOptional(id)).thenReturn(Optional.of(movie));

    // Act
    Response response = movieResource.getById(id);

    // Assert
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    assertEquals(movie, response.getEntity());
  }

  @Test
  void getByIdKO() {
    // Arrange
    Long id = 1L;
    Mockito.when(movieRepository.findByIdOptional(id)).thenReturn(Optional.empty());

    // Act
    Response response = movieResource.getById(id);

    // Assert
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
  }

  @Test
  void getByTitleOK() {
    // Arrange
    String title = "FirstMovie";
    List<Movie> movies = new ArrayList<>();
    movies.add(new Movie(1L, title, "Country 1", "Description 1", "Director 1"));

    PanacheQuery<Movie> query = Mockito.mock(PanacheQuery.class);
    Mockito.when(query.singleResultOptional()).thenReturn(Optional.of(movies.get(0)));

    Mockito.when(movieRepository.find("title", title)).thenReturn(query);

    // Act
    Response response = movieResource.getByTitle(title);

    // Assert
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    assertEquals(movies.get(0), response.getEntity());
  }

  @Test
  void getByTitleKO() {
    // Arrange
    String title = "Movie 1";
    PanacheQuery<Movie> query = Mockito.mock(PanacheQuery.class);
    Mockito.when(query.singleResultOptional()).thenReturn(Optional.empty());

    Mockito.when(movieRepository.find("title", title)).thenReturn(query);

    // Act
    Response response = movieResource.getByTitle(title);

    // Assert
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
  }

  @Test
  void getByCountry() {
    // Arrange
    String country = "Country 1";
    List<Movie> movies = new ArrayList<>();
    movies.add(new Movie(1L, "Movie 1", country, "Description 1", "Director 1"));
    Mockito.when(movieRepository.findByCountry(country)).thenReturn(movies);

    // Act
    Response response = movieResource.getByCountry(country);

    // Assert
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    assertEquals(movies, response.getEntity());
  }

  @Test
  void createOK() {
    // Arrange
    Movie movie = new Movie(1L, "Movie 1", "Country 1", "Description 1", "Director 1");
    doNothing().when(movieRepository).persist(movie);
    doReturn(true).when(movieRepository).isPersistent(movie);

    // Act
    Response response = movieResource.create(movie);

    // Assert
    assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
  }

  @Test
  void createKO() {
    // Arrange
    Movie movie = new Movie(1L, "Movie 1", "Country 1", "Description 1", "Director 1");
    doNothing().when(movieRepository).persist(movie);
    doReturn(false).when(movieRepository).isPersistent(movie);

    // Act
    Response response = movieResource.create(movie);

    // Assert
    assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
  }

  @Test
  void updateByIdOK() {
    // Arrange
    Long id = 1L;
    Movie movie = new Movie(id, "Movie 1", "Country 1", "Description 1", "Director 1");
    Mockito.when(movieRepository.findByIdOptional(id)).thenReturn(Optional.of(movie));
    doNothing().when(movieRepository).persist(movie);

    // Act
    Response response = movieResource.updateById(id, movie);

    // Assert
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    assertEquals(movie, response.getEntity());
  }

  @Test
  void updateByIdKO() {
    // Arrange
    Long id = 1L;
    Movie movie = new Movie(id, "Movie 1", "Country 1", "Description 1", "Director 1");
    Mockito.when(movieRepository.findByIdOptional(id)).thenReturn(Optional.empty());

    // Act
    Response response = movieResource.updateById(id, movie);

    // Assert
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
  }

  @Test
  void deleteByIdOK() {
    // Arrange
    Long id = 1L;

    doReturn(true).when(movieRepository).deleteById(id);
    // Act
    Response response = movieResource.deleteById(id);

    // Assert
    assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
  }

  @Test
  void deleteByIdKO() {
    // Arrange
    Long id = 0L;

    doReturn(false).when(movieRepository).deleteById(id);
    // Act
    Response response = movieResource.deleteById(id);

    // Assert
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
  }
}
