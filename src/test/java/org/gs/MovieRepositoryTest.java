package org.gs;

import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.common.constraint.Assert;

import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class MovieRepositoryTest {

  @Inject
  MovieRepository movieRepository;

  // @Test
  // void findByCountryOK() {
  // }

  // @Test
  // void findByCountryKO() {}
}
