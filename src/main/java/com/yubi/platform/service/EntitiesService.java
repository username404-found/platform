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

import com.yubi.platform.element.Entities;
import com.yubi.platform.element.Groups;
import com.yubi.platform.element.Products;
import com.yubi.platform.element.RoleTemplates;
import com.yubi.platform.element.Roles;
import com.yubi.platform.repository.GroupsRepository;
import com.yubi.platform.repository.RolesRepository;
import com.yubi.platform.repository.RolesUsersRepository;
import com.yubi.platform.repository.EntitiesRepository;


// The @Service annotation is used to define the service class.
@Service
public class EntitiesService {
    
    // The @Autowired annotation is used to inject the dependency of the service class.
    @Autowired
    private ProductService productService;

    @Autowired
    private EntitiesRepository entityRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private RolesUsersRepository rolesUsersRepository;


    /*                                         -------------------------------------- Entities -----------------------------------------                                      */


    public List<Map<String, Object>> getAllEntity() {
        return entityRepository.findAllEntities();
    }

    public Entities getEntityById(Long entity_id) {
        return entityRepository.findById(entity_id).orElse(null);
    }

    // The addEntity() method is used to add an entity in the entities table.
    // It also finds the default product_groups and role_template for every product and adds these records in the groups table and roles table for this entity.
    public Map<String, Object> addEntity(Map<String, Object> entity) {
        Long entityId = entityRepository.count() + 1L;
        Map<String, Object> error = new HashMap<>();
        Map<String, Object> newEntity = new HashMap<>();
        if(entity.get("parent_id") == null){
            newEntity = entityRepository.add(entityId, (String) entity.get("company_name"), (String) entity.get("company_id"), null);
        }
        else if(entityId==Long.valueOf(entity.get("parent_id").toString())){
            error.put("error", "Parent id cannot be same as entity id");
            return error;
        }
        else{
            newEntity = entityRepository.add(entityId, (String) entity.get("company_name"), (String) entity.get("company_id"), Long.valueOf((String) entity.get("parent_id").toString()));
        }

        // find the default product_groups for every product and add in the groups table for this entity.
        List<Map<String, Object>> productGroups = productService.getAllProductGroup();
        for(Map<String, Object> pg : productGroups) {
            Map<String, Object> group = new HashMap<>();
            group.put("entity_id", entityId);
            group.put("name", pg.get("name"));
            group.put("product_id", pg.get("product_id"));
            addGroups(group);
        }

        // find the default role_template for every product and add in the roles table for this entity.
        List<Map<String, Object>> roleTemplates = productService.getAllRoleTemplates();
        for(Map<String, Object> rt : roleTemplates) {
            Map<String, Object> roles = new HashMap<>();
            roles.put("entity_id", entityId);
            roles.put("name", rt.get("name"));
            roles.put("product_id", rt.get("product_id"));
            roles.put("role_template_id", rt.get("id"));
            Long prdGrpId = Long.valueOf(rt.get("product_group_id").toString());
            String prdGrpName = productService.getProductGroupById(prdGrpId).getName();
            Groups grp = findGroupByProductIdAndEntityIdAndName(Long.valueOf(rt.get("product_id").toString()), entityId, prdGrpName).get(0);
            roles.put("group_id", grp.getId());
            Object obj = rt.get("privileges");
            List<Long> privileges = new ArrayList<>();
            if(obj instanceof Long[]){
                Long[] rawList = (Long[]) obj;
                for(Long o : rawList){
                    privileges.add(o);
                }
            }
            roles.put("privileges", privileges);
            addRoles(roles);
        }

        return newEntity;
    }

    public Map<String, Object> getEntitiesById(long entityId) {
        return entityRepository.findEntity(entityId);
    }

    public Map<String, Object> updateEntityById(Map<String, Object> entity, long entityId) {
        Map<String, Object> error = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        Entities e = getEntityById(entityId);
        if(e==null){
            error.put("error", "Entity with id "+entityId+" does not exist");
            return error;
        }
        if(entity.get("parent_id")==null){
            e.setParent(null);
            entityRepository.save(e);
            Entities newEntity = getEntityById(entityId);
            result.put("id", newEntity.getId());
            result.put("company_name", newEntity.getCompany_name());
            result.put("company_id", newEntity.getCompany_id());
            result.put("parent_id", newEntity.getParent());
            return result;
        }
        Entities Parent = getEntityById(Long.valueOf(entity.get("parent_id").toString()));
        if(Parent==null){
            error.put("error", "Parent with id "+entity.get("parent_id")+" does not exist");
            return error;
        }
        e.setParent(Parent);
        entityRepository.save(e);
        Entities newEntity = getEntityById(entityId);
        result.put("id", newEntity.getId());
        result.put("company_name", newEntity.getCompany_name());
        result.put("company_id", newEntity.getCompany_id());
        result.put("parent_id", newEntity.getParent().getId());
        return result;
    }

