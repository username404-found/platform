// The repository class is used to perform CRUD(create, read, update, delete) operations on the database.
// we can write our own queries or we can use the predefined queries.

package com.yubi.platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yubi.platform.element.ProductGroupResources;

// The @Repository annotation is used to define the repository class.
// we don't need to annotation @Repository here, because we are using JpaRepository which is already annotated with @Repository
public interface ProductGroupResourcesRepository extends JpaRepository<ProductGroupResources, Long> {

    // custom query for selecting all the product group resources.
    // The @Query annotation is used to write the custom queries.
    // The nativeQuery=true attribute is used to define the query in native SQL format.
    @Query(value = "SELECT id, product_id, product_group_id, resource_id FROM product_group_resources",
            nativeQuery=true
    )
    List<Map<String, Object>> findAllProductGroupResources();

    @Query(value = "insert into product_group_resources (id, product_id, product_group_id, resource_id) values(?1, ?2, ?3, ?4) returning id, product_id, product_group_id, resource_id",
            nativeQuery=true
    )
    Map<String, Object> add(long id, Long product_id, Long product_group_id, Long resource_id);

    
}
