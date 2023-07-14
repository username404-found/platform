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
@Table(name = "products")
public class Products {
    
    // The @Id annotation is used to define the primary key.
    // The @GeneratedValue annotation is used to generate the value of the primary key.
    // The strategy attribute is used to define the strategy for generating the primary key.
    // The GenerationType.IDENTITY is used to generate the primary key using the auto-increment strategy.
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    
    // The @Column annotation is used to define the column in the table.
    @Column(name="id")
    private long id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    // The default constructor is needed by the spring boot.
    public Products() {
    }

    // The parameterized constructor is used to initialize the instance variables.
    public Products(long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    // The getter and setter methods are used to get and set the values of the instance variables.
    public long getId() {
        return this.id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

}
