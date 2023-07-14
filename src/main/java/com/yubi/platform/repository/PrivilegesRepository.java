// The repository class is used to perform CRUD(create, read, update, delete) operations on the database.
// we can write our own queries or we can use the predefined queries.

package com.yubi.platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yubi.platform.element.Privileges;

// The @Repository annotation is used to define the repository class.
// we don't need to annotation @Repository here, because we are using JpaRepository which is already annotated with @Repository
public interface PrivilegesRepository extends JpaRepository<Privileges, Long> {

    // custom query for selecting all the privileges.
    // The @Query annotation is used to write the custom queries.
    // The nativeQuery=true attribute is used to define the query in native SQL format.
    @Query(value = "SELECT id, name, product_id, resource_id FROM privileges where product_id = ?1 and resource_id = ?2",
            nativeQuery=true
    )
    List<Map<String, Object>> findAllPrivileges(Long product_id, Long Resource_id);

    @Query(value = "SELECT resource_id FROM privileges WHERE id = ?1",
            nativeQuery=true
    )
    Long findResourceIdByPrivilegeId(Long privileges_id);

    @Query(value = "SELECT name FROM privileges WHERE id = ?1",
            nativeQuery=true
    )
    String findPrivilegeNameByPrivilegeId(Long privilegeId);

    @Query(value = "insert into privileges (id, name, product_id, resource_id) values(?1, ?2, ?3, ?4) returning id, name, product_id, resource_id",
            nativeQuery=true
    )
    Map<String, Object> add(long id, String name, Long product_id, Long resource_id);

    
}
