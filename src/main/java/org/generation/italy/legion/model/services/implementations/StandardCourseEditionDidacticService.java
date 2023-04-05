package org.generation.italy.legion.model.services.implementations;

import org.generation.italy.legion.model.data.abstractions.CourseEditionRepository;
import org.generation.italy.legion.model.data.abstractions.TeacherRepository;
import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.legion.model.entities.CourseEdition;
import org.generation.italy.legion.model.services.abstractions.AbstractCourseEditionDidacticService;
import org.generation.italy.legion.model.services.abstractions.AbstractCrudDidacticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class StandardCourseEditionDidacticService implements AbstractCrudDidacticService<CourseEdition>, AbstractCourseEditionDidacticService {

    private CourseEditionRepository repo;

    @Autowired
    public StandardCourseEditionDidacticService(CourseEditionRepository repo){
        this.repo = repo;
    }
    @Override
    public Iterable<CourseEdition> findAll() throws DataException {
        return repo.findAll();
    }

    @Override
    public Optional<CourseEdition> findById(long id) throws DataException {
        return repo.findById(id);
    }

    @Override
    public CourseEdition create(CourseEdition entity) throws DataException {
        return repo.save(entity);
    }

    @Override
    public void update(CourseEdition entity) throws EntityNotFoundException, DataException {
        repo.save(entity);
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
        repo.deleteById(id);
    }
}
