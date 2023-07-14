// A controller class is used to define the endpoints for the REST API.

package com.yubi.platform.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yubi.platform.service.ProductService;

// The @RestController annotation is used to define the controller class.
@RestController
public class ProductController {
    
    // The @Autowired annotation is used to inject the dependency of the service class.
    @Autowired
    private ProductService productService;


    /*                                         -------------------------------------- Products -----------------------------------------                                      */


    // The @GetMapping annotation is used when the user wants to get some information.
    @GetMapping("/products")
    public List<Map<String, Object>> getAllProduct() {
        return productService.getAllProduct();
    }

    // The @PostMapping annotation is used when the user wants to post some information.
    // The @RequestBody annotation is used to get the data from the request body.
    @PostMapping("/addProduct")
    public Map<String, Object> addProduct(@RequestBody Map<String, Object> product){
        return productService.addProduct(product);
    }


    /*                                         -------------------------------------- Product Groups -----------------------------------------                                      */


    @GetMapping("/productGroups")
    public List<Map<String, Object>> getAllProductGroup() {
        return productService.getAllProductGroup();
    }


    @PostMapping("/addProductGroup")
    public Map<String, Object> addProductGroup(@RequestBody Map<String, Object> productGroup){
        System.out.println("productGroup is adding!!\n");
        return productService.addProductGroup(productGroup);
    }


    /*                                         -------------------------------------- Role Templates -----------------------------------------                                      */


    @GetMapping("/roleTemplates")
    public List<Map<String, Object>> getAllRoleTemplates() {
        return productService.getAllRoleTemplates();
    }

    @PostMapping("/addRoleTemplates")
    public Map<String, Object> addRoleTemplates(@RequestBody Map<String, Object> productGroupRoles){
        return productService.addRoleTemplates(productGroupRoles);
    }


    /*                                         -------------------------------------- Privileges -----------------------------------------                                      */


    @GetMapping("/product/{product_id}/resource/{resource_id}/privileges")
    public List<Map<String, Object>> getAllPrivilegesByProductAndResource(@PathVariable long product_id, @PathVariable long resource_id) {
        return productService.getAllPrivilegesByProductAndResource(product_id, resource_id);
    }

    @PostMapping("/product/{product_id}/resource/{resource_id}/addPrivileges")
    public Map<String, Object> addPrivilegesByProductAndResource(@PathVariable Long product_id, @PathVariable Long resource_id, @RequestBody Map<String, Object> privileges){
        return productService.addPrivilegesByProductAndResource(product_id, resource_id, privileges);
    }

    @GetMapping("/delete/privileges/{privilege_id}")
    public Map<String, Object> deletePrivilegeById(@PathVariable Long privilege_id){
        return productService.deletePrivilegeById(privilege_id);
    }


    /*                                         -------------------------------------- Resources -----------------------------------------                                      */


    @GetMapping("/product/{product_id}/resources")
    public List<Map<String, Object>> getAllResourcesByProduct(@PathVariable Long product_id) {
        return productService.getAllResourcesByProduct(product_id);
    }

    @GetMapping("/resources/{resource_id}")
    public Map<String, Object> getResourceById(@PathVariable Long resource_id) {
        return productService.getResourceById(resource_id);
    }

    @PostMapping("/product/{product_id}/addResources")
    public Map<String, Object> addResourcesByProduct(@PathVariable Long product_id, @RequestBody Map<String, Object> resources){
        return productService.addResourcesByProduct(product_id, resources);
    }

    @GetMapping("/delete/resources/{resource_id}")
    public Map<String, Object> deleteResourceById(@PathVariable Long resource_id){
        return productService.deleteResourceById(resource_id);
    }

    @PostMapping("/update/resources/{resource_id}")
    public Map<String, Object> updateResourceById(@PathVariable Long resource_id, @RequestBody Map<String, Object> resource){
        return productService.updateResourceById(resource_id, resource);
    }


    /*                                         -------------------------------------- Product Group Resources -----------------------------------------                                      */

    
    @GetMapping("/productGroupResources")
    public List<Map<String, Object>> getAllProductGroupResources() {
        return productService.getAllProductGroupResources();
    }

    @PostMapping("/addProductGroupResources")
    public Map<String, Object> addProductGroupResources(@RequestBody Map<String, Object> productGroupResources){
        return productService.addProductGroupResources(productGroupResources);
    }


