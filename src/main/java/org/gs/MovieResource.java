package org.gs;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

  @Inject MovieRepository movieRepository;

  /**
   * Retrieves all movies.
   *
   * @return A Response object containing a list of Movie objects in JSON format.
  */
  @GET
  public Response getAll() {
    List<Movie> movies = movieRepository.listAll();
    return Response.ok(movies).build();
  }

  /**
   * Retrieves a movie by its ID.
   *
   * @param id The ID of the movie to retrieve.
   * @return A Response object containing the Movie object in JSON format.
  */
  @GET
  @Path("{id}")
  public Response getById(@PathParam("id") Long id) {
    return movieRepository
        .findByIdOptional(id)
        .map(movie -> Response.ok(movie).build())
        .orElse(Response.status(NOT_FOUND).build());
  }

  /**
   * Retrieves a movie by its title.
   *
   * @param title The title of the movie to retrieve.
   * @return A Response object containing the Movie object in JSON format.
  */
  @GET
  @Path("title/{title}")
  public Response getByTitle(@PathParam("title") String title) {
    return movieRepository
        .find("title", title)
        .singleResultOptional()
        .map(movie -> Response.ok(movie).build())
        .orElse(Response.status(NOT_FOUND).build());
  }

  /**
   * Retrieves a movie by its country.
   *
   * @param country The country of the movie to retrieve.
   * @return A Response object containing a list of Movie objects in JSON format.
  */
  @GET
  @Path("country/{country}")
  public Response getByCountry(@PathParam("country") String country) {
    List<Movie> movies = movieRepository.findByCountry(country);
    return Response.ok(movies).build();
  }

  /**
   * Creates a new movie.
   *
   * @param movie The movie to create.
   * @return A Response object with a status code of 201 if the movie was created successfully, or a
   *     status code of 400 if the movie was not created.
  */
  @POST
  @Transactional
  public Response create(Movie movie) {
    movieRepository.persist(movie);
    if (movieRepository.isPersistent(movie)) {
      return Response.created(URI.create("/movies/" + movie.getId())).build();
    }
    return Response.status(BAD_REQUEST).build();
  }

  /**
   * Updates a movie by its ID.
   *
   * @param id The ID of the movie to update.
   * @param movie The updated movie object.
   * @return A Response object containing the updated Movie object in JSON format if the movie was found and updated successfully, or a
   *     status code of 404 if the movie was not found.
  */
  @PUT
  @Path("{id}")
  @Transactional
  public Response updateById(@PathParam("id") Long id, Movie movie) {
    return movieRepository
        .findByIdOptional(id)
        .map(
            m -> {
              m.setTitle(movie.getTitle());
              return Response.ok(m).build();
            })
        .orElse(Response.status(NOT_FOUND).build());
  }

  /**
   * Deletes a movie by its ID.
   *
   * @param id The ID of the movie to delete.
   * @return A Response object with a status code of 204 if the movie was deleted successfully, or a
   *     status code of 404 if the movie was not found.
  */
  @DELETE
  @Path("{id}")
  @Transactional
  public Response deleteById(@PathParam("id") Long id) {
    boolean deleted = movieRepository.deleteById(id);
    return deleted ? Response.noContent().build() : Response.status(NOT_FOUND).build();
  }
}
