package com.lanc.planit.service;

import com.lanc.planit.dao.PlanRepo;
import com.lanc.planit.model.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService{

    @Autowired
    private PlanRepo repo;

    public Plan add(Plan p) {
        return repo.save(p);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public Iterable<Plan> findAllByUserId(long id){
        return repo.findAllByUserId(id);
    }

    public Optional<Plan> find(Integer id) {
        return repo.findById(id);
    }

    public Iterable<Plan> findAll() {
        return repo.findAll();
    }
}
