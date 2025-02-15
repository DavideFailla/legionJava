package org.generation.italy.legion.model.data.implementations;

import org.generation.italy.legion.model.data.abstractions.CourseRepository;
import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.legion.model.entities.Course;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.function.Function;

import static org.generation.italy.legion.model.data.Constants.ENTITY_NOT_FOUND;


public class SerializedCourseRepository implements CourseRepository {
    private static final String SERIALIZED_FILE_NAME = "courses.ser";
    public static long nextID;
    private String filename;

    public SerializedCourseRepository(String filename) {
        this.filename = filename;
    }

    public SerializedCourseRepository() {
        this.filename = SERIALIZED_FILE_NAME;
    }

    @Override
    public <S extends Course> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Course> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Course> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Course> findAll()  {
        try {
            return load();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Errore nel findByTitleContains", e);
        }
    }

    @Override
    public List<Course> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Course entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Course> entities) {

    }

    @Override
    public void deleteAll() {

    }


    public Optional<Course> findById(long id) throws DataException {
        try {
            var courses = load();
            for(var c : courses) {
                if(c.getId() == id) {
                    return Optional.of(c);
                }
            }
            return Optional.empty();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new DataException("Errore nel create course", e);
        }
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        var courses = new ArrayList<Course>();
        try {
            var all = load();
            for (Course c : all) {
                if (c.getTitle().contains(part)) {
                    courses.add(c);
                }
            }
            return courses;
        } catch (IOException | ClassNotFoundException e) {
            throw new DataException("Errore nel findByTitleContains", e);
        }
    }


    public Course create(Course course) throws DataException {
        try {
            var courses = load();
            course.setId(++nextID);
            courses.add(course);
            store(courses);
            return course;
        } catch (IOException | ClassNotFoundException e) {
            throw new DataException("Errore nel create course", e);
        }
    }


    public void update(Course course) throws EntityNotFoundException, DataException {
        try {
            var courses = load();
            int pos = -1;
            for(int i=0; i < courses.size(); i++) {
                if(courses.get(i).getId() == course.getId()) {
                    pos = i;
                }
            }
            if(pos == -1) {
                throw new EntityNotFoundException(ENTITY_NOT_FOUND + course.getId());
            }
            courses.set(pos, course);
            store(courses);

        } catch (IOException | ClassNotFoundException e) {
            throw new DataException("Errore nel create course", e);
        }
    }


    public void deleteById(long id) throws EntityNotFoundException, DataException {
        try {
            var courses = load();
            for(Iterator<Course> it = courses.iterator(); it.hasNext();) {
                Course c = it.next();
                if(c.getId() == id) {
                    it.remove();
                    //courses.remove(c);
                    store(courses);
                    return;
                }
            }
            throw new EntityNotFoundException(ENTITY_NOT_FOUND + id);

        } catch (IOException | ClassNotFoundException e) {
            throw new DataException("Errore nel create course", e);
        }
    }

    @Override
    public int countActiveCourses() throws DataException {
        try {
          return (int) load().stream()
                    .filter(Course::isActive)
                    .count();
        } catch (IOException | ClassNotFoundException e) {
            throw new DataException("Errore nel conteggio dei corsi attivi", e);
        }
    }

    @Override
    public void deactivateOldest(int n) throws DataException {
       try {
           load().stream()
                   .filter(Course::isActive)
                   .sorted(Comparator.comparing(Course::getCreatedAt))
                   .limit(n)
                   .forEach(Course::deactivate);
       }catch (IOException | ClassNotFoundException e) {
           throw new DataException("Errore nella disattivazione dei corsi più vecchi", e);
       }


    }

    @Override
    public boolean adjustActiveCourses(int NumActive) throws DataException {
        return false;
    }

    @Override
    public Iterable<Course> findByTitleWhenActiveAndMinEdition(String part, boolean status, int minEditions) throws DataException {
        return null;
    }

    @Override
    public Iterable<Course> findByTitleContainingAndActiveTrue(String part) throws DataException {
        return null;
    }




    @Override
    public Iterable<Course> findByTitleAndMinEdition(String part, int minEditions) throws DataException {
        return null;
    }

    private List<Course> load() throws IOException, ClassNotFoundException {
        File f = new File(filename);
        if (!f.exists()) {
            f.createNewFile();
        }
        if (f.length() == 0) {
            return new ArrayList<>();
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<Course> courseList = (List<Course>) ois.readObject();
            return courseList;
        }
    }

    private void store(List<Course> courses) throws IOException{
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(courses);
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Course> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Course> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Course> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Course getOne(Long aLong) {
        return null;
    }

    @Override
    public Course getById(Long aLong) {
        return null;
    }

    @Override
    public Course getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Course> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Course> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Course> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Course> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Course> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Course> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Course, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Course> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return null;
    }
}