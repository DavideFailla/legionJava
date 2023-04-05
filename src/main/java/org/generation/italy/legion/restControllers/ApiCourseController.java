package org.generation.italy.legion.restControllers;

import org.generation.italy.legion.dtos.CourseDto;
import org.generation.italy.legion.dtos.TeacherDto;
import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.entities.Course;
import org.generation.italy.legion.model.entities.Teacher;
import org.generation.italy.legion.model.services.abstractions.AbstractCourseDidacticService;
import org.generation.italy.legion.model.services.abstractions.AbstractTeacherDidacticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/courses")
public class ApiCourseController {
    private AbstractCourseDidacticService service;

    @Autowired
    public ApiCourseController(AbstractCourseDidacticService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> findById(@PathVariable long id){
        try {
            Optional<Course> c = service.findById(id);
            if(c.isPresent()){
                return ResponseEntity.ok().body(CourseDto.fromEntity(c.get()));
            }
            return ResponseEntity.notFound().build();
        } catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping()
    public ResponseEntity<Iterable<CourseDto>> findWithSkillAndLevel(@RequestParam() String part,
                                                                     @RequestParam(required = false) Boolean isActive,
                                                                     @RequestParam(required = false) Integer minEdition){
        try {
            if(isActive == null && minEdition == null) {
                Iterable<Course> iC = service.findCoursesByTitleContains(part);
                return ResponseEntity.ok().body(CourseDto.fromEntityIterable(iC));
            }
            if( minEdition == null){
                Iterable<Course>  iC = service.findByTitleWhenActive(part,true);
                return ResponseEntity.ok().body(CourseDto.fromEntityIterable(iC));
            }
            if(isActive == null){
                Iterable<Course> iC = service.findByTitleAndMinEdition(part,minEdition);
                return ResponseEntity.ok().body(CourseDto.fromEntityIterable(iC));
            }
            Iterable<Course> iC = service.findByTitleWhenActiveAndMinEdition(part,true,minEdition);
            return ResponseEntity.ok().body(CourseDto.fromEntityIterable(iC));
        } catch (DataException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
