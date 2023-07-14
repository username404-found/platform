// The service class is used to write the business logic in the service layer.
// Business logic is the part of the program that encodes the real-world business rules that determine how data can be created, displayed, stored, and changed.
// It is contrasted with the remainder of the software that might be concerned with lower-level details of managing a database or displaying the user interface, system infrastructure, or generally connecting various parts of the program.

package com.yubi.platform.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.yubi.platform.element.Entities;
import com.yubi.platform.element.EntityUsers;
import com.yubi.platform.element.Groups;
import com.yubi.platform.element.Privileges;
import com.yubi.platform.element.Products;
import com.yubi.platform.element.Roles;
import com.yubi.platform.element.User;
import com.yubi.platform.element.RolesUsers;
import com.yubi.platform.repository.UsersRepository;
import com.yubi.platform.repository.EntityUsersRepository;
import com.yubi.platform.repository.RolesUsersRepository;


// The @Service annotation is used to define the service class.
@Service
public class UsersService {
    
    // The @Autowired annotation is used to inject the dependency of the service class.
    @Autowired
    private EntitiesService entityService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private EntityUsersRepository entityUserRepository;

    @Autowired
    private RolesUsersRepository rolesUsersRepository;


    /*                                         -------------------------------------- Users -----------------------------------------                                      */


