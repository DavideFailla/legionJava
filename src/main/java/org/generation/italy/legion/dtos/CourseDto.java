package org.generation.italy.legion.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.generation.italy.legion.model.entities.Course;

import java.time.LocalDate;
import java.util.stream.StreamSupport;

public class CourseDto {
    private long id;
    private String title;
    private String description;
    private String program;
    private double duration;
    private boolean active;
    private String createdAt;

    public CourseDto(long id, String title, String description, String program, double duration, boolean active, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.program = program;
        this.duration = duration;
        this.active = active;
        this.createdAt = createdAt;
    }

    public static CourseDto fromEntity(Course c){
        return new CourseDto(c.getId(),c.getTitle(),c.getDescription(),c.getProgram(),
                c.getDuration(),c.isActive(),c.getCreatedAt().toString());
    }

    public static Iterable<CourseDto> fromEntityIterable(Iterable<Course> c){
        return StreamSupport.stream(c.spliterator(), false)
                .map(s -> CourseDto.fromEntity(s)).toList();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
