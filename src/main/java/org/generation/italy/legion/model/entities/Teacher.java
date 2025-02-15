package org.generation.italy.legion.model.entities;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
@Entity
@PrimaryKeyJoinColumn(name = "id_teacher")
@Table(name = "teacher")
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends Person implements WithId{
    @Column(name = "p_iva")
    private String pIVA;
    @Column(name = "is_employee")
    private boolean isEmployee;
    @Column(name = "hire_date")
    private LocalDate hireDate;
    @Column(name = "fire_date")
    private LocalDate fireDate;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "level")
    @Type(PostgreSQLEnumType.class)
    private Level level;
    public Teacher(long id, String firstname, String lastname, LocalDate dob, Sex sex, String email, String cellNumber,
                   Address address, String username, String password, Set<Competence> competences, String pIVA, boolean isEmployee,
                   LocalDate hireDate, LocalDate fireDate, Level level) {
        super(id, firstname, lastname, dob, sex, email, cellNumber, address, username, password, competences);
        this.pIVA = pIVA;
        this.isEmployee = isEmployee;
        this.hireDate = hireDate;
        this.fireDate = fireDate;
        this.level = level;
    }



}
