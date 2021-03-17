package com.lanc.planit.dao;
/**
 * @Author Luigi Lin
 *
 */
import com.lanc.planit.model.Plan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlanRepo extends CrudRepository<Plan, Integer> {

    @Query(value = "select * from plan where user_id = ?1", nativeQuery = true)
    Iterable<Plan> findAllByUserId(long id);
}
