// The entity class is needed in spring boot to define the table in the database.

package com.yubi.platform.element;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// The @Entity annotation is used to define the entity class.
// The @Table annotation is used to define the table in the database.
@Entity
@Table(name = "users")
public class User {
    
    // The @Id annotation is used to define the primary key.
    // The @GeneratedValue annotation is used to generate the value of the primary key.
    // The strategy attribute is used to define the strategy for generating the primary key.
    // The GenerationType.IDENTITY is used to generate the primary key using the auto-increment strategy.
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    
    // The @Column annotation is used to define the column in the table.
    @Column(name="id")
    private Long id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    // The default constructor is needed by the spring boot.
    public User() {
    }

    // The parameterized constructor is used to initialize the instance variables.
    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // The getter and setter methods are used to get and set the values of the instance variables.
    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

}