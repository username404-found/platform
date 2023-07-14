// The repository class is used to perform CRUD(create, read, update, delete) operations on the database.
// we can write our own queries or we can use the predefined queries.

package com.yubi.platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yubi.platform.element.Products;

// The @Repository annotation is used to define the repository class.
// we don't need to annotation @Repository here, because we are using JpaRepository which is already annotated with @Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    // custom query for selecting all the products.
    // The @Query annotation is used to write the custom queries.
    // The nativeQuery=true attribute is used to define the query in native SQL format.
    @Query(value = "SELECT id, name, code FROM products",
            nativeQuery=true
    )
    List<Map<String, Object>> findAllProduct();

    @Query(value = "insert into products (id, name, code) values(?1, ?2, ?3) returning id, name, code",
            nativeQuery=true
    )
    Map<String, Object> add(long id, String name, String code);
  
}