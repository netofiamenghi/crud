package com.example.crud.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.crud.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}