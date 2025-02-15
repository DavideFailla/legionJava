package org.generation.italy.legion.model.entities;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person")
public abstract class Person {
    @Id
    @GeneratedValue(generator = "person_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "person_generator", sequenceName = "person_sequence", allocationSize = 1)
    @Column(name= "id_person")
    protected long id;
    protected String firstname;
    protected String lastname;
    protected LocalDate dob;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "sex")
    @Type(PostgreSQLEnumType.class)
    protected Sex sex;
    protected String email;
    @Column(name = "cell_number")
    protected String cellNumber;
    @ManyToOne
    @JoinColumn(name = "id_address")
    protected Address address;
    protected String username;
    protected String password;
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Competence> competences;
    public Person(){}

    public Person(long id, String firstname, String lastname, LocalDate dob, Sex sex, String email, String cellNumber,
                  Address address, String username, String password, Set<Competence> competences) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.sex = sex;
        this.email = email;
        this.cellNumber = cellNumber;
        this.address = address;
        this.username = username;
        this.password = password;
        this.competences = competences;
    }

    public void addCompetence(Competence c) {
        competences.add(c);
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Sex getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public Address getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Competence> getCompetences() {
        return competences;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCompetences(Set<Competence> competences) {
        this.competences = competences;
    }

    public Optional<Competence> getCompetenceForSkill(long idSkill){
        return competences.stream().filter(c->c.getSkill().getId() == idSkill).findFirst();
    }

}
