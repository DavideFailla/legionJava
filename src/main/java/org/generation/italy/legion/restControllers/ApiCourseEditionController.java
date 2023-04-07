package org.generation.italy.legion.restControllers;

import org.generation.italy.legion.dtos.CourseEditionDto;
import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.entities.CourseEdition;
import org.generation.italy.legion.model.services.abstractions.AbstractCourseEditionDidacticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/courseEdition")
public class ApiCourseEditionController {
    public AbstractCourseEditionDidacticService service;
    @Autowired
    public ApiCourseEditionController(AbstractCourseEditionDidacticService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseEditionDto> findById(@PathVariable long id){
        try{
            Optional<CourseEdition> cE = service.findById(id);
            if(cE.isPresent()){
                return ResponseEntity.ok().body(CourseEditionDto.fromEntity(cE.get()));
            }
            return ResponseEntity.notFound().build();
        }catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    public ResponseEntity<Iterable<CourseEditionDto>> findAll(){
        try {
            Iterable<CourseEdition> itCe = service.findAll();
            return ResponseEntity.ok().body(CourseEditionDto.fromEntityIterable(itCe));
        }catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
