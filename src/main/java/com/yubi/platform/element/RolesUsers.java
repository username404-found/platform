// The entity class is needed in spring boot to define the table in the database.

package com.yubi.platform.element;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// The @Entity annotation is used to define the entity class.
// The @Table annotation is used to define the table in the database.
@Entity
@Table(name = "roles_users")
public class RolesUsers {

    // The @Id annotation is used to define the primary key.
    // The @GeneratedValue annotation is used to generate the value of the primary key.
    // The strategy attribute is used to define the strategy for generating the primary key.
    // The GenerationType.IDENTITY is used to generate the primary key using the auto-increment strategy.
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    
    // The @Column annotation is used to define the column in the table.
    @Column(name="id")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Roles role;

    // The default constructor is needed by the spring boot.
    public RolesUsers() {
    }

    // The parameterized constructor is used to initialize the instance variables.
    public RolesUsers(long id, User user, Roles role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    // The getter and setter methods are used to get and set the values of the instance variables.
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Roles getRole() {
        return role;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
    
}
