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
@Table(name = "entities")
public class Entities {
    
    // The @Id annotation is used to define the primary key.
    // The @GeneratedValue annotation is used to generate the value of the primary key.
    // The strategy attribute is used to define the strategy for generating the primary key.
    // The GenerationType.IDENTITY is used to generate the primary key using the auto-increment strategy.
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    
    // The @Column annotation is used to define the column in the table.
    @Column(name="id")
    private long id;
    
    @Column(name = "company_name")
    private String company_name;

    @Column(name = "company_id")
    private String company_id;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = true)
    private Entities parent;

    // The default constructor is needed by the spring boot.
    public Entities() {
    }

    // The parameterized constructor is used to initialize the instance variables.
    public Entities(long id, String name, String code, Entities parent) {
        this.id = id;
        this.company_name = name;
        this.company_id = code;
        this.parent = parent;
    }

    // The getter and setter methods are used to get and set the values of the instance variables.
    public long getId() {
        return this.id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public String getCompany_name() {
        return this.company_name;
    }

    public void setCompany_name(String value) {
        this.company_name = value;
    }

    public String getCompany_id() {
        return this.company_id;
    }

    public void setCompany_id(String value) {
        this.company_id = value;
    }

    public Entities getParent() {
        return this.parent;
    }

    public void setParent(Entities value) {
        this.parent = value;
    }
    
}