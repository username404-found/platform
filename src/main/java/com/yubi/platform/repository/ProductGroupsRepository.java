// The repository class is used to perform CRUD(create, read, update, delete) operations on the database.
// we can write our own queries or we can use the predefined queries.

package com.yubi.platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yubi.platform.element.ProductGroups;

// The @Repository annotation is used to define the repository class.
// we don't need to annotation @Repository here, because we are using JpaRepository which is already annotated with @Repository
public interface ProductGroupsRepository extends JpaRepository<ProductGroups, Long> {

    // The @Query annotation is used to write the custom queries.
    // The nativeQuery=true attribute is used to define the query in native SQL format.
    @Query(value = "SELECT id, name, product_id FROM product_groups",
            nativeQuery=true
    )
    public List<Map<String, Object>> findAllProductGroup();

    @Query(value = "insert into product_groups (id, name, product_id) values(?1, ?2, ?3) returning id, name, product_id",
            nativeQuery=true
    )
    public Map<String, Object> add(long id, String name, Long productId);

}