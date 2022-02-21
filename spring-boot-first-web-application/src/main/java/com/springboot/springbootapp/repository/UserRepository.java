package com.springboot.springbootapp.repository;

import com.springboot.springbootapp.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmailId(String emailId);

    @Query("SELECT count(emailId) FROM users WHERE emailId=:emailId")
    int isEmailPresent(@Param("emailId") String emailId);
}