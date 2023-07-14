// The entity class is needed in spring boot to define the table in the database.

package com.yubi.platform.element;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "roles")
public class Roles {

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

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private Groups group;

    @ManyToOne
    @JoinColumn(name = "entity_id", referencedColumnName = "id", nullable = false)
    private Entities entity;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Products product;

    @ManyToOne
    @JoinColumn(name = "role_template_id", referencedColumnName = "id", nullable = false)
    private RoleTemplates role_template;

    @Column(name = "privileges")
    private List<Long> privileges = new ArrayList<Long>();

    // The default constructor is needed by the spring boot.
    public Roles() {
    }

    // The parameterized constructor is used to initialize the instance variables.
    public Roles(String name, Groups group, Entities entity, Products product, RoleTemplates role_template, List<Long> privileges) {
        this.name = name;
        this.privileges = privileges;
        this.group = group;
        this.entity = entity;
        this.product = product;
        this.role_template = role_template;
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

    public List<Long> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(List<Long> value) {
        this.privileges = value;
    }

    public Groups getGroup() {
        return this.group;
    }

    public void setGroup(Groups value) {
        this.group = value;
    }

    public Entities getEntity() {
        return this.entity;
    }

    public void setEntity(Entities value) {
        this.entity = value;
    }

    public Products getProduct() {
        return this.product;
    }

    public void setProduct(Products value) {
        this.product = value;
    }

    public RoleTemplates getRole_template() {
        return this.role_template;
    }

    public void setRole_template(RoleTemplates value) {
        this.role_template = value;
    }

}