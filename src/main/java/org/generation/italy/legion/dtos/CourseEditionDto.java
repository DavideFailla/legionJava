package org.generation.italy.legion.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.generation.italy.legion.model.entities.Classroom;
import org.generation.italy.legion.model.entities.Course;
import org.generation.italy.legion.model.entities.CourseEdition;
import org.generation.italy.legion.model.entities.EditionModule;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

public class CourseEditionDto {
    private long id;
    @JsonIgnore
    private Course course;
    private String startedAt;
    private double cost;

    public CourseEditionDto(long id, Course course, String startedAt, double cost) {
        this.id = id;
        this.course = course;
        this.startedAt = startedAt;
        this.cost = cost;
    }

    public static CourseEditionDto fromEntity(CourseEdition ce){
        return new CourseEditionDto(ce.getId(), ce.getCourse(),ce.getStartedAt().toString(), ce.getCost());
    }

    public static Iterable<CourseEditionDto> fromEntityIterable(Iterable<CourseEdition> cE){
        return StreamSupport.stream(cE.spliterator(),false)
                .map(s -> CourseEditionDto.fromEntity(s)).toList();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
