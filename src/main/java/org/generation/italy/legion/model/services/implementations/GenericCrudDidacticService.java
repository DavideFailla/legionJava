package org.generation.italy.legion.model.services.implementations;

import org.generation.italy.legion.model.data.abstractions.AbstractCrudRepository;
import org.generation.italy.legion.model.data.exceptions.DataException;
import org.generation.italy.legion.model.data.exceptions.EntityNotFoundException;
import org.generation.italy.legion.model.data.implementations.GenericCrudRepository;
import org.generation.italy.legion.model.services.abstractions.AbstractCrudDidacticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class GenericCrudDidacticService<T> implements AbstractCrudDidacticService<T> {

    private AbstractCrudRepository<T> repo;

    @Autowired
    public GenericCrudDidacticService(AbstractCrudRepository<T> repo){
        this.repo = repo;
    }



    @Override
    public List<T> findAll() throws DataException {
        return repo.findAll();
    }

    @Override
    public Optional<T> findById(long id) throws DataException {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public T create(T entity) throws DataException {
        return repo.create(entity);
    }

    @Override
    public void update(T entity) throws EntityNotFoundException, DataException {
        repo.update(entity);
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
        repo.deleteById(id);
    }
}