// A controller class is used to define the endpoints for the REST API.

package com.yubi.platform.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yubi.platform.service.EntitiesService;

// The @RestController annotation is used to define the controller class.
@RestController
public class EntitiesController {
    
    // The @Autowired annotation is used to inject the dependency of the service class.
    @Autowired
    private EntitiesService entityService;



    /*                                         -------------------------------------- Entities -----------------------------------------                                      */



    // The @GetMapping annotation is used when the user wants to get some information.
    @GetMapping("/entities")
    public List<Map<String, Object>> getEntities() {
        return entityService.getAllEntity();
    }

    @GetMapping("/entities/{entityId}")
    public Map<String, Object> getEntitiesById(@PathVariable long entityId) {
        return entityService.getEntitiesById(entityId);
    }

    // The @PostMapping annotation is used when the user wants to post some information.
    // The @RequestBody annotation is used to get the data from the request body.
    @PostMapping("/addEntity")
    public Map<String, Object> addEntity(@RequestBody Map<String, Object> entity) {
        return entityService.addEntity(entity);
    }

    // The @PathVariable annotation is used to get the data from the path variable.
    @PostMapping("/updateEntity/{entityId}")
    public Map<String, Object> updateEntityById(@RequestBody Map<String, Object> entity, @PathVariable long entityId) {
        return entityService.updateEntityById(entity, entityId);
    }

    @GetMapping("/subEntities/{entityId}")
    public List<Map<String, Object>> getSubEntities(@PathVariable long entityId) {
        return entityService.getSubEntities(entityId);
    }


    /*                                         -------------------------------------- Groups -----------------------------------------                                      */


    @GetMapping("/groups/{entityId}")
    public List<Map<String, Object>> getAllGroupsByEntity(@PathVariable long entityId) {
        return entityService.getAllGroupsByEntity(entityId);
    }

    @GetMapping("/groups/{entityId}/{productId}")
    public List<Map<String, Object>> getGroupsByEntityAndProduct(@PathVariable long entityId, @PathVariable long productId) {
        return entityService.getAllGroupsByEntityAndProduct(entityId, productId);
    }

    @PostMapping("/group/update/{groupId}")
    public Map<String, Object> updateGroupById(@RequestBody Map<String, Object> newGroup, @PathVariable long groupId) {
        return entityService.updateGroupById(newGroup, groupId);
    }

    @PostMapping("/addGroup")
    public Map<String, Object> addGroups(@RequestBody Map<String, Object> entityProductGroup){
        return entityService.addGroups(entityProductGroup);
    }


    /*                                         -------------------------------------- Roles -----------------------------------------                                      */
    

    @GetMapping("/roles")
    public List<Map<String, Object>> getRoles() {
        return entityService.getAllRoles();
    }

    @GetMapping("/roles/entity/{entityId}/group/{groupId}/product/{productId}")
    public List<Map<String, Object>> getRolesByEntityAndGroupAndProduct(@PathVariable long entityId, @PathVariable long groupId, @PathVariable long productId) {
        return entityService.getAllRolesByEntityAndGroupAndProduct(entityId, groupId, productId);
    }

    @GetMapping("/roles/{role_id}")
    public Map<String, Object> getRoleById(@PathVariable long role_id) {
        return entityService.getRoleById(role_id);
    }

    @PostMapping("/roles/entity/{entityId}/group/{groupId}/product/{productId}")
    public Map<String, Object> addRolesByEntityAndGroupAndProduct(@RequestBody Map<String, Object> entityProductGroupRoles, @PathVariable long entityId, @PathVariable long groupId, @PathVariable long productId){
        return entityService.addRolesByEntityAndGroupAndProduct(entityProductGroupRoles, entityId, groupId, productId);
    }

    @PostMapping("/roles/update/{roleId}")
    public Map<String, Object> updateRoleById(@RequestBody Map<String, Object> newRole, @PathVariable long roleId) {
        return entityService.updateRoleById(newRole, roleId);
    }

    @GetMapping("/roles/delete/{roleId}")
    public Map<String, Object> deleteRoleById(@PathVariable long roleId) {
        return entityService.deleteRoleById(roleId);
    }

    @PostMapping("/addRoles")
    public Map<String, Object> addRoles(@RequestBody Map<String, Object> entityProductGroupRoles){
        return entityService.addRoles(entityProductGroupRoles);
    }
 
}