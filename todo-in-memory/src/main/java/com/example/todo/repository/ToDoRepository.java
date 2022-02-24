package com.example.todo.repository;

import com.example.todo.domain.ToDo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

    private Map<String, ToDo> toDos = new HashMap<>();

    @Override
    public ToDo save(ToDo domain) {
        ToDo result = toDos.get(domain.getId());
        if (result != null) {
            result.setUpdated(LocalDateTime.now());
            result.setDescription(domain.getDescription());
            result.setCompleted(domain.isCompleted());
            domain = result;
        }
        toDos.put(domain.getId(), domain);
        return toDos.get(domain.getId());
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> domains) {
        domains.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(ToDo domain) {
        toDos.remove(domain.getId());
    }

    @Override
    public ToDo findById(String id) {
        return toDos.get(id);
    }

    @Override
    public Iterable<ToDo> findAll() {
        return toDos.values().stream()
                .sorted(Comparator.comparing(ToDo::getCreated))
                .collect(Collectors.toList());
    }

}
