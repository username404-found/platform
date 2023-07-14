// The repository class is used to perform CRUD(create, read, update, delete) operations on the database.
// we can write our own queries or we can use the predefined queries.

package com.yubi.platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yubi.platform.element.User;

// The @Repository annotation is used to define the repository class.
// we don't need to annotation @Repository here, because we are using JpaRepository which is already annotated with @Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    // The @Query annotation is used to write the custom queries.
    // The nativeQuery=true attribute is used to define the query in native SQL format.
    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    public User findByEmail(String email);

    @Query(value = "SELECT id, name, email FROM users where id = ?1", nativeQuery = true)
    public Map<String, Object> findByUserId(Long user_id);

    @Query(value = "SELECT id, name, email FROM users", nativeQuery = true)
    public List<Map<String, Object>> findAllUser();

    @Query(value = "insert into users (id, name, email) values(?1, ?2, ?3) returning id, name, email", nativeQuery = true)
    public Map<String, Object> add(Long id, String name, String email);

    @Query(value = "SELECT id, name, email FROM users WHERE id = ?1", nativeQuery = true)
    public List<Map<String, Object>> findByEntityId(Long entity_id);

}