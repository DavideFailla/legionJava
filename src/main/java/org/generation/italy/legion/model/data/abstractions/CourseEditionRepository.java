package org.generation.italy.legion.model.data.abstractions;

import org.generation.italy.legion.model.entities.CourseEdition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CourseEditionRepository extends CrudRepository<CourseEdition, Long> {
      @Query("select sum(ce.cost) from CourseEdition ce")
      double getTotalCost();  //ok
      @Query(""" 
              from CourseEdition ce
              order by ce.cost desc
              limit 1
              """)
      Optional<CourseEdition> findMostExpensive(); //ok
      @Query("""
              select sum(ce.cost)/count(ce)
              from CourseEdition ce
              """)
      double findAverageCost();  //ok
      @Query("""
              select c.duration
              from CourseEdition ce join Course c on ce.c.id
              """)
      Iterable<Double> findAllDuration();

      Iterable<CourseEdition> findByCourseId (long courseId);  //ok

      Iterable<CourseEdition> findByCourseTitleAndStartedAtBetween(String titlePart, LocalDate startAt, LocalDate endAt);  //ok


//    Iterable<CourseEdition> findMedian();
//    Optional<Double> getCourseEditionCostMode();

}