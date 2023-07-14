// The repository class is used to perform CRUD(create, read, update, delete) operations on the database.
// we can write our own queries or we can use the predefined queries.

package com.yubi.platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yubi.platform.element.RolesUsers;

import jakarta.transaction.Transactional;

// The @Repository annotation is used to define the repository class.
// we don't need to annotation @Repository here, because we are using JpaRepository which is already annotated with @Repository
@Transactional
public interface RolesUsersRepository extends JpaRepository<RolesUsers, Long> {

    // custom query for selecting role_id from roles_users for an user.
    // The @Query annotation is used to write the custom queries.
    // The nativeQuery=true attribute is used to define the query in native SQL format.
    @Query(value = "SELECT role_id FROM roles_users WHERE user_id = ?1", nativeQuery = true)
    List<Long> findByUserId(Long user_id);

    @Query(value = "SELECT id, role_id, user_id FROM roles_users", nativeQuery = true)
    List<Map<String, Object>> findAllRolesUsers();

    @Modifying
    @Query(value = "delete from roles_users where user_id = ?1", nativeQuery = true)
    void deleteByUserId(Long user_id);

    @Query(value = "SELECT * FROM roles_users WHERE role_id = ?1", nativeQuery = true)
    List<Map<String, Object>> findByRoleId(long roleId);
    
}
