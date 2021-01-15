package com.lanc.planit.service;

import com.lanc.planit.model.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanService {
    public Plan add(Plan p);
    public void delete(Integer id);
    public Optional<Plan> find(Integer id);
    public Iterable<Plan> findAll();
}
