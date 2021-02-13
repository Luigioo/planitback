package com.lanc.planit.dao;

import com.lanc.planit.model.MyUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MyUserRepo extends CrudRepository<MyUser, Long> {

    Optional<MyUser> findByName(String name);

}
