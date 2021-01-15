package com.lanc.planit.dao;

import com.lanc.planit.model.Plan;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepo extends CrudRepository<Plan, Integer> {
}
