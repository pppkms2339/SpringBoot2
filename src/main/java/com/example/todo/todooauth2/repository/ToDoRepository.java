package com.example.todo.todooauth2.repository;

import com.example.todo.todooauth2.domain.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, String> {
}