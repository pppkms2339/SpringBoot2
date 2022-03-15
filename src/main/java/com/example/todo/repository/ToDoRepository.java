package com.example.todo.repository;

import com.example.todo.domain.ToDo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

    private static final String SQL_INSERT = "insert into todo (id," +
            " description, created, updated, completed) values (" +
            ":id, :description, :created, :updated, :completed)";

    private static final String SQL_UPDATE = "update todo set " +
            "description = :description, created = :created, " +
            "updated = :updated, completed = :completed where id = :id";

    private static final String SQL_DELETE = "delete from todo where id = :id";

    private static final String SQL_QUERY_FIND_ALL = "select id, " +
            "description, created, updated, completed from todo";

    private static final String SQL_QUERY_FIND_BY_ID = SQL_QUERY_FIND_ALL +
            " where id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ToDoRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ToDo save(ToDo domain) {
        ToDo result = findById(domain.getId());
        if (result != null) {
            result.setDescription(domain.getDescription());
            result.setCompleted(domain.isCompleted());
            result.setUpdated(LocalDateTime.now());
            return upsert(result, SQL_UPDATE);
        }
        return upsert(domain, SQL_INSERT);
    }

    private ToDo upsert(ToDo toDo, String sql) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("id", toDo.getId());
        namedParameters.put("description", toDo.getDescription());
        namedParameters.put("created", toDo.getCreated());
        namedParameters.put("updated", toDo.getUpdated());
        namedParameters.put("completed", toDo.isCompleted());
        this.jdbcTemplate.update(sql, namedParameters);
        return findById(toDo.getId());
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> domains) {
        domains.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(ToDo domain) {
        Map<String, Object> namedParameters =
                Collections.singletonMap("id", domain.getId());
        this.jdbcTemplate.update(SQL_DELETE, namedParameters);
    }

    @Override
    public ToDo findById(String id) {
        try {
            Map<String, Object> namedParameters =
                    Collections.singletonMap("id", id);
            return this.jdbcTemplate.queryForObject(SQL_QUERY_FIND_BY_ID,
                    namedParameters, toDoRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Iterable<ToDo> findAll() {
        return this.jdbcTemplate.query(SQL_QUERY_FIND_ALL, toDoRowMapper);
    }

    private RowMapper<ToDo> toDoRowMapper = (ResultSet rs, int rowNum) -> {
        ToDo toDo = new ToDo();
        toDo.setId(rs.getString("id"));
        toDo.setDescription(rs.getString("description"));
        toDo.setCreated(rs.getTimestamp("created").toLocalDateTime());
        toDo.setUpdated(rs.getTimestamp("updated").toLocalDateTime());
        toDo.setCompleted(rs.getBoolean("completed"));
        return toDo;
    };

}