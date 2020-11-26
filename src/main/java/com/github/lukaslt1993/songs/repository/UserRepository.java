package com.github.lukaslt1993.songs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.lukaslt1993.songs.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findFirstByUserName(String name);

}
