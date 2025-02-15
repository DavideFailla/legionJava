package org.generation.italy.legion.model.data.implementations;

import org.generation.italy.legion.model.data.abstractions.CourseRepository;
import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.entities.Course;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.legion.model.data.implementations.HibernateTestUtils.insertCourse;
import static org.generation.italy.legion.model.data.implementations.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class HibernateCourseRepositoryTest {
    private Course c1;
    private Course c2;
    private Course c3;
    private Session s;
    private CourseRepository repo;


    @BeforeEach
    void setUp() {
        c1 = new Course(0, TITLE1, DESCRIPTION, PROGRAM, DURATION, IS_ACTIVE, CREATED_AT);
        c2 = new Course(0, TITLE2, DESCRIPTION2, PROGRAM2, DURATION2, IS_ACTIVE, CREATED_AT.plusDays(1));
        c3 = new Course(0, TITLE1, DESCRIPTION3, PROGRAM3, DURATION3, IS_ACTIVE, CREATED_AT.plusMonths(2));
        s = HibernateUtils.getSessionFactory().openSession();
        s.getTransaction().begin();
        insertCourse(c1, s);
        insertCourse(c2, s);
        insertCourse(c3, s);
        //repo = new CourseRepository(s);
    }

    @AfterEach
    void tearDown() {
        s.getTransaction().rollback();
        s.close();
    }

    @Test
    void findById() {

            Optional<Course> c = repo.findById(c1.getId());
            assertFalse(c.isEmpty());
            Course course = c.get();
            assertEquals(c1.getId(), course.getId());
            assertEquals(c1.getTitle(), course.getTitle());


    }

    @Test
    void countActiveCourses() {
    }

    @Test
    void deactivateOldest() {

        try {
            repo.deactivateOldest(2);
            //s.flush();
            //s.clear();
            System.out.println("Prima di leggere i corsi da database");
            List<Course> all = s.createQuery("from Course", Course.class).list()
                    .stream().sorted(Comparator.comparing(Course::getCreatedAt)).toList();
            assertEquals(3, all.size());
            assertEquals(all.get(0).getId() , c1.getId());
            assertFalse(all.get(0).isActive());
            assertEquals(all.get(1).getId() , c2.getId());
            assertFalse(all.get(1).isActive());
            assertEquals(all.get(2).getId() , c3.getId());
            assertTrue(all.get(2).isActive());

        } catch (DataException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void adjustActiveCourses() {
    }

    @Test
    void findByTitleContains_should_courses_when_present() {
        try {
            List<Course> cl = repo.findByTitleContains("1_T");
            assertEquals(2, cl.size());
            assertTrue(cl.get(0).getId() == c1.getId() || cl.get(0).getId() == c3.getId());
            assertTrue(cl.get(1).getId() == c1.getId() || cl.get(1).getId() == c3.getId());
            assertTrue(cl.get(0).getTitle().equals(c1.getTitle()) || cl.get(0).getTitle().equals(c3.getTitle()));
            assertTrue(cl.get(1).getTitle().equals(c1.getTitle()) || cl.get(1).getTitle().equals(c3.getTitle()));
        } catch (DataException e) {
            fail(e.getMessage());

        }

    }
}