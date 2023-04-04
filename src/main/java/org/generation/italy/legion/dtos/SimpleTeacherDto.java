package org.generation.italy.legion.dtos;

import org.generation.italy.legion.model.entities.*;

import java.util.Optional;
import java.util.stream.StreamSupport;

public class SimpleTeacherDto {
    private long id;
    private String firstname;
    private String lastname;
    private Sex sex;
    private Level level;
    private String pIVA;
    private String skillName;
    private long skillId;
    private Level skillLevel;

    public SimpleTeacherDto(long id, String firstname, String lastname, Sex sex, Level level, String pIVA,
                            String skillName,long skillId,Level skillLevel) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.level = level;
        this.pIVA = pIVA;
        this.skillName = skillName;
        this.skillId = skillId;
        this.skillLevel = skillLevel;
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

    public String getpIVA() {
        return pIVA;
    }

    public void setpIVA(String pIVA) {
        this.pIVA = pIVA;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public long getSkillId() {
        return skillId;
    }

    public void setSkillId(long skillId) {
        this.skillId = skillId;
    }

    public Level getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(Level skillLevel) {
        this.skillLevel = skillLevel;
    }

    public static SimpleTeacherDto fromEntity(Teacher t, long skillId){
        Optional<Competence> oC = t.getCompetenceForSkill(skillId);
        Competence c = oC.get();
        String skillName = c.getSkill().getName();
        Level level = c.getLevel();
        return new SimpleTeacherDto(t.getId(),t.getFirstname(),t.getLastname(),t.getSex(),t.getLevel(),t.getpIVA(),
                skillName, skillId, level);
    }

    public static Iterable<SimpleTeacherDto> fromEntityIterable(Iterable<Teacher> iT, long skillId){
        return StreamSupport.stream(iT.spliterator(), false)
                .map(t -> SimpleTeacherDto.fromEntity(t, skillId)).toList();
    }
}
