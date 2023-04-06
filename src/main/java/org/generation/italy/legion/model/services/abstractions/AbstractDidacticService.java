package org.generation.italy.legion.model.services.abstractions;

import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.entities.Course;
import org.generation.italy.legion.model.entities.CourseEdition;
import org.generation.italy.legion.model.entities.Level;
import org.generation.italy.legion.model.entities.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AbstractDidacticService {

    List<Course> findCoursesByTitleContains(String part) throws DataException;

    boolean adjustActiveCourses(int numActive) throws DataException; //se corsi attivi > numActive disattiva i più vecchi

    Iterable<Course> findByTitleWhenActiveAndMinEdition(String part, boolean status, int minEditions) throws DataException;

    Iterable<Course> findByTitleWhenActive(String part, boolean status) throws DataException;

    Iterable<Course> findByTitleAndMinEdition(String part, int minEditions) throws DataException;
    Iterable<Teacher> findWithCompetenceByLevel(Level teacherLevel) throws DataException;
    Iterable<Teacher> findWithSkillAndLevel(long idSkill, Level competenceLevel) throws DataException;
    double getTotalCost() throws DataException;

    Optional<CourseEdition> findFirstByOrderByCostDesc() throws DataException;

    double findAverageCost() throws DataException;

    Iterable<Double> findAllDuration() throws DataException;

    Iterable<CourseEdition> findByCourseId (long courseId) throws DataException;

    Iterable<CourseEdition> findByCourseTitleAndStartedAtBetween(String titlePart, LocalDate startAt, LocalDate endAt) throws DataException;  //ok

    Iterable<CourseEdition> findMedian();

    Optional<Double> getCourseEditionCostMode();
}
