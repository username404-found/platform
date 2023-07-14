// The repository class is used to perform CRUD(create, read, update, delete) operations on the database.
// we can write our own queries or we can use the predefined queries.

package com.yubi.platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yubi.platform.element.Groups;

// The @Repository annotation is used to define the repository class.
// we don't need to annotation @Repository here, because we are using JpaRepository which is already annotated with @Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {

    // Predefined query for selecting all the groups.
    public List<Groups> findAll();

    // The @Query annotation is used to write the custom queries.
    // The nativeQuery=true attribute is used to define the query in native SQL format.
    @Query(value = "SELECT * FROM groups where product_id = ?1 and entity_id = ?2 and name = ?3",
            nativeQuery=true
    )
    List<Groups> findGroupByProductIdAndEntityIdAndName(Long productId, Long entityId, String name);

    // custom query for selecting all the groups.
    @Query(value = "SELECT id, name, product_id, entity_id FROM groups where entity_id = ?1",
            nativeQuery=true
    )
    public List<Map<String, Object>> findAllGroups(Long entity_id);

    @Query(value = "insert into groups (id, name, product_id, entity_id) values(?1, ?2, ?3, ?4) returning id, name, product_id, entity_id",
            nativeQuery=true
    )
    public Map<String, Object> add(Long id, String name, Long productId, Long entityId);

	@Query(value = "select * from groups where entity_id = ?1 and product_id = ?2",
			nativeQuery=true)
    public List<Map<String, Object>> findAllGroupsByEntityAndProduct(long entityId, long productId);
    
}