package com.lanc.planit.service;

import com.lanc.planit.dao.TaskRepo;
import com.lanc.planit.model.Plan;
import com.lanc.planit.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepo repo;

    public Task add(Task p) {
        return repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Iterable<Task> findAllByUserId(long id){
        return repo.findAllByUserId(id);
    }

    public Optional<Task> find(Long id){ return repo.findById(id); }

}
