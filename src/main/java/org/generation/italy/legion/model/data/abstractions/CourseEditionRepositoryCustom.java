package org.generation.italy.legion.model.data.abstractions;

import org.generation.italy.legion.model.entities.CourseEdition;

import java.util.Optional;

public interface CourseEditionRepositoryCustom {


    Iterable<CourseEdition> findMedian();
    Optional<Double> getCourseEditionCostMode();

}
