package org.generation.italy.legion.dtos;

import org.generation.italy.legion.model.entities.CourseEdition;

import java.util.List;

public class SimpleCourseDto {
    private long id;
    private String title;
    private boolean isActive;
    private List<CourseEdition> minEditions;

    public SimpleCourseDto(long id, String title, boolean isActive, List<CourseEdition> minEditions) {
        this.id = id;
        this.title = title;
        this.isActive = isActive;
        this.minEditions = minEditions;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<CourseEdition> getMinEditions() {
        return minEditions;
    }

    public void setMinEditions(List<CourseEdition> minEditions) {
        this.minEditions = minEditions;
    }
}
