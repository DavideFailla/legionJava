package org.generation.italy.legion.restControllers;

import org.generation.italy.legion.model.data.abstractions.GenericRepository;
import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.services.abstractions.GenericsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public abstract class ApiGenericController<T> {
    private GenericsService<T> service;

    public ApiGenericController(GenericRepository<T> repository) {
        this.service = new GenericsService<T>(repository);
    }

//    @GetMapping()
//    public ResponseEntity<Iterable<T>> findAll()  {
//        return ResponseEntity.ok().body(service.findAll());
//    }
}
