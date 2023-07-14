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
@Table(name = "product_group_resources")
public class ProductGroupResources {

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
    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false)
    private Resources resource;

    @ManyToOne
    @JoinColumn(name = "product_group_id", referencedColumnName = "id", nullable = false)
    private ProductGroups product_group;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Products product;

    // The default constructor is needed by the spring boot.
    public ProductGroupResources() {
    }

    // The parameterized constructor is used to initialize the instance variables.
    public ProductGroupResources(long id, Products product, Resources resource, ProductGroups product_group) {
        this.id = id;
        this.product = product;
        this.product_group = product_group;
        this.resource = resource;
    }

    // The getter and setter methods are used to get and set the values of the instance variables.
    public long getId() {
        return this.id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public Products getProduct() {
        return this.product;
    }

    public void setProduct(Products value) {
        this.product = value;
    }

    public ProductGroups getProduct_group() {
        return this.product_group;
    }

    public void setProduct_group(ProductGroups value) {
        this.product_group = value;
    }

    public Resources getResource() {
        return this.resource;
    }

    public void setResource(Resources value) {
        this.resource = value;
    }
    
}