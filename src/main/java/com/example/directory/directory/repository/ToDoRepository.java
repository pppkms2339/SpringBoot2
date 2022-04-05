package com.example.directory.directory.repository;

import com.example.directory.directory.domain.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, String> {
}
