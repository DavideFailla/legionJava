package org.generation.italy.legion.restControllers;

import org.generation.italy.legion.dtos.CourseEditionDto;
import org.generation.italy.legion.model.data.abstractions.GenericRepository;
import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.entities.Course;
import org.generation.italy.legion.model.entities.CourseEdition;
import org.generation.italy.legion.model.services.abstractions.AbstractCrudDidacticService;
import org.generation.italy.legion.model.services.abstractions.AbstractDidacticService;
import org.generation.italy.legion.model.services.abstractions.GenericsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/courseEditions")
public class ApiCourseEditionController {

    public AbstractDidacticService service;

    private GenericsService<CourseEdition> crudService;

    @Autowired
    public ApiCourseEditionController(AbstractDidacticService service,
                                      GenericRepository<CourseEdition> editionRepo){
        this.service = service;
        this.crudService =new GenericsService<>(editionRepo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseEditionDto> findById(@PathVariable long id){
        try {
            Optional<CourseEdition> cE = crudService.findById(id);
            if(cE.isPresent()){
                return ResponseEntity.ok().body(CourseEditionDto.fromEntity(cE.get()));
            }
            return ResponseEntity.notFound().build();
        } catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping()
    public ResponseEntity<Iterable<CourseEditionDto>> findAll(){
        try {
            Iterable<CourseEdition> itE = crudService.findAll();
            return ResponseEntity.ok().body(CourseEditionDto.fromEntityIterable(itE));
        }catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotalCost(){
        try {
            double total = service.getTotalCost();
            return ResponseEntity.ok().body(total);
        }catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/expensive")
    public ResponseEntity<CourseEditionDto> findFirstByOrderByCostDesc() {
        try{
        Optional<CourseEdition> opCE = service.findFirstByOrderByCostDesc();
        if (opCE.isPresent()) {
            return ResponseEntity.ok().body(CourseEditionDto.fromEntity(opCE.get()));
        }
        return ResponseEntity.notFound().build();
        }catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/average")
    public ResponseEntity<Double> findAverageCost() {
        try {
            double average = service.findAverageCost();
            return ResponseEntity.ok().body(average);
        }catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/duration")
    public ResponseEntity<Iterable<Double>> findDurationFromCourse(){
        try {
            Iterable <Double> itD = service.findAllDuration();
            return ResponseEntity.ok().body(itD);
        }catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/courseId")
    public ResponseEntity<Iterable<CourseEditionDto>> findByCourseId(@PathVariable long id){
        try {
             Iterable<CourseEdition> cE = service.findByCourseId(id);

                return ResponseEntity.ok().body(CourseEditionDto.fromEntityIterable(cE));


        } catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }



    @GetMapping("/titleAndRange")
    public ResponseEntity<Iterable<CourseEditionDto>> findByTitlePartAndStartAtBetween(@RequestParam() String titlePart,
                                                                                       @RequestParam() LocalDate startAt,
                                                                                       @RequestParam() LocalDate endAt) {
        try {
            Iterable<CourseEdition> itE = service.findByCourseTitleAndStartedAtBetween(titlePart,startAt,endAt);
            System.out.println(itE);
            return ResponseEntity.ok().body(CourseEditionDto.fromEntityIterable(itE));
        } catch (DataException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/median")
    public ResponseEntity<Iterable<CourseEditionDto>> findMedian(){
        Iterable<CourseEdition> itE = service.findMedian();
        return ResponseEntity.ok().body(CourseEditionDto.fromEntityIterable(itE));
    }
    @GetMapping("/cost/mode")
    public ResponseEntity<Double> getCourseEditionCostMode(){
        Optional<Double> od = service.getCourseEditionCostMode();
        return od.map(aDouble -> ResponseEntity.ok().body(aDouble)).orElseGet(() -> ResponseEntity.notFound().build());
    }



}
