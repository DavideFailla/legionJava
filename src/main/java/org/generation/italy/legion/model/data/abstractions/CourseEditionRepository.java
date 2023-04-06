package org.generation.italy.legion.model.data.abstractions;

import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.entities.CourseEdition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CourseEditionRepository extends GenericRepository<CourseEdition> , CourseEditionRepositoryCustom {
      @Query("select sum(ce.cost) from CourseEdition ce")
      double getTotalCost();  //ok

      Optional<CourseEdition> findFirstByOrderByCostDesc(); //ok
      @Query(" select avg(ce.cost) from CourseEdition ce ")
      double findAverageCost();  //ok
      @Query("SELECT c.duration FROM CourseEdition ce JOIN ce.course c")
      Iterable<Double> findAllDuration();

      Iterable<CourseEdition> findByCourseId (long courseId);  //ok

      Iterable<CourseEdition> findByCourseTitleContainsAndStartedAtBetween(String titlePart, LocalDate startAt, LocalDate endAt) throws DataException;  //ok


}