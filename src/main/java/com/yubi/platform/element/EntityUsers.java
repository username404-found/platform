// The entity class is needed in spring boot to define the table in the database.

package com.yubi.platform.element;

import java.util.Map;

import org.hibernate.annotations.Type;

// import com.yubi.platform.custom.HstoreConverter;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLHStoreType;
// import jakarta.persistence.Convert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// The @Entity annotation is used to define the entity class.
// The @Table annotation is used to define the table in the database.
@Entity
@Table(name = "entity_users")
public class EntityUsers {

    // The @Id annotation is used to define the primary key.
    // The @GeneratedValue annotation is used to generate the value of the primary key.
    // The strategy attribute is used to define the strategy for generating the primary key.
    // The GenerationType.IDENTITY is used to generate the primary key using the auto-increment strategy.
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    
    // The @Column annotation is used to define the column in the table.
    @Column(name="id")
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "entity_id", referencedColumnName = "id", nullable = false)
    private Entities entity;

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "groups", columnDefinition = "hstore")
    private Map<String, String> groups;

   @Type(PostgreSQLHStoreType.class)
    @Column(name = "credit_roles", columnDefinition = "hstore")
    private Map<String, String> credit_roles;

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "credpool_roles", columnDefinition = "hstore")
    private Map<String, String> credpool_roles;

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "loan_roles", columnDefinition = "hstore")
    private Map<String, String> loan_roles;

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "user_platform_roles", columnDefinition = "hstore")
    private Map<String, String> user_platform_roles;

    // The default constructor is needed by the spring boot.
    public EntityUsers() {
    }

    // The parameterized constructor is used to initialize the instance variables.
    public EntityUsers(Long id, Entities entity, User user, Map<String, String> groups, Map<String, String> credit_roles, Map<String, String> credpool_roles, Map<String, String> loan_roles, Map<String, String> user_platform_roles) {
        this.id = id;
        this.user = user;
        this.entity = entity;
        this.groups = groups;
        this.credit_roles = credit_roles;
        this.credpool_roles = credpool_roles;
        this.loan_roles = loan_roles;
        this.user_platform_roles = user_platform_roles;
    }

    // The getter and setter methods are used to get and set the values of the instance variables.
    public long getId() {
        return this.id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User value) {
        this.user = value;
    }

    public Entities getEntity() {
        return this.entity;
    }

    public void setEntity(Entities value) {
        this.entity = value;
    }

    public Map<String, String> getGroups() {
        return this.groups;
    }

    public void setGroups(Map<String, String> value) {
        this.groups = value;
    }

    public Map<String, String> getCredit_roles() {
        return this.credit_roles;
    }

    public void setCredit_roles(Map<String, String> value) {
        this.credit_roles = value;
    }

    public Map<String, String> getCredpool_roles() {
        return this.credpool_roles;
    }

    public void setCredpool_roles(Map<String, String> value) {
        this.credpool_roles = value;
    }

    public Map<String, String> getLoan_roles() {
        return this.loan_roles;
    }

    public void setLoan_roles(Map<String, String> value) {
        this.loan_roles = value;
    }

    public Map<String, String> getUser_platform_roles() {
        return this.user_platform_roles;
    }

    public void setUser_platform_roles(Map<String, String> value) {
        this.user_platform_roles = value;
    }
    
}
