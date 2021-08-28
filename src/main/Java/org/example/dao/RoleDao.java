package org.example.dao;

import org.example.model.Role;

import java.util.List;


public interface RoleDao {

    Role findRoleByRoleName(String role);

    List<Role> findRoleByUsername(String username);

    Role saveRole(Role role);

    Role updateRole(Role updatedRole);

    List<Role> allRoles();

    void deleteRole(String role);
}
