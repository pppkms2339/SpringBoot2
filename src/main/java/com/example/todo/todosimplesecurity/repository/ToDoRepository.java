package com.example.todo.todosimplesecurity.repository;

import com.example.todo.todosimplesecurity.domain.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, String> {
}