    public List<Map<String, Object>> getSubEntities(long entityId) {
        if(getEntityById(entityId)==null){
            throw new RuntimeException("Entity does not exist");
        }
        List<Entities> ret = entityRepository.findSubEntities(entityId);
        List<Map<String, Object>> ret2 = new ArrayList<>(0);
        for(Entities e : ret){
            Map<String, Object> ret3 = new HashMap<>(0, entityId);
            ret3.put("id", e.getId());
            ret3.put("company_name", e.getCompany_name());
            ret3.put("company_id", e.getCompany_id());
            ret3.put("parent_id", e.getParent().getId());
            ret2.add(ret3);
        }
        return ret2;
    }


    /*                                         -------------------------------------- Groups -----------------------------------------                                      */


    public List<Map<String, Object>> getAllGroupsByEntity(Long entity_id) {
        return groupsRepository.findAllGroups(entity_id);
    }

    public Groups getGroupById(Long group_id) {
        return groupsRepository.findById(group_id).orElse(null);
    }

    public Map<String, Object> addGroups(Map<String, Object> group) {
        return groupsRepository.add((Long) groupsRepository.count() + 1L, (String) group.get("name"), Long.valueOf(group.get("product_id").toString()), Long.valueOf(group.get("entity_id").toString()));
    }

    public List<Map<String, Object>> getAllGroupsByEntityAndProduct(long entityId, long productId) {
        return groupsRepository.findAllGroupsByEntityAndProduct(entityId, productId);
    }

    public Map<String, Object> updateGroupById(Map<String, Object> newGroup, long groupId) {
        Map<String, Object> error = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        Groups g = getGroupById(groupId);
        if(g==null){
            error.put("error", "Group with id "+groupId+" does not exist");
            return error;
        }
        g.setEntity(getEntityById(Long.valueOf(newGroup.get("entity_id").toString())));
        g.setProduct(productService.getProductById(Long.valueOf(newGroup.get("product_id").toString())));
        g.setName((String) newGroup.get("name"));
        groupsRepository.save(g);
        Groups newGroup2 = getGroupById(groupId);
        result.put("id", newGroup2.getId());
        result.put("name", newGroup2.getName());
        result.put("product_id", newGroup2.getProduct().getId());
        result.put("entity_id", newGroup2.getEntity().getId());
        return result;
    }

    public List<Groups> findGroupByProductIdAndEntityIdAndName(Long productId, Long entityId, String name) {
        return groupsRepository.findGroupByProductIdAndEntityIdAndName(productId, entityId, name);
    }


    /*                                         -------------------------------------- Roles -----------------------------------------                                      */


    public List<Map<String, Object>> getAllRoles() {
        return rolesRepository.findAllRoles();
    }

    public Roles getRolesById(Long role_id) {
        return rolesRepository.findById(role_id).orElse(null);
    }

    public Map<String, Object> addRoles(Map<String, Object> roles) {

        Map<String, Object> error = new HashMap<>();
        List<Long> privileges = new ArrayList<>();
        Object obj = roles.get("privileges");
        // String className = obj.getClass().getSimpleName();
        // System.out.println("Object is of type " + className);
        if (obj instanceof ArrayList){
            ArrayList<?> rawList = (ArrayList<?>) obj;
            for (Object id : rawList) {
                Long privilegesId = Long.valueOf(id.toString());
                if(productService.getPrivilegesById(privilegesId)==null){
                    error.put("error", "Privilege with id "+privilegesId+" does not exist");
                    return error;
                }
                privileges.add(Long.valueOf(id.toString()));
            }
        }
        Entities entity = entityRepository.findById(Long.valueOf(roles.get("entity_id").toString())).get();
        Groups group = groupsRepository.findById(Long.valueOf(roles.get("group_id").toString())).get();
        Products product = productService.getProductById(Long.valueOf(roles.get("product_id").toString()));
        RoleTemplates roleTemplate = productService.getRoleTemplateById(Long.valueOf(roles.get("role_template_id").toString()));
        Roles newRole = rolesRepository.save(new Roles((String) roles.get("name"), group, entity, product, roleTemplate, privileges));

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", newRole.getId());
        result.put("name", newRole.getName());
        result.put("product_id", newRole.getProduct().getId());
        result.put("entity_id", newRole.getEntity().getId());
        result.put("group_id", newRole.getGroup().getId());
        result.put("role_template_id", newRole.getRole_template().getId());
        result.put("privileges", newRole.getPrivileges());
        return result;

    }

