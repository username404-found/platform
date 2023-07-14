// The repository class is used to perform CRUD(create, read, update, delete) operations on the database.
// we can write our own queries or we can use the predefined queries.

package com.yubi.platform.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yubi.platform.element.EntityUsers;

// The @Repository annotation is used to define the repository class.
// we don't need to annotation @Repository here, because we are using JpaRepository which is already annotated with @Repository
public interface EntityUsersRepository extends JpaRepository<EntityUsers, Long> {
    
    // custom query for selecting all the groups.
    // The @Query annotation is used to write the custom queries.
    // The nativeQuery=true attribute is used to define the query in native SQL format.
    @Query(value = "SELECT * FROM entity_users",
            nativeQuery=true
    )
    public List<EntityUsers> findAllGroups();

    @Query(value = "insert into entity_users (id, entity_id, user_id, groups, credit_roles, credpool_roles, loan_roles, user_platform_roles) values(?1, ?2, ?3, cast(?4 as hstore), cast(?5 as hstore), cast(?6 as hstore), cast(?7 as hstore), cast(?8 as hstore)) returning id, entity_id, user_id, groups, credit_roles, credpool_roles, loan_roles, user_platform_roles",
            nativeQuery=true
    )
    public Map<String, Object> add(Long id, Long entityId, Long userId, String groups, String creditRoles, String credpoolRoles, String loanRoles, String userPlatformRoles);

    @Query(value = "SELECT * FROM entity_users",
            nativeQuery=true
    )
    public List<Map<String, Object>> findAllEntityUser();

    @Query(value = "SELECT user_id FROM entity_users where entity_id = ?1",
            nativeQuery=true
    )
    public List<Long> findByEntityId(Long entity_id);

    @Query(value = "SELECT * FROM entity_users where user_id = ?1",
            nativeQuery=true
    )
    public Map<String, Object> findByUserId(Long entity_id);

}
