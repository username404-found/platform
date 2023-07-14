// The repository class is used to perform CRUD(create, read, update, delete) operations on the database.
// we can write our own queries or we can use the predefined queries.

package com.yubi.platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yubi.platform.element.Resources;

// The @Repository annotation is used to define the repository class.
// we don't need to annotation @Repository here, because we are using JpaRepository which is already annotated with @Repository
public interface ResourcesRepository extends JpaRepository<Resources, Long> {

    // custom query for selecting all the resources.
    // The @Query annotation is used to write the custom queries.
    // The nativeQuery=true attribute is used to define the query in native SQL format.
    @Query(value = "SELECT id, name, product_id FROM resources where product_id = ?1",
            nativeQuery=true
    )
    List<Map<String, Object>> findAllResourcesByProduct(Long product_id);

    @Query(value = "SELECT name FROM resources WHERE id = ?1",
            nativeQuery=true
    )
    String findResourceNameByResourceId(Long resource_id);

    @Query(value = "insert into resources (id, name, product_id) values(?1, ?2, ?3) returning id, name, product_id",
            nativeQuery=true
    )
    Map<String, Object> add(long id, String name, Long productId);

    @Query(value = "SELECT id, name, product_id FROM resources WHERE id = ?1",
            nativeQuery=true
    )
    Map<String, Object> findResourceById(Long resource_id);
    
}