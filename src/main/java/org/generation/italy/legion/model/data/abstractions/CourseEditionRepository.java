package org.generation.italy.legion.model.data.abstractions;

import org.generation.italy.legion.model.entities.CourseEdition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CourseEditionRepository extends CrudRepository<CourseEdition, Long> {
    @Query("select sum(ce.cost) from CourseEdition ce")
    double getTotalCost();

    @Query("""
            from CourseEdition ce
            order by ce.cost desc
            limit 1
            """)
    Optional<CourseEdition> findMostExpensive();

    @Query("""
            select sum(ce.cost) / count(ce)
            from CourseEdition ce
            """)
    double findAverageCost();

    @Query("""
            """)
    Iterable<Double> findALlDuration();
    Iterable<CourseEdition> findByCourseId(long courseId);
    Iterable<CourseEdition> findByCourseTitleAndStartedAtBetween(String titlePart, LocalDate startAt, LocalDate endAt);


//    Iterable<CourseEdition> findMedian();
//    Optional<Double> getCourseEditionCostMode();

}