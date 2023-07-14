// The service class is used to write the business logic in the service layer.
// Business logic is the part of the program that encodes the real-world business rules that determine how data can be created, displayed, stored, and changed.
// It is contrasted with the remainder of the software that might be concerned with lower-level details of managing a database or displaying the user interface, system infrastructure, or generally connecting various parts of the program.

package com.yubi.platform.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yubi.platform.element.Privileges;
import com.yubi.platform.element.ProductGroupResources;
import com.yubi.platform.element.ProductGroups;
import com.yubi.platform.element.Products;
import com.yubi.platform.element.Resources;
import com.yubi.platform.element.RoleTemplates;
import com.yubi.platform.repository.PrivilegesRepository;
import com.yubi.platform.repository.ProductGroupResourcesRepository;
import com.yubi.platform.repository.ProductGroupsRepository;
import com.yubi.platform.repository.ProductsRepository;
import com.yubi.platform.repository.ResourcesRepository;
import com.yubi.platform.repository.RoleTemplatesRepository;

// The @Service annotation is used to define the service class.
@Service
public class ProductService {

    // The @Autowired annotation is used to inject the dependency of the service class.
    @Autowired
    private ProductsRepository productRepository;

    @Autowired
    private ProductGroupsRepository productGroupsRepository;

    @Autowired
    private RoleTemplatesRepository roleTemplatesRepository;

    @Autowired
    private PrivilegesRepository privilegesRepository;

    @Autowired
    private ResourcesRepository resourcesRepository;

    @Autowired
    private ProductGroupResourcesRepository productGroupResourcesRepository;


    /*                                         -------------------------------------- Products -----------------------------------------                                      */

    public List<Map<String, Object>> getAllProduct() {
        return productRepository.findAllProduct();
    }

    public Products getProductById(Long product_id) {
        return productRepository.findById(product_id).orElse(null);
    }

    public Map<String, Object> addProduct(Map<String, Object> product) {
        return productRepository.add(productRepository.count()+1L, (String) product.get("name"), (String) product.get("code"));
    }


    /*                                         -------------------------------------- Product Groups -----------------------------------------                                      */


    public List<Map<String, Object>> getAllProductGroup() {
        return productGroupsRepository.findAllProductGroup();
    }

    public ProductGroups getProductGroupById(Long product_group_id) {
        return productGroupsRepository.findById(product_group_id).orElse(null);
    }

    public Map<String, Object> addProductGroup(Map<String, Object> productGroup) {
        System.out.println("product group still not added \n");
        return productGroupsRepository.add(productGroupsRepository.count()+1L, (String) productGroup.get("name"),  Long.valueOf(productGroup.get("product_id").toString()));
    }


    /*                                         -------------------------------------- Role Templates -----------------------------------------                                      */


    public List<Map<String, Object>> getAllRoleTemplates() {
        return roleTemplatesRepository.findAllRoleTemplates();
    }

    public RoleTemplates getRoleTemplateById(Long role_template_id) {
        return roleTemplatesRepository.findById(role_template_id).orElse(null);
    }

    public Map<String, Object> addRoleTemplates(Map<String, Object> roleTemplates) {
        
        Map<String, Object> error = new HashMap<String, Object>();
        List<Long> privileges = new ArrayList<>();
        Object obj = roleTemplates.get("privileges");
        // String className = obj.getClass().getSimpleName();
        // System.out.println("Object is of type " + className);
        if (obj instanceof ArrayList){
            ArrayList<?> rawList = (ArrayList<?>) obj;
            for (Object id : rawList) {
                Long privilegesId = Long.valueOf(id.toString());
                if(getPrivilegesById(privilegesId)==null){
                    error.put("error", "Privilege with id "+privilegesId+" does not exist");
                    return error;
                }
                privileges.add(Long.valueOf(id.toString()));
            }
        }
        Products product = productRepository.findById(Long.valueOf(roleTemplates.get("product_id").toString())).get();
        ProductGroups productGroup = productGroupsRepository.findById(Long.valueOf(roleTemplates.get("product_group_id").toString())).get();

        RoleTemplates newRoleTemplate = roleTemplatesRepository.save(new RoleTemplates((Long) roleTemplatesRepository.count()+1L, (String) roleTemplates.get("name"), product, productGroup, privileges));

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", newRoleTemplate.getId());
        result.put("name", newRoleTemplate.getName());
        result.put("product_id", newRoleTemplate.getProduct().getId());
        result.put("product_group_id", newRoleTemplate.getProductGroup().getId());
        result.put("privileges", newRoleTemplate.getPrivileges());
        return result;
    }


