package org.generation.italy.legion.dtos;

import org.generation.italy.legion.model.entities.Competence;
import org.generation.italy.legion.model.entities.Level;
import org.generation.italy.legion.model.entities.Sex;
import org.generation.italy.legion.model.entities.Teacher;

import java.util.Optional;
import java.util.stream.StreamSupport;

public class SimpleTeacherDto {
    protected long id;
    protected String firstname;
    protected String lastname;

    protected Sex sex;
    private Level level;
    private boolean isEmployee;
    private String skillName;
    private long skillId;
    private Level skillLevel;


    public SimpleTeacherDto(long id, String firstname, String lastname, Sex sex, Level level, boolean isEmployee, String skillName, long skillId, Level skillLevel) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.level = level;
        this.isEmployee = isEmployee;
        this.skillName = skillName;
        this.skillId = skillId;
        this.skillLevel = skillLevel;
    }

    public static SimpleTeacherDto fromEntity(Teacher t, long skillId){
        Optional<Competence> oC = t.getCompetenceForSkill(skillId);
        Competence c = oC.get();
        String skillName = c.getSkill().getName();
        Level level = c.getLevel();
        return new SimpleTeacherDto(t.getId(), t.getFirstname(), t.getLastname(), t.getSex(), t.getLevel(), t.isEmployee(),
                skillName, skillId, level);
    }
    public static Iterable<SimpleTeacherDto> fromEntityIterable(Iterable<Teacher> iT, long skillId){
        return StreamSupport.stream(iT.spliterator(),false)
                .map(t->SimpleTeacherDto.fromEntity(t,skillId))
                .toList();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }
}