    public List<Map<String, Object>> getAllUser() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> users = userRepository.findAllUser();
        for (Map<String, Object> user : users){
            Map<String, Object> entityUser = entityUserRepository.findByUserId((Long) user.get("id"));
            Map<String, Object> res = new HashMap<>();
            res.put("user_id", user.get("id"));
            res.put("name", user.get("name"));
            res.put("email", user.get("email"));
            res.put("entity_id", entityUser.get("entity_id"));
            res.put("groups", entityUser.get("groups"));
            res.put("credit_roles", entityUser.get("credit_roles"));
            res.put("credpool_roles", entityUser.get("credpool_roles"));
            res.put("loan_roles", entityUser.get("loan_roles"));
            res.put("user_platform_roles", entityUser.get("user_platform_roles"));
            result.add(res);
        }
        return result;
    }

    public User getUsersById(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    public Map<String, Object> addUser(Map<String, Object> user) {
        if(userRepository.findByEmail(user.get("email").toString())!=null){
            return null;
        }
        return userRepository.add((Long) userRepository.count() + 1L, (String) user.get("name").toString(), (String) user.get("email").toString());
    }

    public Map<String, Object> getUserById(Long user_id) {
        Map<String, Object> user = userRepository.findByUserId(user_id);
        Map<String, Object> entityUser = entityUserRepository.findByUserId(user_id);
        Map<String, Object> res = new HashMap<>();
        res.put("user_id", user.get("id"));
        res.put("name", user.get("name"));
        res.put("email", user.get("email"));
        res.put("entity_id", entityUser.get("entity_id"));
        res.put("groups", entityUser.get("groups"));
        res.put("credit_roles", entityUser.get("credit_roles"));
        res.put("credpool_roles", entityUser.get("credpool_roles"));
        res.put("loan_roles", entityUser.get("loan_roles"));
        res.put("user_platform_roles", entityUser.get("user_platform_roles"));
        return res;
    }

    public List<Map<String, Object>> getUsersByEntity(Long entity_id) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Long> users_id = entityUserRepository.findByEntityId(entity_id);
        for (Long user_id : users_id){
            result.add(getUserById(user_id));
        }
        return result;
    }

    public Map<String, Object> updateUserById(@RequestBody Map<String, Object> user, @PathVariable Long user_id){
        Map<String, Object> error = new HashMap<>();
        User userObj = getUsersById(user_id);
        if(userObj==null){
            error.put("error", "user not found");
            return error;
        }
        Long entity_user_id = Long.valueOf(entityUserRepository.findByUserId(user_id).get("id").toString());
        EntityUsers entityUserObj = getEntityUserById(entity_user_id);
        Map<String, Object> ret = new HashMap<>();
        for (String key : user.keySet()) {
            if(key.equals("name"))
                userObj.setName(user.get(key).toString());
            else if(key.equals("email"))
                userObj.setEmail(user.get(key).toString());
            else if(key.equals("groups")){
                Object groupsObj = user.get(key);
                if(groupsObj instanceof LinkedHashMap){
                    LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) groupsObj;
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        String key1 = entry.getKey().toString();
                        String value = entry.getValue().toString();
                        Map<String, String> group = new HashMap<>();
                        group.put(key1, value);
                        entityUserObj.setGroups(group);
                    }
                }
                else{
                    error.put("error", "invalid value");
                    return error;
                }
            }
            else if(key.equals("credit_roles")){
                Object creditRolesObj = user.get(key);
                if(creditRolesObj instanceof LinkedHashMap){
                    LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) creditRolesObj;
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        String key1 = entry.getKey().toString();
                        String value = entry.getValue().toString();
                        Map<String, String> creditRoles = new HashMap<>();
                        creditRoles.put(key1, value);
                        entityUserObj.setCredit_roles(creditRoles);
                    }
                }
                else{
                    error.put("error", "invalid value");
                    return error;
                }
            }
            else if(key.equals("credpool_roles")){
                Object credpoolRolesObj = user.get(key);
                if(credpoolRolesObj instanceof LinkedHashMap){
                    LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) credpoolRolesObj;
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        String key1 = entry.getKey().toString();
                        String value = entry.getValue().toString();
                        Map<String, String> credpoolRoles = new HashMap<>();
                        credpoolRoles.put(key1, value);
                        entityUserObj.setCredpool_roles(credpoolRoles);
                    }
                }
                else{
                    error.put("error", "invalid value");
                    return error;
                }
            }
            else if(key.equals("loan_roles")){
                Object loanRolesObj = user.get(key);
                if(loanRolesObj instanceof LinkedHashMap){
                    LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) loanRolesObj;
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        String key1 = entry.getKey().toString();
                        String value = entry.getValue().toString();
                        Map<String, String> loanRoles = new HashMap<>();
                        loanRoles.put(key1, value);
                        entityUserObj.setLoan_roles(loanRoles);
                    }
                }
                else{
                    error.put("error", "invalid value");
                    return error;
                }
            }
            else if(key.equals("user_platform_roles")){
                Object userPlatformRolesObj = user.get(key);
                if(userPlatformRolesObj instanceof LinkedHashMap){
                    LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) userPlatformRolesObj;
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        String key1 = entry.getKey().toString();
                        String value = entry.getValue().toString();
                        Map<String, String> userPlatformRoles = new HashMap<>();
                        userPlatformRoles.put(key1, value);
                        entityUserObj.setUser_platform_roles(userPlatformRoles);
                    }
                }
                else{
                    error.put("error", "invalid value");
                    return error;
                }
            }
            else{
                error.put("error", "invalid key");
                return error;
            }
        }
        userRepository.save(userObj);
        entityUserRepository.save(entityUserObj);
        User newUser = getUsersById(user_id);
        EntityUsers newEntityUser = getEntityUserById(entity_user_id);
        ret.put("user_id", newUser.getId());
        ret.put("name", newUser.getName());
        ret.put("email", newUser.getEmail());
        ret.put("entity_id", newEntityUser.getEntity().getId());
        ret.put("groups", newEntityUser.getGroups());
        ret.put("credit_roles", newEntityUser.getCredit_roles());
        ret.put("credpool_roles", newEntityUser.getCredpool_roles());
        ret.put("loan_roles", newEntityUser.getLoan_roles());
        ret.put("user_platform_roles", newEntityUser.getUser_platform_roles());
        return ret;
    }

    public Map<String, Object> deleteUserById(Long user_id){
        Map<String, Object> error = new HashMap<>();
        User userObj = getUsersById(user_id);
        if(userObj==null){
            error.put("error", "user not found");
            return error;
        }
        Long entity_user_id = Long.valueOf(entityUserRepository.findByUserId(user_id).get("id").toString());
        entityUserRepository.deleteById(entity_user_id);
        rolesUsersRepository.deleteByUserId(user_id);
        userRepository.deleteById(user_id);
        Map<String, Object> ret = new HashMap<>();
        ret.put("user_id", user_id);
        ret.put("status", "deleted");
        return ret;
    }


    /*                                         -------------------------------------- Entity Users -----------------------------------------                                      */


    public Map<String, Object> addEntityUser(Map<String, Object> entityUser) {
        System.out.println("addEntityUser() is called!!\n");
        Object obj1 = entityUser.get("groups"), obj2 = entityUser.get("credit_roles"), obj3 = entityUser.get("credpool_roles"), obj4 = entityUser.get("loan_roles"), obj5 = entityUser.get("user_platform_roles");
        // String className = obj.getClass().getSimpleName();
        // System.out.println("Object is of type " + className);
        String groups = "", credit_roles = "", credpool_roles = "", loan_roles = "", user_platform_roles = "";
        if(obj1 instanceof LinkedHashMap){
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) obj1;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                groups = "\"" + entry.getKey().toString() + "\"" + "=>" + "\"" + entry.getValue().toString() + "\"";
            }
        }
        if(obj2 instanceof LinkedHashMap){
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) obj2;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                credit_roles = "\"" + entry.getKey().toString() + "\"" + "=>" + "\"" + entry.getValue().toString() + "\"";
            }
        }
        if(obj3 instanceof LinkedHashMap){
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) obj3;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                credpool_roles = "\"" + entry.getKey().toString() + "\"" + "=>" + "\"" + entry.getValue().toString() + "\"";
            }
        }
        if(obj4 instanceof LinkedHashMap){
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) obj4;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                loan_roles = "\"" + entry.getKey().toString() + "\"" + "=>" + "\"" + entry.getValue().toString() + "\"";
            }
        }
        if(obj5 instanceof LinkedHashMap){
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) obj5;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                user_platform_roles = "\"" + entry.getKey().toString() + "\"" + "=>" + "\"" + entry.getValue().toString() + "\"";
            }
        }
        return entityUserRepository.add((Long) entityUserRepository.count() + 1L, (Long) entityUser.get("entity_id"), (Long) entityUser.get("user_id"), groups, credit_roles, credpool_roles, loan_roles, user_platform_roles);
    }

    public EntityUsers getEntityUserById(Long entity_user_id) {
        return entityUserRepository.findById(entity_user_id).orElse(null);
    }

    public List<Map<String, Object>> getAllEntityUser() {
        System.out.println("getAllEntityUser() is called!!\n");
        return entityUserRepository.findAllEntityUser();
    }

    // The addUserRoles() method is used to add new records in the role_users table according to the roles users have.
    public void addUserRoles(Map<String, Object> entityUser) {
        Long entityId = Long.valueOf(entityUser.get("entity_id").toString());
        Long NumberOfProductsInGroup = 2L; // assumption

        // add the roles of the user in the roles_users table for all the products other than credit, credpool, loan and user_platform.
        // search for the groupId in the groups table and then search for the roleId in the roles table and then add the user_id and role_id in the roles_users table.
        Map<String, String> grp = new HashMap<String, String>();
        Object groupObj = entityUser.get("groups");
        if(groupObj instanceof LinkedHashMap){
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) groupObj;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                grp.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        for (String key : grp.keySet()) {
            String value = grp.get(key);
            for(Long productId = 1L; productId<=NumberOfProductsInGroup; productId++){
                List<Groups> entityPrdGrp = entityService.findGroupByProductIdAndEntityIdAndName(productId, entityId, key);
                Long grpId = entityPrdGrp.get(0).getId();
                List<Roles> entityPrdGrpRoles = entityService.findRoleByProductIdAndEntityIdAndGroupIdAndName(productId, entityId, grpId, value);
                Roles role = entityPrdGrpRoles.get(0);
                RolesUsers userRole = new RolesUsers();
                userRole.setUser(getUsersById(Long.valueOf(entityUser.get("user_id").toString())));
                userRole.setRole(role);
                rolesUsersRepository.save(userRole);
            }
        }
        
        // add the roles of the user in the roles_users table for the credit product.
        // search for the groupId in the groups table and then search for the roleId in the roles table and then add the user_id and role_id in the roles_users table.
        Map<String, String> credit_roles = new HashMap<String, String>();
        Object creditObj = entityUser.get("credit_roles");
        if(groupObj instanceof LinkedHashMap){
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) creditObj;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                credit_roles.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        for (String key : credit_roles.keySet()) {
            String value = credit_roles.get(key);
            List<Groups> entityPrdGrp = entityService.findGroupByProductIdAndEntityIdAndName(3L, entityId, key);
            Long grpId = entityPrdGrp.get(0).getId();
            List<Roles> entityPrdGrpRoles = entityService.findRoleByProductIdAndEntityIdAndGroupIdAndName(3L, entityId, grpId, value);
            Roles role = entityPrdGrpRoles.get(0);
            RolesUsers userRole = new RolesUsers();
            userRole.setUser(getUsersById(Long.valueOf(entityUser.get("user_id").toString())));
            userRole.setRole(role);
            rolesUsersRepository.save(userRole);
        }


        // add the roles of the user in the roles_users table for the credpool product.
        // search for the groupId in the groups table and then search for the roleId in the roles table and then add the user_id and role_id in the roles_users table.
        Map<String, String> credpool_roles = new HashMap<String, String>();
        Object credpoolObj = entityUser.get("credpool_roles");
        if(groupObj instanceof LinkedHashMap){
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) credpoolObj;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                credpool_roles.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        for (String key : credpool_roles.keySet()) {
            String value = credpool_roles.get(key);
            List<Groups> entityPrdGrp = entityService.findGroupByProductIdAndEntityIdAndName(4L, entityId, key);
            Long grpId = entityPrdGrp.get(0).getId();
            List<Roles> entityPrdGrpRoles = entityService.findRoleByProductIdAndEntityIdAndGroupIdAndName(4L, entityId, grpId, value);
            Roles role = entityPrdGrpRoles.get(0);
            RolesUsers userRole = new RolesUsers();
            userRole.setUser(getUsersById(Long.valueOf(entityUser.get("user_id").toString())));
            userRole.setRole(role);
            rolesUsersRepository.save(userRole);
        }


        // add the roles of the user in the roles_users table for the loan product.
        // search for the groupId in the groups table and then search for the roleId in the roles table and then add the user_id and role_id in the roles_users table.
        Map<String, String> loan_roles = new HashMap<String, String>();
        Object loanObj = entityUser.get("loan_roles");
        if(groupObj instanceof LinkedHashMap){
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) loanObj;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                loan_roles.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        for (String key : loan_roles.keySet()) {
            String value = loan_roles.get(key);
            List<Groups> entityPrdGrp = entityService.findGroupByProductIdAndEntityIdAndName(5L, entityId, key);
            Long grpId = entityPrdGrp.get(0).getId();
            List<Roles> entityPrdGrpRoles = entityService.findRoleByProductIdAndEntityIdAndGroupIdAndName(5L, entityId, grpId, value);
            Roles role = entityPrdGrpRoles.get(0);
            RolesUsers userRole = new RolesUsers();
            userRole.setUser(getUsersById(Long.valueOf(entityUser.get("user_id").toString())));
            userRole.setRole(role);
            rolesUsersRepository.save(userRole);
        }


        // add the roles of the user in the roles_users table for the user_platform product.
        // search for the groupId in the groups table and then search for the roleId in the roles table and then add the user_id and role_id in the roles_users table.
        Map<String, String> user_platform_roles = new HashMap<String, String>();
        Object user_platformObj = entityUser.get("user_platform_roles");
        if(groupObj instanceof LinkedHashMap){
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) user_platformObj;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                user_platform_roles.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        for (String key : user_platform_roles.keySet()) {
            String value = user_platform_roles.get(key);
            List<Groups> entityPrdGrp = entityService.findGroupByProductIdAndEntityIdAndName(6L, entityId, key);
            Long grpId = entityPrdGrp.get(0).getId();
            List<Roles> entityPrdGrpRoles = entityService.findRoleByProductIdAndEntityIdAndGroupIdAndName(6L, entityId, grpId, value);
            Roles role = entityPrdGrpRoles.get(0);
            RolesUsers userRole = new RolesUsers();
            userRole.setUser(getUsersById(Long.valueOf(entityUser.get("user_id").toString())));
            userRole.setRole(role);
            rolesUsersRepository.save(userRole);
        }

    }


    /*                                         -------------------------------------- Roles Users -----------------------------------------                                      */


    public List<Map<String, Object>> getUserRolesByUserId(Long user_id) {
        List<Map<String, Object>> error = new ArrayList<>();
        List<Map<String, Object>> ret = new ArrayList<>();
        List<Long> roleIds = rolesUsersRepository.findByUserId(user_id);
        for (Long roleId : roleIds) {
            Roles role = entityService.getRolesById(roleId);
            if(role==null){
                Map<String, Object> errorObj = new HashMap<>();
                errorObj.put("error", "role not found");
                error.add(errorObj);
                return error;
            }
            Map<String, Object> roleObj = new HashMap<>();
            roleObj.put("role_id", role.getId());
            roleObj.put("role_name", role.getName());
            roleObj.put("product", role.getProduct().getName());
            roleObj.put("entity", role.getEntity().getCompany_name());
            roleObj.put("group", role.getGroup().getName());
            roleObj.put("role_template", role.getRole_template().getName());
            roleObj.put("privileges", role.getPrivileges());
            ret.add(roleObj);
        }
        return ret;
    }

    public Map<String, Object> updateUserRolesByUserId(Map<String, Object> roles, Long user_id) {
        Map<String, Object> error = new HashMap<>();
        Map<String, Object> ret = new HashMap<>();
        User userObj = getUsersById(user_id);
        if(userObj==null){
            error.put("error", "user not found");
            return error;
        }
        rolesUsersRepository.deleteByUserId(user_id);
        List<Long> temp = new ArrayList<>();
        Object roleIds = roles.get("roles");
        if (roleIds instanceof ArrayList){
            ArrayList<?> rawList = (ArrayList<?>) roleIds;
            for (Object id : rawList) {
                Long roleId = Long.valueOf(id.toString());
                if(entityService.getRoleById(roleId)==null){
                    error.put("error", "Privilege with id "+roleId+" does not exist");
                    return error;
                }
                temp.add(Long.valueOf(id.toString()));
            }
        }
        for (Long roleId : temp) {
            Roles role = entityService.getRolesById(roleId);
            RolesUsers rolesUsers = new RolesUsers();
            rolesUsers.setUser(userObj);
            rolesUsers.setRole(role);
            rolesUsersRepository.save(rolesUsers);
        }
        ret.put("user_id", user_id);
        ret.put("name", userObj.getName());
        ret.put("email", userObj.getEmail());
        ret.put("roles", temp);
        return ret;
    }


    /*                                         -------------------------------------- User Privileges -----------------------------------------                                      */

    public Map<String, Object> getUserPrivilegesByUserId(Long user_id) {
        if(getUsersById(user_id)==null){
            return null;
        }
        Map<String, Object> privileges = new HashMap<String, Object>();
        User user = userRepository.findById(user_id).orElse(null);
        privileges.put("id", user_id);
        privileges.put("name", user.getName());
        privileges.put("email", user.getEmail());
        Map<String, List<String>> Permissions = new HashMap<String, List<String>>();
        List<Long> roles_id = rolesUsersRepository.findByUserId(user_id);
        for (Long role_id : roles_id) {
            List<Long> privilegeIds = entityService.findPrivilegesByRoleId(role_id).get(0);
            for (Long privilegeId : privilegeIds) {
                String privilegeName = productService.getPrivilegesById(privilegeId).getName();
                Long resource = productService.getPrivilegesById(privilegeId).getResource().getId();
                String resourceName = productService.getResourcesById(resource).getName();
                boolean found = false;
                for (String key : Permissions.keySet()){
                    if(key.equals(resourceName)){
                        List<String> privilegeList = Permissions.get(key);
                        privilegeList.add(privilegeName);
                        Permissions.put(key, privilegeList);
                        found = true;
                        break;
                    }
                }
                if(found == false){
                    List<String> privilegeList = new ArrayList<String>();
                    privilegeList.add(privilegeName);
                    Permissions.put(resourceName, privilegeList);
                }
            }
        }
        privileges.put("permissions", Permissions);
        return privileges;
    }

    public List<Map<String, Object>> getUserPrivilegesByProduct(Long user_id, Long product_id) {
        List<Map<String, Object>> error = new ArrayList<>();
        List<Map<String, Object>> ret = new ArrayList<>();
        User userObj = getUsersById(user_id);
        if(userObj==null){
            Map<String, Object> errorObj = new HashMap<>();
            errorObj.put("error", "user not found");
            error.add(errorObj);
            return error;
        }
        System.out.println("user id: "+user_id);
        Products productObj = productService.getProductById(product_id);
        if(productObj==null){
            Map<String, Object> errorObj = new HashMap<>();
            errorObj.put("error", "product not found");
            error.add(errorObj);
            return error;
        }
        System.out.println("product id: "+product_id);
        List<Long> roleIds = rolesUsersRepository.findByUserId(user_id);
        List<Long> isTaken = new ArrayList<>();
        for (Long roleId : roleIds) {
            Roles role = entityService.getRolesById(roleId);
            if(role==null){
                Map<String, Object> errorObj = new HashMap<>();
                errorObj.put("error", "role not found");
                error.add(errorObj);
                return error;
            }
            System.out.println("role id: "+roleId);
            if(role.getProduct().getId()==product_id){
                List<Long> privilegeIds = role.getPrivileges();
                for (Long privilegeId : privilegeIds){
                    if(!isTaken.contains(privilegeId)){
                        Privileges privilege = productService.getPrivilegeById(privilegeId);
                        if(privilege==null){
                            Map<String, Object> errorObj = new HashMap<>();
                            errorObj.put("error", "privilege not found");
                            error.add(errorObj);
                            return error;
                        }
                        System.out.println("privilege id: "+privilegeId);
                        Map<String, Object> privilegeObj = new HashMap<>();
                        privilegeObj.put("privilege_id", privilege.getId());
                        privilegeObj.put("privilege_name", 
                        privilege.getName());
                        privilegeObj.put("product", privilege.getProduct().getName());
                        privilegeObj.put("resource", privilege.getResource().getName());
                        ret.add(privilegeObj);
                        isTaken.add(privilegeId);
                    }
                }
            }
        }
        return ret;
    }

    public List<Map<String, Object>> getUserPrivilegesByProductAndEntityAndGroup(Long user_id, Long product_id, Long entity_id, Long group_id) {
        List<Map<String, Object>> error = new ArrayList<>();
        List<Map<String, Object>> ret = new ArrayList<>();
        User userObj = getUsersById(user_id);
        if(userObj==null){
            Map<String, Object> errorObj = new HashMap<>();
            errorObj.put("error", "user not found");
            error.add(errorObj);
            return error;
        }
        Products productObj = productService.getProductById(product_id);
        if(productObj==null){
            Map<String, Object> errorObj = new HashMap<>();
            errorObj.put("error", "product not found");
            error.add(errorObj);
            return error;
        }
        Entities entityObj = entityService.getEntityById(entity_id);
        if(entityObj==null){
            Map<String, Object> errorObj = new HashMap<>();
            errorObj.put("error", "entity not found");
            error.add(errorObj);
            return error;
        }
        Groups groupObj = entityService.getGroupById(group_id);
        if(groupObj==null){
            Map<String, Object> errorObj = new HashMap<>();
            errorObj.put("error", "group not found");
            error.add(errorObj);
            return error;
        }
        List<Long> roleIds = rolesUsersRepository.findByUserId(user_id);
        List<Long> isTaken = new ArrayList<>();
        for (Long roleId : roleIds) {
            Roles role = entityService.getRolesById(roleId);
            if(role==null){
                Map<String, Object> errorObj = new HashMap<>();
                errorObj.put("error", "role not found");
                error.add(errorObj);
                return error;
            }
            if(role.getProduct().getId()==product_id && role.getEntity().getId()==entity_id && role.getGroup().getId()==group_id){
                List<Long> privilegeIds = role.getPrivileges();
                for (Long privilegeId : privilegeIds){
                    if(!isTaken.contains(privilegeId)){
                        Privileges privilege = productService.getPrivilegeById(privilegeId);
                        if(privilege==null){
                            Map<String, Object> errorObj = new HashMap<>();
                            errorObj.put("error", "privilege not found");
                            error.add(errorObj);
                            return error;
                        }
                        Map<String, Object> privilegeObj = new HashMap<>();
                        privilegeObj.put("privilege_id", privilege.getId());
                        privilegeObj.put("privilege_name", 
                        privilege.getName());
                        privilegeObj.put("product", privilege.getProduct().getName());
                        privilegeObj.put("resource", privilege.getResource().getName());
                        ret.add(privilegeObj);
                        isTaken.add(privilegeId);
                    }
                }
            }
        }
        return ret;
    }

    public List<Map<String, Object>> getUserPrivilegesByProductAndEntity(Long user_id, Long product_id, Long entity_id) {
        List<Map<String, Object>> error = new ArrayList<>();
        List<Map<String, Object>> ret = new ArrayList<>();
        User userObj = getUsersById(user_id);
        if(userObj==null){
            Map<String, Object> errorObj = new HashMap<>();
            errorObj.put("error", "user not found");
            error.add(errorObj);
            return error;
        }
        Products productObj = productService.getProductById(product_id);
        if(productObj==null){
            Map<String, Object> errorObj = new HashMap<>();
            errorObj.put("error", "product not found");
            error.add(errorObj);
            return error;
        }
        Entities entityObj = entityService.getEntityById(entity_id);
        if(entityObj==null){
            Map<String, Object> errorObj = new HashMap<>();
            errorObj.put("error", "entity not found");
            error.add(errorObj);
            return error;
        }
        List<Long> roleIds = rolesUsersRepository.findByUserId(user_id);
        List<Long> isTaken = new ArrayList<>();
        for (Long roleId : roleIds) {
            Roles role = entityService.getRolesById(roleId);
            if(role==null){
                Map<String, Object> errorObj = new HashMap<>();
                errorObj.put("error", "role not found");
                error.add(errorObj);
                return error;
            }
            if(role.getProduct().getId()==product_id && role.getEntity().getId()==entity_id){
                List<Long> privilegeIds = role.getPrivileges();
                for (Long privilegeId : privilegeIds){
                    if(!isTaken.contains(privilegeId)){
                        Privileges privilege = productService.getPrivilegeById(privilegeId);
                        if(privilege==null){
                            Map<String, Object> errorObj = new HashMap<>();
                            errorObj.put("error", "privilege not found");
                            error.add(errorObj);
                            return error;
                        }
                        Map<String, Object> privilegeObj = new HashMap<>();
                        privilegeObj.put("privilege_id", privilege.getId());
                        privilegeObj.put("privilege_name", 
                        privilege.getName());
                        privilegeObj.put("product", privilege.getProduct().getName());
                        privilegeObj.put("resource", privilege.getResource().getName());
                        ret.add(privilegeObj);
                        isTaken.add(privilegeId);
                    }
                }
            }
        }
        return ret;
    }

}