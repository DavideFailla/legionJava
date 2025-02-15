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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

import static org.generation.italy.legion.model.data.Constants.CSV_COURSE;
import static org.generation.italy.legion.model.data.Constants.ENTITY_NOT_FOUND;




public class CSVFileCourseRepository implements CourseRepository {
    private String fileName;
    public static long nextId;
    public static final String DEFAULT_FILE_NAME = "Corsi.csv";

    public CSVFileCourseRepository() {
        this.fileName = DEFAULT_FILE_NAME;
    }
    public CSVFileCourseRepository(String fileName) {
        this.fileName = fileName;
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
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            List<Course> courses = new ArrayList<>();
            for(String s : lines){
                courses.add(CSVToCourse(s));
            }
            return courses;
        }catch (IOException e){
            throw new RuntimeException("Errore nella lettura del file", e);
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


    public Optional<Course> findById(long id) throws DataException{             //!!RICORDATI!! se un metodo può dare un errore allora DEVI mettere il THROWS e l'exception che "lancerà"
        try{
            File f = new File(fileName);
            if (!f.exists()){
                f.createNewFile();
            }
            List<String> lines = Files.readAllLines(Paths.get(fileName));       //apro il file
            for (String s:lines){                                               //ciclo per ogni riga letta
                String[] trimmed = s.split(",");                          //uso un metodo della classe String che creerà una nuova stringa per ogni , che incontrerà, ogni stringa verrà salvata in un array
                long courseId = Long.parseLong(trimmed[0]);
                if (courseId == id){
                    Course found = CSVToCourse(s);
                    return Optional.of(found);
                }
            }
            return Optional.empty();
        }catch (IOException e){                                                  //"raccogliamo" IOException ma "lanciamo" un DataException di nostra creazione (si, si può fare) così da poter ancora utilizzare la nostra interfaccia nel miglior modo possibile
            throw new DataException("Errore nella lettura del file",e);          //lo lanciamo qui
        }
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            List<Course> courses = new ArrayList<>();
            for(String s : lines){
                String[] tokens = s.split(",");
                if(tokens[1].contains(part)){
                    Course found = CSVToCourse(s);
                    courses.add(found);
                }
            }
            return courses;
        }catch (IOException e){
            throw new DataException("Errore nella lettura del file", e);
        }
    }


    public Course create(Course course) throws DataException{
        /*
            FileOutputStream serve per scrivere nel file                !!(magari da richiedere)!!
            quel (append)true serve ad aggiungere una nuova riga alle riche esistenti, se non ci fosse sovrascriverebbe tutte le righe presenti nel file
            PintWriter sarà colui che effettivamente scriverà sul file
         */
        try (FileOutputStream output = new FileOutputStream(fileName,true);
             PrintWriter pw = new PrintWriter(output)){
            course.setId(++nextId);
            pw.println(courseToCSV(course));                //è qui che scrivo sul file (si con una println) richiamando un metodo creato da noi(sta verso la fine)
            return course;                                  //ovviamente nelle parentesi gli passo la stringa che voglio sivere sul file
        }catch (IOException e){
            throw new DataException("Errore nel salvataggio su file",e);
        }
    }


    public void update(Course course) throws EntityNotFoundException, DataException {
        try{
            int pos = -1;
            List<String> lines= Files.readAllLines(Paths.get(fileName));
            for(int i = 0; i < lines.size(); i++) {
                if(lines.get(i).startsWith(String.valueOf(course.getId()))) {
                    pos = i;
                    break;
                }
            }
            if(pos == -1) {
                throw new EntityNotFoundException(ENTITY_NOT_FOUND + course.getId());
            }
            lines.set(pos, courseToCSV(course));
            flushStringsToFile(lines);
        }catch(IOException e){
            throw new DataException("Errore nel cancellamento di una linea da file CSV", e);
        }
    }


    public void deleteById(long id) throws EntityNotFoundException, DataException {
        try{
            List<String> lines= Files.readAllLines(Paths.get(fileName));
            for(Iterator<String> it = lines.iterator(); it.hasNext();){
                String line = it.next();
                String[] tokens = line.split(",");
                long courseId=Long.parseLong(tokens[0]);
                if (courseId==id){
                    it.remove();
                    flushStringsToFile(lines);
                    return;
                }
            }
            throw new EntityNotFoundException(ENTITY_NOT_FOUND + id);
        }catch(IOException e){
            throw new DataException("Errore nel cancellamento di una linea da file CSV", e);
        }

    }

    @Override
    public int countActiveCourses() {
        return 0;
    }

    @Override
    public void deactivateOldest(int n) {

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





    public Iterable<Course> findByTitleWhenActive(String part, boolean status) throws DataException {
        return null;
    }

    @Override
    public Iterable<Course> findByTitleAndMinEdition(String part, int minEditions) throws DataException {
        return null;
    }

    public String courseToCSV(Course c){                //trasforma i dati presenti dell'oggetto in una stringa(che poi scriveremo sul file)
        return String.format(Locale.US,CSV_COURSE,c.getId(),c.getTitle()
                ,c.getDescription(),c.getProgram(),c.getDuration(),c.isActive(),c.getCreatedAt());
    }

    private Course CSVToCourse(String CSVLine){
        String[] tokens = CSVLine.split(",");
        return new Course(Long.parseLong(tokens[0]), tokens[1], tokens[2],
                tokens[3], Double.parseDouble(tokens[4]),Boolean.parseBoolean(tokens[5]) ,LocalDate.parse(tokens[6]));
    }
    private void flushStringsToFile(List<String> lines) throws FileNotFoundException {
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(fileName))) {
            for (String st : lines) {
                pw.println(st);
            }
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
