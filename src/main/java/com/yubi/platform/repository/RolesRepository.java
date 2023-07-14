// The repository class is used to perform CRUD(create, read, update, delete) operations on the database.
// we can write our own queries or we can use the predefined queries.

package com.yubi.platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yubi.platform.element.Roles;

// The @Repository annotation is used to define the repository class.
// we don't need to annotation @Repository here, because we are using JpaRepository which is already annotated with @Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    // Predefined query for selecting all the roles.
    public List<Roles> findAll();

    // The @Query annotation is used to write the custom queries.
    // The nativeQuery=true attribute is used to define the query in native SQL format.
    @Query(value = "SELECT * FROM roles where product_id = ?1 and entity_id = ?2 and group_id = ?3 and name = ?4",
            nativeQuery=true
    )
    List<Roles> findRoleByProductIdAndEntityIdAndGroupIdAndName(long productId, Long entityId, Long groupId, String name);

    // custom query for selecting all the roles.
    @Query(value = "SELECT * FROM roles",
            nativeQuery=true
    )
    public List<Map<String, Object>> findAllRoles();

    @Query(value = "SELECT privileges FROM roles where id = ?1",
            nativeQuery=true
    )
    public List<List<Long>> findByRoleId(Long role_id);

    @Query(value = "select * from roles where entity_id = ?1 and group_id = ?2 and product_id = ?3",
            nativeQuery=true)
    public List<Map<String, Object>> findAllRolesByEntityAndGroupAndProduct(long entityId, long groupId, long productId);
    
}