    public Map<String, Object> getRoleById(long role_id) {
        Roles role = getRolesById(role_id);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        if(role==null){
            error.put("error", "Role with id "+role_id+" does not exist");
            return error;
        }
        result.put("id", role.getId());
        result.put("name", role.getName());
        result.put("product_id", role.getProduct().getId());
        result.put("entity_id", role.getEntity().getId());
        result.put("group_id", role.getGroup().getId());
        result.put("role_template_id", role.getRole_template().getId());
        result.put("privileges", role.getPrivileges());
        return result;
    }

    public List<Map<String, Object>> getAllRolesByEntityAndGroupAndProduct(long entityId, long groupId,
            long productId) {
        return rolesRepository.findAllRolesByEntityAndGroupAndProduct(entityId, groupId, productId);
    }

    public Map<String, Object> addRolesByEntityAndGroupAndProduct(Map<String, Object> entityProductGroupRoles,
            long entityId, long groupId, long productId) {
                String name = (String) entityProductGroupRoles.get("name");
                Entities entity = getEntityById(entityId);
                Groups group = getGroupById(groupId);
                Products product = productService.getProductById(productId);
                Long role_template_id = Long.valueOf(entityProductGroupRoles.get("role_template_id").toString());
                RoleTemplates role_template = productService.getRoleTemplateById(role_template_id);
                Map<String, Object> error = new HashMap<>();
                List<Long> privileges = new ArrayList<>();
                Object obj = entityProductGroupRoles.get("privileges");
                if (obj instanceof ArrayList){
                    ArrayList<?> rawList = (ArrayList<?>) obj;
                    for (Object pId : rawList) {
                        Long privilegesId = Long.valueOf(pId.toString());
                        if(productService.getPrivilegesById(privilegesId)==null){
                            error.put("error", "Privilege with id "+privilegesId+" does not exist");
                            return error;
                        }
                        privileges.add(Long.valueOf(pId.toString()));
                    }
                }
            Roles newRole = rolesRepository.save(new Roles(name, group, entity, product, role_template, privileges));
            Map<String, Object> result = new HashMap<>();
            result.put("id", newRole.getId());
            result.put("name", newRole.getName());
            result.put("product_id", newRole.getProduct().getId());
            result.put("entity_id", newRole.getEntity().getId());
            result.put("group_id", newRole.getGroup().getId());
            result.put("role_template_id", newRole.getRole_template().getId());
            result.put("privileges", newRole.getPrivileges());
            return result;
    }

    public Map<String, Object> updateRoleById(Map<String, Object> newRole, long roleId) {
        Map<String, Object> error = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        Roles r = getRolesById(roleId);
        if(r==null){
            error.put("error", "Role with id "+roleId+" does not exist");
            return error;
        }
        Object obj = newRole.get("privileges");
        List<Long> privileges = new ArrayList<>();
        if (obj instanceof ArrayList){
            ArrayList<?> rawList = (ArrayList<?>) obj;
            for (Object pId : rawList) {
                Long privilegesId = Long.valueOf(pId.toString());
                if(productService.getPrivilegesById(privilegesId)==null){
                    error.put("error", "Privilege with id "+privilegesId+" does not exist");
                    return error;
                }
                privileges.add(Long.valueOf(pId.toString()));
            }
        }
        r.setPrivileges(privileges);
        rolesRepository.save(r);
        Roles newRole2 = getRolesById(roleId);
        result.put("id", newRole2.getId());
        result.put("name", newRole2.getName());
        result.put("product_id", newRole2.getProduct().getId());
        result.put("entity_id", newRole2.getEntity().getId());
        result.put("group_id", newRole2.getGroup().getId());
        result.put("role_template_id", newRole2.getRole_template().getId());
        result.put("privileges", newRole2.getPrivileges());
        return result;
    }

    public Map<String, Object> deleteRoleById(long roleId) {
        Map<String, Object> error = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        Roles r = getRolesById(roleId);
        if(r==null){
            error.put("error", "Role with id "+roleId+" does not exist");
            return error;
        }
        List<Map<String, Object>> roles_users = rolesUsersRepository.findByRoleId(roleId);
        if(roles_users.size()>0){
            error.put("error", "Role with id "+roleId+" cannot be deleted because it is assigned to a user");
            return error;
        }
        rolesRepository.deleteById(roleId);
        result.put("message", "Role with id "+roleId+" deleted successfully");
        return result;
    }

    public List<Roles> findRoleByProductIdAndEntityIdAndGroupIdAndName(Long productId, Long entityId, Long groupId, String name) {
        return rolesRepository.findRoleByProductIdAndEntityIdAndGroupIdAndName(productId, entityId, groupId, name);
    }

    public List<List<Long>> findPrivilegesByRoleId(Long role_id) {
        return rolesRepository.findByRoleId(role_id);
    }

}
