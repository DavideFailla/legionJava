package org.generation.italy.legion.restControllers;

import org.generation.italy.legion.dtos.SimpleTeacherDto;
import org.generation.italy.legion.dtos.TeacherDto;
import org.generation.italy.legion.model.data.abstractions.GenericRepository;
import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.legion.model.entities.Level;
import org.generation.italy.legion.model.entities.Teacher;
import org.generation.italy.legion.model.services.abstractions.AbstractCrudDidacticService;
import org.generation.italy.legion.model.services.abstractions.AbstractDidacticService;
import org.generation.italy.legion.model.services.abstractions.GenericsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/teachers") //davanti al prefisso dei metodi avremo "api"
public class ApiTeacherController {
    private AbstractDidacticService service;

    private GenericsService<Teacher> crudService;

    @Autowired
    public ApiTeacherController(AbstractDidacticService service,
                                GenericRepository<Teacher> teacherRepo){
        this.service = service;

        this.crudService = new GenericsService<>(teacherRepo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> findById(@PathVariable long id){
        try {
            Optional<Teacher> teacherOp = crudService.findById(id);
            if(teacherOp.isPresent()){
                return ResponseEntity.ok().body(TeacherDto.fromEntity(teacherOp.get()));
            }
            return ResponseEntity.notFound().build();
        } catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    public ResponseEntity<Iterable<SimpleTeacherDto>> findWithSkillAndLevel(@RequestParam(required = false) Long skillId,
                                                                            @RequestParam(required = false) Level level){
        try {
            Iterable<Teacher> teacherIt = service.findWithSkillAndLevel(skillId, level);
            return ResponseEntity.ok().body(SimpleTeacherDto.fromEntityIterable(teacherIt, skillId));
        } catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping()
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherDto teacherDto){
        Teacher te = teacherDto.toEntity();
        try {
            var teacherResult = this.crudService.create(te);
            TeacherDto dtoResult = TeacherDto.fromEntity(teacherResult);
            return ResponseEntity.created(URI.create("/api/teachers/" + teacherResult.getId())).body(dtoResult);
        } catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody TeacherDto teacherDto, @PathVariable long id){
        if(teacherDto.getId() != id){
            return ResponseEntity.badRequest().build();
        }
        Teacher te = teacherDto.toEntity();
        try {
            this.crudService.update(te);
            return ResponseEntity.noContent().build();
        } catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        try {
            this.crudService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (DataException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