    /*                                         -------------------------------------- Privileges -----------------------------------------                                      */


    public List<Map<String, Object>> getAllPrivilegesByProductAndResource(Long product_id, Long resource_id) {
        return privilegesRepository.findAllPrivileges(product_id, resource_id);
    }

    public Privileges getPrivilegesById(Long privilege_id) {
        return privilegesRepository.findById(privilege_id).orElse(null);
    }

    public Map<String, Object> addPrivilegesByProductAndResource(Long product_id, Long resource_id, Map<String, Object> privileges) {
        return privilegesRepository.add(privilegesRepository.count()+1L, (String) privileges.get("name"), product_id, resource_id);
    }

    public Map<String, Object> deletePrivilegeById(Long privilege_id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> error = new HashMap<String, Object>();
        Privileges privilege = getPrivilegesById(privilege_id);
        if(privilege==null){
            error.put("error", "Privilege with id "+privilege_id+" does not exist");
            return error;
        }
        result.put("id", privilege.getId());
        result.put("name", privilege.getName());
        result.put("product_id", privilege.getProduct().getId());
        result.put("resource_id", privilege.getResource().getId());
        privilegesRepository.deleteById(privilege_id);
        return result;
    }


    /*                                         -------------------------------------- Resources -----------------------------------------                                      */


    public List<Map<String, Object>> getAllResourcesByProduct(Long product_id) {
        return resourcesRepository.findAllResourcesByProduct( product_id);
    }

    public Resources getResourcesById(Long resource_id) {
        return resourcesRepository.findById(resource_id).orElse(null);
    }

    public Map<String, Object> getResourceById(Long resource_id) {
        return resourcesRepository.findResourceById(resource_id);
    }

    public Map<String, Object> addResourcesByProduct(Long product_id, Map<String, Object> resources) {
        return resourcesRepository.add(resourcesRepository.count()+1L, (String) resources.get("name"), product_id);
    }

    public Map<String, Object> deleteResourceById(Long resource_id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> error = new HashMap<String, Object>();
        Resources resource = getResourcesById(resource_id);
        if(resource==null){
            error.put("error", "Resource with id "+resource_id+" does not exist");
            return error;
        }
        result.put("id", resource.getId());
        result.put("name", resource.getName());
        result.put("product_id", resource.getProduct().getId());
        resourcesRepository.deleteById(resource_id);
        return result;
    }

    public Map<String, Object> updateResourceById(Long resource_id, Map<String, Object> resource) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> error = new HashMap<String, Object>();
        Resources resourceToUpdate = getResourcesById(resource_id);
        if(resourceToUpdate==null){
            error.put("error", "Resource with id "+resource_id+" does not exist");
            return error;
        }
        resourceToUpdate.setName((String) resource.get("name"));
        resourceToUpdate.setProduct(productRepository.findById(Long.valueOf(resource.get("product_id").toString())).get());
        resourcesRepository.save(resourceToUpdate);
        Resources updatedResource = getResourcesById(resource_id);
        result.put("id", updatedResource.getId());
        result.put("name", updatedResource.getName());
        result.put("product_id", updatedResource.getProduct().getId());
        return result;
    }

    public Privileges getPrivilegeById(Long privilegeId) {
        return privilegesRepository.findById(privilegeId).orElse(null);
    }


    /*                                         -------------------------------------- Product Group Resources -----------------------------------------                                      */


    public List<Map<String, Object>> getAllProductGroupResources() {
        return productGroupResourcesRepository.findAllProductGroupResources();
    }

    public ProductGroupResources getProductGroupResourcesById(Long product_group_resource_id) {
        return productGroupResourcesRepository.findById(product_group_resource_id).orElse(null);
    }

    public Map<String, Object> addProductGroupResources(Map<String, Object> productGroupResources) {
        return productGroupResourcesRepository.add(productGroupResourcesRepository.count()+1L, Long.valueOf(productGroupResources.get("product_id").toString()), Long.valueOf(productGroupResources.get("product_group_id").toString()), Long.valueOf(productGroupResources.get("resource_id").toString()));
    }
    
}