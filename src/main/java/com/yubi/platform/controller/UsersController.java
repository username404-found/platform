// A controller class is used to define the endpoints for the REST API.

package com.yubi.platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yubi.platform.service.EntitiesService;
import com.yubi.platform.service.UsersService;

// The @RestController annotation is used to define the controller class.
@RestController
public class UsersController {
    
    // The @Autowired annotation is used to inject the dependency of the service class.
    @Autowired
    private UsersService userService;

    @Autowired
    private EntitiesService entityService;


    /*                                         -------------------------------------- Users -----------------------------------------                                      */


    // The @GetMapping annotation is used when the user wants to get some information.
    @GetMapping("/users")
    public List<Map<String, Object>> getUsers() {
        return userService.getAllUser();
    }

    // The @PostMapping annotation is used when the user wants to post some information.
    // The @RequestBody annotation is used to get the data from the request body.
    // The @PathVariable annotation is used to get the data from the path variable.
    @PostMapping("/entity/{entity_id}/add_user")
    public Map<String, Object> addUser(@PathVariable Long entity_id, @RequestBody Map<String, Object> userEntity) {
        if (entityService.getEntityById(entity_id) == null) {
            return null;
        }

        Map<String, Object> user = new HashMap<>();
        user.put("name", userEntity.get("name"));
        user.put("email", userEntity.get("email"));
        Map<String, Object> newUser = userService.addUser(user);

        Map<String, Object> entityUser = new HashMap<>();
        entityUser.put("user_id", newUser.get("id"));
        entityUser.put("entity_id", entity_id);
        entityUser.put("groups", userEntity.get("groups"));
        entityUser.put("credit_roles", userEntity.get("credit_roles"));
        entityUser.put("credpool_roles", userEntity.get("credpool_roles"));
        entityUser.put("loan_roles", userEntity.get("loan_roles"));
        entityUser.put("user_platform_roles", userEntity.get("user_platform_roles"));
        Map<String, Object> newEntityUser = userService.addEntityUser(entityUser);

        Map<String, Object> returnEntity = new HashMap<>();
        returnEntity.put("user_id", newUser.get("id"));
        returnEntity.put("name", newUser.get("name"));
        returnEntity.put("email", newUser.get("email"));
        returnEntity.put("entity_id", entity_id);
        returnEntity.put("groups", newEntityUser.get("groups"));
        returnEntity.put("credit_roles", newEntityUser.get("credit_roles"));
        returnEntity.put("credpool_roles", newEntityUser.get("credpool_roles"));
        returnEntity.put("loan_roles", newEntityUser.get("loan_roles"));
        returnEntity.put("user_platform_roles", newEntityUser.get("user_platform_roles"));

        userService.addUserRoles(entityUser);
        return returnEntity;
    }

    @GetMapping("/users/entity/{entity_id}")
    public List<Map<String, Object>> getUsersByEntity(@PathVariable Long entity_id) {
        return userService.getUsersByEntity(entity_id);
    }

    @GetMapping("/users/{user_id}")
    public Map<String, Object> getUserById(@PathVariable Long user_id) {
        return userService.getUserById(user_id);
    }

    @PostMapping("/update/user/{user_id}")
    public Map<String, Object> updateUserById(@PathVariable Long user_id, @RequestBody Map<String, Object> user) {
        return userService.updateUserById(user, user_id);
    }

    @GetMapping("/delete/user/{user_id}")
    public Map<String, Object> deleteUserById(@PathVariable Long user_id) {
        return userService.deleteUserById(user_id);
    }


    /*                                         -------------------------------------- Entity Users -----------------------------------------                                      */


    @GetMapping("/entity_users")
    public List<Map<String, Object>> getAllEntityUser() {
        return userService.getAllEntityUser();
    }
    
    @GetMapping("/users/{user_id}/privileges")
    public Map<String, Object> getUserPrivilegesByUserId(@PathVariable Long user_id) {
        return userService.getUserPrivilegesByUserId(user_id);
    }

    @GetMapping("/roles/user/{user_id}")
    public List<Map<String, Object>> getUserRolesByUserId(@PathVariable Long user_id) {
        return userService.getUserRolesByUserId(user_id);
    }

    @PostMapping("/update/roles/user/{user_id}")
    public Map<String, Object> updateUserRolesByUserId(@PathVariable Long user_id, @RequestBody Map<String, Object> roles) {
        return userService.updateUserRolesByUserId(roles, user_id);
    }

    @GetMapping("/users/{user_id}/privileges/product/{product_id}")
    public List<Map<String, Object>> getUserPrivilegesByProduct(@PathVariable Long user_id, @PathVariable Long product_id) {
        return userService.getUserPrivilegesByProduct(user_id, product_id);
    }

    @GetMapping("/users/{user_id}/privileges/product/{product_id}/entity/{entity_id}/group/{group_id}")
    public List<Map<String, Object>> getUserPrivilegesByProductAndEntityAndGroup(@PathVariable Long user_id, @PathVariable Long product_id, @PathVariable Long entity_id, @PathVariable Long group_id) {
        return userService.getUserPrivilegesByProductAndEntityAndGroup(user_id, product_id, entity_id, group_id);
    }

    @GetMapping("/users/{user_id}/privileges/product/{product_id}/entity/{entity_id}")
    public List<Map<String, Object>> getUserPrivilegesByProductAndEntity(@PathVariable Long user_id, @PathVariable Long product_id, @PathVariable Long entity_id) {
        return userService.getUserPrivilegesByProductAndEntity(user_id, product_id, entity_id);
    }


}

