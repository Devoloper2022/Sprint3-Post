package com.sonik.practicum.repository;

import com.sonik.practicum.models.Entity.User;
import com.sonik.practicum.repository.Interface.UserRepo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class H2UserRepo implements UserRepo {

    private final JdbcTemplate jdbcTemplate;

    public H2UserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(
                "select id, first_name, last_name, age, active from users",
                (rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getBoolean("active")
                ));
    }



    @Override
    public void save(User user) {
        // Формируем insert-запрос с параметрами
        jdbcTemplate.update("insert into users(first_name, last_name, age, active) values(?, ?, ?, ?)",
                user.getFirstName(), user.getLastName(), user.getAge(), user.isActive());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from users where id = ?", id);
    }
}
