// The repository class is used to perform CRUD(create, read, update, delete) operations on the database.
// we can write our own queries or we can use the predefined queries.

package com.yubi.platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.platform.element.Entities;

// The @Repository annotation is used to define the repository class.
// we don't need to annotation @Repository here, because we are using JpaRepository which is already annotated with @Repository
@Transactional
public interface EntitiesRepository extends JpaRepository<Entities, Long> {

    // The @Query annotation is used to write the custom queries.
    // The nativeQuery=true attribute is used to define the query in native SQL format.
    @Query(value = "SELECT id, company_name, company_id, parent_id FROM entities",
            nativeQuery=true
    )
    public List<Map<String, Object>> findAllEntities();

    // Predefined query for selecting all the entities.
    public List<Entities> findAll();

    @Query(value = "SELECT * FROM entities WHERE parent_id = ?1",
            nativeQuery=true
    )
    public List<Entities> findSubEntities(long entityId);

    @Query(value = "insert into entities (id, company_name, company_id, parent_id) values(?1, ?2, ?3, ?4) returning id, company_name, company_id, parent_id",
            nativeQuery=true
    )
    public Map<String, Object> add(Long id, String name, String code, Long parentId);

    @Query(value = "select * from entities where id = ?1",
            nativeQuery=true)
    public Map<String, Object> findEntity(long entityId);
    
}
