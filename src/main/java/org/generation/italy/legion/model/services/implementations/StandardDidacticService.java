package org.generation.italy.legion.model.services.implementations;

import org.generation.italy.legion.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.legion.model.data.abstractions.CourseRepository;
import org.generation.italy.legion.model.data.abstractions.TeacherRepository;
import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.entities.Course;
import org.generation.italy.legion.model.entities.CourseEdition;
import org.generation.italy.legion.model.entities.Level;
import org.generation.italy.legion.model.entities.Teacher;
import org.generation.italy.legion.model.services.abstractions.AbstractDidacticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StandardDidacticService  implements AbstractDidacticService {
    //@Autowired
    private CourseRepository courseRepo;
    private TeacherRepository teacherRepo;
    private CourseEditionRepository editionRepo;
    @Autowired
    public StandardDidacticService(CourseRepository courseRepo,
                                   CourseEditionRepository editionRepo,
                                   TeacherRepository teacherRepo) {
        this.courseRepo = courseRepo;
        this.editionRepo = editionRepo;
        this.teacherRepo = teacherRepo;
    }


    @Override
    public List<Course> findCoursesByTitleContains(String part) throws DataException {
        return courseRepo.findByTitleContains(part);
    }

    @Override
    public boolean adjustActiveCourses(int numActive) throws DataException {
        //chiama il repository per scoprire quanti corsi attivi esistono
        //se i corsi attivi sono <= numActive ritorniamo false per segnalare
        //che non è stato necessario apportare alcuna modifica
        //altrimenti chiameremo un metodo sul repository che cancella gli
        //n corsi più vecchi
        int actives = courseRepo.countActiveCourses();
        if (actives <= numActive) {
            return false;
        }
        courseRepo.deactivateOldest(actives - numActive);
        return true;
    }

    @Override
    public Iterable<Course> findByTitleWhenActiveAndMinEdition(String part, boolean status, int minEditions) throws DataException {
        return courseRepo.findByTitleWhenActiveAndMinEdition(part,status,minEditions);
    }

    @Override
    public Iterable<Course> findByTitleWhenActive(String part, boolean status) throws DataException {
        return courseRepo.findByTitleContainingAndActiveTrue(part);
    }

    @Override
    public Iterable<Course> findByTitleAndMinEdition(String part, int minEditions) throws DataException {
        return courseRepo.findByTitleAndMinEdition(part,minEditions);
    }

    @Override
    public Iterable<Teacher> findWithCompetenceByLevel(Level teacherLevel) throws DataException {
        return teacherRepo.findByLevel(teacherLevel);
    }

    @Override
    public Iterable<Teacher> findWithSkillAndLevel(long idSkill, Level competenceLevel) {
        return teacherRepo.findWithSkillAndLevel(idSkill, competenceLevel);
    }
    @Override
    public double getTotalCost(){
        return editionRepo.getTotalCost();
    }

    @Override
    public Optional<CourseEdition> findFirstByOrderByCostDesc() {
        return editionRepo.findFirstByOrderByCostDesc();
    }

    @Override
    public double findAverageCost() {
        return editionRepo.findAverageCost();
    }

    @Override
    public Iterable<Double> findAllDuration() throws DataException {
        return editionRepo.findAllDuration();
    }

//    @Override
//    public Iterable<Double> findAllDuration() {
//        return repo.findAllDuration();
//    }

    @Override
    public Iterable<CourseEdition> findByCourseId(long courseId) {
        return editionRepo.findByCourseId(courseId);
    }

    @Override
    public Iterable<CourseEdition> findByCourseTitleAndStartedAtBetween(String titlePart, LocalDate startAt, LocalDate endAt) throws DataException {
        return editionRepo.findByCourseTitleContainsAndStartedAtBetween(titlePart,startAt,endAt);
    }
    @Override
    public Iterable<CourseEdition> findMedian(){
        return editionRepo.findMedian();
    }

    @Override
    public Optional<Double> getCourseEditionCostMode() {
        return editionRepo.getCourseEditionCostMode();
    }



}