    // This is a manual work to add some products, product_groups, role_templates, resources, privileges, product_group_resources and define some relations accordingly.
    // There are total 6 products. Four of them are independent (credit, credpool, loan, user_platform) and rest two comes under groups (product1, product2).
    // There are total 12 product_groups. Two product_groups (customer, investor) under each product.
    // There are total 24 role_templates. Two role_templates (admin, non-admin) under each product_group.
    // There are total 48 privileges. Two under each role_template. Every privilege is associated with a resource.
    // There are total 24 resources. Each resources contains two privileges and there are four resources under each product.
    // If any privilege associated with a resource is assigned to a role_template. then a record in the product_group_resources table is created with that resource and product_group to which that role_template belongs.
    @GetMapping("/")
    public void ManualWork(){

        // Add Products
        Map<String, Object> product1 = new HashMap<String, Object>();
        product1.put("name", "Product1");
        product1.put("code", "P1");
        productService.addProduct(product1);
        Map<String, Object> product2 = new HashMap<String, Object>();
        product2.put("name", "Product2");
        product2.put("code", "P2");
        productService.addProduct(product2);
        Map<String, Object> product3 = new HashMap<String, Object>();
        product3.put("name", "Credit");
        product3.put("code", "P3");
        productService.addProduct(product3);
        Map<String, Object> product4 = new HashMap<String, Object>();
        product4.put("name", "Credpool");
        product4.put("code", "P4");
        productService.addProduct(product4);
        Map<String, Object> product5 = new HashMap<String, Object>();
        product5.put("name", "Loan");
        product5.put("code", "P5");
        productService.addProduct(product5);
        Map<String, Object> product6 = new HashMap<String, Object>();
        product6.put("name", "User Platform");
        product6.put("code", "P6");
        productService.addProduct(product6);


        // Add Resources
        Map<String, Object> resource1 = new HashMap<String, Object>();
        resource1.put("name", "Resource1");
        productService.addResourcesByProduct(1L, resource1);
        Map<String, Object> resource2 = new HashMap<String, Object>();
        resource2.put("name", "Resource2");
        productService.addResourcesByProduct(1L, resource2);
        Map<String, Object> resource3 = new HashMap<String, Object>();
        resource3.put("name", "Resource3");
        productService.addResourcesByProduct(1L, resource3);
        Map<String, Object> resource4 = new HashMap<String, Object>();
        resource4.put("name", "Resource4");
        productService.addResourcesByProduct(1L, resource4);
        Map<String, Object> resource5 = new HashMap<String, Object>();
        resource5.put("name", "Resource5");
        productService.addResourcesByProduct(2L, resource5);
        Map<String, Object> resource6 = new HashMap<String, Object>();
        resource6.put("name", "Resource6");
        productService.addResourcesByProduct(2L, resource6);
        Map<String, Object> resource7 = new HashMap<String, Object>();
        resource7.put("name", "Resource7");
        productService.addResourcesByProduct(2L, resource7);
        Map<String, Object> resource8 = new HashMap<String, Object>();
        resource8.put("name", "Resource8");
        productService.addResourcesByProduct(2L, resource8);
        Map<String, Object> resource9 = new HashMap<String, Object>();
        resource9.put("name", "Resource9");
        productService.addResourcesByProduct(3L, resource9);
        Map<String, Object> resource10 = new HashMap<String, Object>();
        resource10.put("name", "Resource10");
        productService.addResourcesByProduct(3L, resource10);
        Map<String, Object> resource11 = new HashMap<String, Object>();
        resource11.put("name", "Resource11");
        productService.addResourcesByProduct(3L, resource11);
        Map<String, Object> resource12 = new HashMap<String, Object>();
        resource12.put("name", "Resource12");
        productService.addResourcesByProduct(3L, resource12);
        Map<String, Object> resource13 = new HashMap<String, Object>();
        resource13.put("name", "Resource13");
        productService.addResourcesByProduct(4L, resource13);
        Map<String, Object> resource14 = new HashMap<String, Object>();
        resource14.put("name", "Resource14");
        productService.addResourcesByProduct(4L, resource14);
        Map<String, Object> resource15 = new HashMap<String, Object>();
        resource15.put("name", "Resource15");
        productService.addResourcesByProduct(4L, resource15);
        Map<String, Object> resource16 = new HashMap<String, Object>();
        resource16.put("name", "Resource16");
        productService.addResourcesByProduct(4L, resource16);
        Map<String, Object> resource17 = new HashMap<String, Object>();
        resource17.put("name", "Resource17");
        productService.addResourcesByProduct(5L, resource17);
        Map<String, Object> resource18 = new HashMap<String, Object>();
        resource18.put("name", "Resource18");
        productService.addResourcesByProduct(5L, resource18);
        Map<String, Object> resource19 = new HashMap<String, Object>();
        resource19.put("name", "Resource19");
        productService.addResourcesByProduct(5L, resource19);
        Map<String, Object> resource20 = new HashMap<String, Object>();
        resource20.put("name", "Resource20");
        productService.addResourcesByProduct(5L, resource20);
        Map<String, Object> resource21 = new HashMap<String, Object>();
        resource21.put("name", "Resource21");
        productService.addResourcesByProduct(6L, resource21);
        Map<String, Object> resource22 = new HashMap<String, Object>();
        resource22.put("name", "Resource22");
        productService.addResourcesByProduct(6L, resource22);
        Map<String, Object> resource23 = new HashMap<String, Object>();
        resource23.put("name", "Resource23");
        productService.addResourcesByProduct(6L, resource23);
        Map<String, Object> resource24 = new HashMap<String, Object>();
        resource24.put("name", "Resource24");
        productService.addResourcesByProduct(6L, resource24);


        // Add Privileges
        Map<String, Object> privilege1 = new HashMap<String, Object>();
        privilege1.put("name", "Privilege1");
        productService.addPrivilegesByProductAndResource(1L, 1L, privilege1);
        Map<String, Object> privilege2 = new HashMap<String, Object>();
        privilege2.put("name", "Privilege2");
        productService.addPrivilegesByProductAndResource(1L, 1L, privilege2);
        Map<String, Object> privilege3 = new HashMap<String, Object>();
        privilege3.put("name", "Privilege3");
        productService.addPrivilegesByProductAndResource(1L, 2L, privilege3);
        Map<String, Object> privilege4 = new HashMap<String, Object>();
        privilege4.put("name", "Privilege4");
        productService.addPrivilegesByProductAndResource(1L, 2L, privilege4);
        Map<String, Object> privilege5 = new HashMap<String, Object>();
        privilege5.put("name", "Privilege5");
        productService.addPrivilegesByProductAndResource(1L, 3L, privilege5);
        Map<String, Object> privilege6 = new HashMap<String, Object>();
        privilege6.put("name", "Privilege6");
        productService.addPrivilegesByProductAndResource(1L, 3L, privilege6);
        Map<String, Object> privilege7 = new HashMap<String, Object>();
        privilege7.put("name", "Privilege7");
        productService.addPrivilegesByProductAndResource(1L, 4L, privilege7);
        Map<String, Object> privilege8 = new HashMap<String, Object>();
        privilege8.put("name", "Privilege8");
        productService.addPrivilegesByProductAndResource(1L, 4L, privilege8);
        Map<String, Object> privilege9 = new HashMap<String, Object>();
        privilege9.put("name", "Privilege9");
        productService.addPrivilegesByProductAndResource(2L, 5L, privilege9);
        Map<String, Object> privilege10 = new HashMap<String, Object>();
        privilege10.put("name", "Privilege10");
        productService.addPrivilegesByProductAndResource(2L, 5L, privilege10);
        Map<String, Object> privilege11 = new HashMap<String, Object>();
        privilege11.put("name", "Privilege11");
        productService.addPrivilegesByProductAndResource(2L, 6L, privilege11);
        Map<String, Object> privilege12 = new HashMap<String, Object>();
        privilege12.put("name", "Privilege12");
        productService.addPrivilegesByProductAndResource(2L, 6L, privilege12);
        Map<String, Object> privilege13 = new HashMap<String, Object>();
        privilege13.put("name", "Privilege13");
        productService.addPrivilegesByProductAndResource(2L, 7L, privilege13);
        Map<String, Object> privilege14 = new HashMap<String, Object>();
        privilege14.put("name", "Privilege14");
        productService.addPrivilegesByProductAndResource(2L, 7L, privilege14);
        Map<String, Object> privilege15 = new HashMap<String, Object>();
        privilege15.put("name", "Privilege15");
        productService.addPrivilegesByProductAndResource(2L, 8L, privilege15);
        Map<String, Object> privilege16 = new HashMap<String, Object>();
        privilege16.put("name", "Privilege16");
        productService.addPrivilegesByProductAndResource(2L, 8L, privilege16);
        Map<String, Object> privilege17 = new HashMap<String, Object>();
        privilege17.put("name", "Privilege17");
        productService.addPrivilegesByProductAndResource(3L, 9L, privilege17);
        Map<String, Object> privilege18 = new HashMap<String, Object>();
        privilege18.put("name", "Privilege18");
        productService.addPrivilegesByProductAndResource(3L, 9L, privilege18);
        Map<String, Object> privilege19 = new HashMap<String, Object>();
        privilege19.put("name", "Privilege19");
        productService.addPrivilegesByProductAndResource(3L, 10L, privilege19);
        Map<String, Object> privilege20 = new HashMap<String, Object>();
        privilege20.put("name", "Privilege20");
        productService.addPrivilegesByProductAndResource(3L, 10L, privilege20);
        Map<String, Object> privilege21 = new HashMap<String, Object>();
        privilege21.put("name", "Privilege21");
        productService.addPrivilegesByProductAndResource(3L, 11L, privilege21);
        Map<String, Object> privilege22 = new HashMap<String, Object>();
        privilege22.put("name", "Privilege22");
        productService.addPrivilegesByProductAndResource(3L, 11L, privilege22);
        Map<String, Object> privilege23 = new HashMap<String, Object>();
        privilege23.put("name", "Privilege23");
        productService.addPrivilegesByProductAndResource(3L, 12L, privilege23);
        Map<String, Object> privilege24 = new HashMap<String, Object>();
        privilege24.put("name", "Privilege24");
        productService.addPrivilegesByProductAndResource(3L, 12L, privilege24);
        Map<String, Object> privilege25 = new HashMap<String, Object>();
        privilege25.put("name", "Privilege25");
        productService.addPrivilegesByProductAndResource(4L, 13L, privilege25);
        Map<String, Object> privilege26 = new HashMap<String, Object>();
        privilege26.put("name", "Privilege26");
        productService.addPrivilegesByProductAndResource(4L, 13L, privilege26);
        Map<String, Object> privilege27 = new HashMap<String, Object>();
        privilege27.put("name", "Privilege27");
        productService.addPrivilegesByProductAndResource(4L, 14L, privilege27);
        Map<String, Object> privilege28 = new HashMap<String, Object>();
        privilege28.put("name", "Privilege28");
        productService.addPrivilegesByProductAndResource(4L, 14L, privilege28);
        Map<String, Object> privilege29 = new HashMap<String, Object>();
        privilege29.put("name", "Privilege29");
        productService.addPrivilegesByProductAndResource(4L, 15L, privilege29);
        Map<String, Object> privilege30 = new HashMap<String, Object>();
        privilege30.put("name", "Privilege30");
        productService.addPrivilegesByProductAndResource(4L, 15L, privilege30);
        Map<String, Object> privilege31 = new HashMap<String, Object>();
        privilege31.put("name", "Privilege31");
        productService.addPrivilegesByProductAndResource(4L, 16L, privilege31);
        Map<String, Object> privilege32 = new HashMap<String, Object>();
        privilege32.put("name", "Privilege32");
        productService.addPrivilegesByProductAndResource(4L, 16L, privilege32);
        Map<String, Object> privilege33 = new HashMap<String, Object>();
        privilege33.put("name", "Privilege33");
        productService.addPrivilegesByProductAndResource(5L, 17L, privilege33);
        Map<String, Object> privilege34 = new HashMap<String, Object>();
        privilege34.put("name", "Privilege34");
        productService.addPrivilegesByProductAndResource(5L, 17L, privilege34);
        Map<String, Object> privilege35 = new HashMap<String, Object>();
        privilege35.put("name", "Privilege35");
        productService.addPrivilegesByProductAndResource(5L, 18L, privilege35);
        Map<String, Object> privilege36 = new HashMap<String, Object>();
        privilege36.put("name", "Privilege36");
        productService.addPrivilegesByProductAndResource(5L, 18L, privilege36);
        Map<String, Object> privilege37 = new HashMap<String, Object>();
        privilege37.put("name", "Privilege37");
        productService.addPrivilegesByProductAndResource(5L, 19L, privilege37);
        Map<String, Object> privilege38 = new HashMap<String, Object>();
        privilege38.put("name", "Privilege38");
        productService.addPrivilegesByProductAndResource(5L, 19L, privilege38);
        Map<String, Object> privilege39 = new HashMap<String, Object>();
        privilege39.put("name", "Privilege39");
        productService.addPrivilegesByProductAndResource(5L, 20L, privilege39);
        Map<String, Object> privilege40 = new HashMap<String, Object>();
        privilege40.put("name", "Privilege40");
        productService.addPrivilegesByProductAndResource(5L, 20L, privilege40);
        Map<String, Object> privilege41 = new HashMap<String, Object>();
        privilege41.put("name", "Privilege41");
        productService.addPrivilegesByProductAndResource(6L, 21L, privilege41);
        Map<String, Object> privilege42 = new HashMap<String, Object>();
        privilege42.put("name", "Privilege42");
        productService.addPrivilegesByProductAndResource(6L, 21L, privilege42);
        Map<String, Object> privilege43 = new HashMap<String, Object>();
        privilege43.put("name", "Privilege43");
        productService.addPrivilegesByProductAndResource(6L, 22L, privilege43);
        Map<String, Object> privilege44 = new HashMap<String, Object>();
        privilege44.put("name", "Privilege44");
        productService.addPrivilegesByProductAndResource(6L, 22L, privilege44);
        Map<String, Object> privilege45 = new HashMap<String, Object>();
        privilege45.put("name", "Privilege45");
        productService.addPrivilegesByProductAndResource(6L, 23L, privilege45);
        Map<String, Object> privilege46 = new HashMap<String, Object>();
        privilege46.put("name", "Privilege46");
        productService.addPrivilegesByProductAndResource(6L, 23L, privilege46);
        Map<String, Object> privilege47 = new HashMap<String, Object>();
        privilege47.put("name", "Privilege47");
        productService.addPrivilegesByProductAndResource(6L, 24L, privilege47);
        Map<String, Object> privilege48 = new HashMap<String, Object>();
        privilege48.put("name", "Privilege48");
        productService.addPrivilegesByProductAndResource(6L, 24L, privilege48);
        

        // Add ProductGroups
        Map<String, Object> productGroup1 = new HashMap<String, Object>();
        productGroup1.put("name", "Customer");
        productGroup1.put("product_id", 1L);
        productService.addProductGroup(productGroup1);
        Map<String, Object> productGroup2 = new HashMap<String, Object>();
        productGroup2.put("name", "Investor");
        productGroup2.put("product_id", 1L);
        productService.addProductGroup(productGroup2);
        Map<String, Object> productGroup3 = new HashMap<String, Object>();
        productGroup3.put("name", "Customer");
        productGroup3.put("product_id", 2L);
        productService.addProductGroup(productGroup3);
        Map<String, Object> productGroup4 = new HashMap<String, Object>();
        productGroup4.put("name", "Investor");
        productGroup4.put("product_id", 2L);
        productService.addProductGroup(productGroup4);
        Map<String, Object> productGroup5 = new HashMap<String, Object>();
        productGroup5.put("name", "Customer");
        productGroup5.put("product_id", 3L);
        productService.addProductGroup(productGroup5);
        Map<String, Object> productGroup6 = new HashMap<String, Object>();
        productGroup6.put("name", "Investor");
        productGroup6.put("product_id", 3L);
        productService.addProductGroup(productGroup6);
        Map<String, Object> productGroup7 = new HashMap<String, Object>();
        productGroup7.put("name", "Customer");
        productGroup7.put("product_id", 4L);
        productService.addProductGroup(productGroup7);
        Map<String, Object> productGroup8 = new HashMap<String, Object>();
        productGroup8.put("name", "Investor");
        productGroup8.put("product_id", 4L);
        productService.addProductGroup(productGroup8);
        Map<String, Object> productGroup9 = new HashMap<String, Object>();
        productGroup9.put("name", "Customer");
        productGroup9.put("product_id", 5L);
        productService.addProductGroup(productGroup9);
        Map<String, Object> productGroup10 = new HashMap<String, Object>();
        productGroup10.put("name", "Investor");
        productGroup10.put("product_id", 5L);
        productService.addProductGroup(productGroup10);
        Map<String, Object> productGroup11 = new HashMap<String, Object>();
        productGroup11.put("name", "Customer");
        productGroup11.put("product_id", 6L);
        productService.addProductGroup(productGroup11);
        Map<String, Object> productGroup12 = new HashMap<String, Object>();
        productGroup12.put("name", "Investor");
        productGroup12.put("product_id", 6L);
        productService.addProductGroup(productGroup12);
        

        // Add RoleTemplates
        Map<String, Object> roleTemplate1 = new HashMap<String, Object>();
        roleTemplate1.put("name", "Admin");
        roleTemplate1.put("product_id", 1L);
        roleTemplate1.put("product_group_id", 1L);
        roleTemplate1.put("privileges", new ArrayList<>(Arrays.asList(1L, 2L)));
        productService.addRoleTemplates(roleTemplate1);
        Map<String, Object> roleTemplate2 = new HashMap<String, Object>();
        roleTemplate2.put("name", "Non-Admin");
        roleTemplate2.put("product_id", 1L);
        roleTemplate2.put("product_group_id", 1L);
        roleTemplate2.put("privileges", new ArrayList<>(Arrays.asList(3L, 4L)));
        productService.addRoleTemplates(roleTemplate2);
        Map<String, Object> roleTemplate3 = new HashMap<String, Object>();
        roleTemplate3.put("name", "Admin");
        roleTemplate3.put("product_id", 1L);
        roleTemplate3.put("product_group_id", 2L);
        roleTemplate3.put("privileges", new ArrayList<>(Arrays.asList(5L, 6L)));
        productService.addRoleTemplates(roleTemplate3);
        Map<String, Object> roleTemplate4 = new HashMap<String, Object>();
        roleTemplate4.put("name", "Non-Admin");
        roleTemplate4.put("product_id", 1L);
        roleTemplate4.put("product_group_id", 2L);
        roleTemplate4.put("privileges", new ArrayList<>(Arrays.asList(7L, 8L)));
        productService.addRoleTemplates(roleTemplate4);
        Map<String, Object> roleTemplate5 = new HashMap<String, Object>();
        roleTemplate5.put("name", "Admin");
        roleTemplate5.put("product_id", 2L);
        roleTemplate5.put("product_group_id", 3L);
        roleTemplate5.put("privileges", new ArrayList<>(Arrays.asList(9L, 10L)));
        productService.addRoleTemplates(roleTemplate5);
        Map<String, Object> roleTemplate6 = new HashMap<String, Object>();
        roleTemplate6.put("name", "Non-Admin");
        roleTemplate6.put("product_id", 2L);
        roleTemplate6.put("product_group_id", 3L);
        roleTemplate6.put("privileges", new ArrayList<>(Arrays.asList(11L, 12L)));
        productService.addRoleTemplates(roleTemplate6);
        Map<String, Object> roleTemplate7 = new HashMap<String, Object>();
        roleTemplate7.put("name", "Admin");
        roleTemplate7.put("product_id", 2L);
        roleTemplate7.put("product_group_id", 4L);
        roleTemplate7.put("privileges", new ArrayList<>(Arrays.asList(13L, 14L)));
        productService.addRoleTemplates(roleTemplate7);
        Map<String, Object> roleTemplate8 = new HashMap<String, Object>();
        roleTemplate8.put("name", "Non-Admin");
        roleTemplate8.put("product_id", 2L);
        roleTemplate8.put("product_group_id", 4L);
        roleTemplate8.put("privileges", new ArrayList<>(Arrays.asList(15L, 16L)));
        productService.addRoleTemplates(roleTemplate8);
        Map<String, Object> roleTemplate9 = new HashMap<String, Object>();
        roleTemplate9.put("name", "Admin");
        roleTemplate9.put("product_id", 3L);
        roleTemplate9.put("product_group_id", 5L);
        roleTemplate9.put("privileges", new ArrayList<>(Arrays.asList(17L, 18L)));
        productService.addRoleTemplates(roleTemplate9);
        Map<String, Object> roleTemplate10 = new HashMap<String, Object>();
        roleTemplate10.put("name", "Non-Admin");
        roleTemplate10.put("product_id", 3L);
        roleTemplate10.put("product_group_id", 5L);
        roleTemplate10.put("privileges", new ArrayList<>(Arrays.asList(19L, 20L)));
        productService.addRoleTemplates(roleTemplate10);
        Map<String, Object> roleTemplate11 = new HashMap<String, Object>();
        roleTemplate11.put("name", "Admin");
        roleTemplate11.put("product_id", 3L);
        roleTemplate11.put("product_group_id", 6L);
        roleTemplate11.put("privileges", new ArrayList<>(Arrays.asList(21L, 22L)));
        productService.addRoleTemplates(roleTemplate11);
        Map<String, Object> roleTemplate12 = new HashMap<String, Object>();
        roleTemplate12.put("name", "Non-Admin");
        roleTemplate12.put("product_id", 3L);
        roleTemplate12.put("product_group_id", 6L);
        roleTemplate12.put("privileges", new ArrayList<>(Arrays.asList(23L, 24L)));
        productService.addRoleTemplates(roleTemplate12);
        Map<String, Object> roleTemplate13 = new HashMap<String, Object>();
        roleTemplate13.put("name", "Admin");
        roleTemplate13.put("product_id", 4L);
        roleTemplate13.put("product_group_id", 7L);
        roleTemplate13.put("privileges", new ArrayList<>(Arrays.asList(25L, 26L)));
        productService.addRoleTemplates(roleTemplate13);
        Map<String, Object> roleTemplate14 = new HashMap<String, Object>();
        roleTemplate14.put("name", "Non-Admin");
        roleTemplate14.put("product_id", 4L);
        roleTemplate14.put("product_group_id", 7L);
        roleTemplate14.put("privileges", new ArrayList<>(Arrays.asList(27L, 28L)));
        productService.addRoleTemplates(roleTemplate14);
        Map<String, Object> roleTemplate15 = new HashMap<String, Object>();
        roleTemplate15.put("name", "Admin");
        roleTemplate15.put("product_id", 4L);
        roleTemplate15.put("product_group_id", 8L);
        roleTemplate15.put("privileges", new ArrayList<>(Arrays.asList(29L, 30L)));
        productService.addRoleTemplates(roleTemplate15);
        Map<String, Object> roleTemplate16 = new HashMap<String, Object>();
        roleTemplate16.put("name", "Non-Admin");
        roleTemplate16.put("product_id", 4L);
        roleTemplate16.put("product_group_id", 8L);
        roleTemplate16.put("privileges", new ArrayList<>(Arrays.asList(31L, 32L)));
        productService.addRoleTemplates(roleTemplate16);
        Map<String, Object> roleTemplate17 = new HashMap<String, Object>();
        roleTemplate17.put("name", "Admin");
        roleTemplate17.put("product_id", 5L);
        roleTemplate17.put("product_group_id", 9L);
        roleTemplate17.put("privileges", new ArrayList<>(Arrays.asList(33L, 34L)));
        productService.addRoleTemplates(roleTemplate17);
        Map<String, Object> roleTemplate18 = new HashMap<String, Object>();
        roleTemplate18.put("name", "Non-Admin");
        roleTemplate18.put("product_id", 5L);
        roleTemplate18.put("product_group_id", 9L);
        roleTemplate18.put("privileges", new ArrayList<>(Arrays.asList(35L, 36L)));
        productService.addRoleTemplates(roleTemplate18);
        Map<String, Object> roleTemplate19 = new HashMap<String, Object>();
        roleTemplate19.put("name", "Admin");
        roleTemplate19.put("product_id", 5L);
        roleTemplate19.put("product_group_id", 10L);
        roleTemplate19.put("privileges", new ArrayList<>(Arrays.asList(37L, 38L)));
        productService.addRoleTemplates(roleTemplate19);
        Map<String, Object> roleTemplate20 = new HashMap<String, Object>();
        roleTemplate20.put("name", "Non-Admin");
        roleTemplate20.put("product_id", 5L);
        roleTemplate20.put("product_group_id", 10L);
        roleTemplate20.put("privileges", new ArrayList<>(Arrays.asList(39L, 40L)));
        productService.addRoleTemplates(roleTemplate20);
        Map<String, Object> roleTemplate21 = new HashMap<String, Object>();
        roleTemplate21.put("name", "Admin");
        roleTemplate21.put("product_id", 6L);
        roleTemplate21.put("product_group_id", 11L);
        roleTemplate21.put("privileges", new ArrayList<>(Arrays.asList(41L, 42L)));
        productService.addRoleTemplates(roleTemplate21);
        Map<String, Object> roleTemplate22 = new HashMap<String, Object>();
        roleTemplate22.put("name", "Non-Admin");
        roleTemplate22.put("product_id", 6L);
        roleTemplate22.put("product_group_id", 11L);
        roleTemplate22.put("privileges", new ArrayList<>(Arrays.asList(43L, 44L)));
        productService.addRoleTemplates(roleTemplate22);
        Map<String, Object> roleTemplate23 = new HashMap<String, Object>();
        roleTemplate23.put("name", "Admin");
        roleTemplate23.put("product_id", 6L);
        roleTemplate23.put("product_group_id", 12L);
        roleTemplate23.put("privileges", new ArrayList<>(Arrays.asList(45L, 46L)));
        productService.addRoleTemplates(roleTemplate23);
        Map<String, Object> roleTemplate24 = new HashMap<String, Object>();
        roleTemplate24.put("name", "Non-Admin");
        roleTemplate24.put("product_id", 6L);
        roleTemplate24.put("product_group_id", 12L);
        roleTemplate24.put("privileges", new ArrayList<>(Arrays.asList(47L, 48L)));
        productService.addRoleTemplates(roleTemplate24);
       

        // Add ProductGroupResources
        Map<String, Object> productGroupResource1 = new HashMap<String, Object>();
        productGroupResource1.put("product_id", 1L);
        productGroupResource1.put("product_group_id", 1L);
        productGroupResource1.put("resource_id", 1L);
        productService.addProductGroupResources(productGroupResource1);
        Map<String, Object> productGroupResource2 = new HashMap<String, Object>();
        productGroupResource2.put("product_id", 1L);
        productGroupResource2.put("product_group_id", 1L);
        productGroupResource2.put("resource_id", 2L);
        productService.addProductGroupResources(productGroupResource2);
        Map<String, Object> productGroupResource3 = new HashMap<String, Object>();
        productGroupResource3.put("product_id", 1L);
        productGroupResource3.put("product_group_id", 2L);
        productGroupResource3.put("resource_id", 3L);
        productService.addProductGroupResources(productGroupResource3);
        Map<String, Object> productGroupResource4 = new HashMap<String, Object>();
        productGroupResource4.put("product_id", 1L);
        productGroupResource4.put("product_group_id", 2L);
        productGroupResource4.put("resource_id", 4L);
        productService.addProductGroupResources(productGroupResource4);
        Map<String, Object> productGroupResource5 = new HashMap<String, Object>();
        productGroupResource5.put("product_id", 2L);
        productGroupResource5.put("product_group_id", 3L);
        productGroupResource5.put("resource_id", 5L);
        productService.addProductGroupResources(productGroupResource5);
        Map<String, Object> productGroupResource6 = new HashMap<String, Object>();
        productGroupResource6.put("product_id", 2L);
        productGroupResource6.put("product_group_id", 3L);
        productGroupResource6.put("resource_id", 6L);
        productService.addProductGroupResources(productGroupResource6);
        Map<String, Object> productGroupResource7 = new HashMap<String, Object>();
        productGroupResource7.put("product_id", 2L);
        productGroupResource7.put("product_group_id", 4L);
        productGroupResource7.put("resource_id", 7L);
        productService.addProductGroupResources(productGroupResource7);
        Map<String, Object> productGroupResource8 = new HashMap<String, Object>();
        productGroupResource8.put("product_id", 2L);
        productGroupResource8.put("product_group_id", 4L);
        productGroupResource8.put("resource_id", 8L);
        productService.addProductGroupResources(productGroupResource8);
        Map<String, Object> productGroupResource9 = new HashMap<String, Object>();
        productGroupResource9.put("product_id", 3L);
        productGroupResource9.put("product_group_id", 5L);
        productGroupResource9.put("resource_id", 9L);
        productService.addProductGroupResources(productGroupResource9);
        Map<String, Object> productGroupResource10 = new HashMap<String, Object>();
        productGroupResource10.put("product_id", 3L);
        productGroupResource10.put("product_group_id", 5L);
        productGroupResource10.put("resource_id", 10L);
        productService.addProductGroupResources(productGroupResource10);
        Map<String, Object> productGroupResource11 = new HashMap<String, Object>();
        productGroupResource11.put("product_id", 3L);
        productGroupResource11.put("product_group_id", 6L);
        productGroupResource11.put("resource_id", 11L);
        productService.addProductGroupResources(productGroupResource11);
        Map<String, Object> productGroupResource12 = new HashMap<String, Object>();
        productGroupResource12.put("product_id", 3L);
        productGroupResource12.put("product_group_id", 6L);
        productGroupResource12.put("resource_id", 12L);
        productService.addProductGroupResources(productGroupResource12);
        Map<String, Object> productGroupResource13 = new HashMap<String, Object>();
        productGroupResource13.put("product_id", 4L);
        productGroupResource13.put("product_group_id", 7L);
        productGroupResource13.put("resource_id", 13L);
        productService.addProductGroupResources(productGroupResource13);
        Map<String, Object> productGroupResource14 = new HashMap<String, Object>();
        productGroupResource14.put("product_id", 4L);
        productGroupResource14.put("product_group_id", 7L);
        productGroupResource14.put("resource_id", 14L);
        productService.addProductGroupResources(productGroupResource14);
        Map<String, Object> productGroupResource15 = new HashMap<String, Object>();
        productGroupResource15.put("product_id", 4L);
        productGroupResource15.put("product_group_id", 8L);
        productGroupResource15.put("resource_id", 15L);
        productService.addProductGroupResources(productGroupResource15);
        Map<String, Object> productGroupResource16 = new HashMap<String, Object>();
        productGroupResource16.put("product_id", 4L);
        productGroupResource16.put("product_group_id", 8L);
        productGroupResource16.put("resource_id", 16L);
        productService.addProductGroupResources(productGroupResource16);
        Map<String, Object> productGroupResource17 = new HashMap<String, Object>();
        productGroupResource17.put("product_id", 5L);
        productGroupResource17.put("product_group_id", 9L);
        productGroupResource17.put("resource_id", 17L);
        productService.addProductGroupResources(productGroupResource17);
        Map<String, Object> productGroupResource18 = new HashMap<String, Object>();
        productGroupResource18.put("product_id", 5L);
        productGroupResource18.put("product_group_id", 9L);
        productGroupResource18.put("resource_id", 18L);
        productService.addProductGroupResources(productGroupResource18);
        Map<String, Object> productGroupResource19 = new HashMap<String, Object>();
        productGroupResource19.put("product_id", 5L);
        productGroupResource19.put("product_group_id", 10L);
        productGroupResource19.put("resource_id", 19L);
        productService.addProductGroupResources(productGroupResource19);
        Map<String, Object> productGroupResource20 = new HashMap<String, Object>();
        productGroupResource20.put("product_id", 5L);
        productGroupResource20.put("product_group_id", 10L);
        productGroupResource20.put("resource_id", 20L);
        productService.addProductGroupResources(productGroupResource20);
        Map<String, Object> productGroupResource21 = new HashMap<String, Object>();
        productGroupResource21.put("product_id", 6L);
        productGroupResource21.put("product_group_id", 11L);
        productGroupResource21.put("resource_id", 21L);
        productService.addProductGroupResources(productGroupResource21);
        Map<String, Object> productGroupResource22 = new HashMap<String, Object>();
        productGroupResource22.put("product_id", 6L);
        productGroupResource22.put("product_group_id", 11L);
        productGroupResource22.put("resource_id", 22L);
        productService.addProductGroupResources(productGroupResource22);
        Map<String, Object> productGroupResource23 = new HashMap<String, Object>();
        productGroupResource23.put("product_id", 6L);
        productGroupResource23.put("product_group_id", 12L);
        productGroupResource23.put("resource_id", 23L);
        productService.addProductGroupResources(productGroupResource23);
        Map<String, Object> productGroupResource24 = new HashMap<String, Object>();
        productGroupResource24.put("product_id", 6L);
        productGroupResource24.put("product_group_id", 12L);
        productGroupResource24.put("resource_id", 24L);
        productService.addProductGroupResources(productGroupResource24);
       
    }



}