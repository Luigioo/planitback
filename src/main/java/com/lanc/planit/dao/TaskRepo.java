package com.lanc.planit.dao;

import com.lanc.planit.model.Plan;
import com.lanc.planit.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepo extends CrudRepository<Task, Long> {

    @Query(value = "select * from task where user_id = ?1", nativeQuery = true)
    Iterable<Task> findAllByUserId(long id);

}
