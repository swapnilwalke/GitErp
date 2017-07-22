/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
 *
 * Copyright (C) 2012 Virtusa Corporation.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.virtusa.akura.api.dto;

/**
 * This class represents all properties of RolePrivilege domain object.
 * 
 * @author Virtusa Corporation
 */
public class RolePrivilege extends BaseDomain {
    
    /** attribute for holding string. */
    private static final String PRIVILEGE_ID = ", privilegeId=";
    
    /** attribute for holding string. */
    private static final String ROLE_ID = ", roleId=";
    
    /** attribute for holding string. */
    private static final String ROLE_PRIVILEGE_ID = "rolePrivilegeId=";
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the ID of a RolePrivilege.
     */
    private int rolePrivilegeId;
    
    /**
     * Represents the related user role of a RolePrivilege.
     */
    private UserRole role;
    
    /**
     * Represents the related privilege object of a RolePrivilege.
     */
    private Privilege privilege;
    
    /**
     * Default constructor.
     */
    public RolePrivilege() {

    }
    
    /**
     * Returns the ID of the RolePrivilege object.
     * 
     * @return - the ID of the RolePrivilege.
     */
    public int getRolePrivilegeId() {

        return rolePrivilegeId;
    }
    
    /**
     * Sets the ID for this RolePrivilege with a given key.
     * 
     * @param rolePrivilegeIdObj - the ID of the RolePrivilege.
     */
    public void setRolePrivilegeId(int rolePrivilegeIdObj) {

        this.rolePrivilegeId = rolePrivilegeIdObj;
    }
    
    /**
     * Returns the related role of the RolePrivilege object.
     * 
     * @return - the related role of the RolePrivilege.
     */
    public UserRole getRole() {

        return role;
    }
    
    /**
     * Sets the related user role for this RolePrivilege.
     * 
     * @param roleobj - the related user role of the RolePrivilege.
     */
    public void setRole(UserRole roleobj) {

        this.role = roleobj;
    }
    
    /**
     * Returns the related privilege of the RolePrivilege object.
     * 
     * @return - the related privilege of the RolePrivilege.
     */
    public Privilege getPrivilege() {

        return privilege;
    }
    
    /**
     * Sets the related privilege object for this RolePrivilege.
     * 
     * @param privilegeObj - the related privilege object of the RolePrivilege.
     */
    public void setPrivilege(Privilege privilegeObj) {

        this.privilege = privilegeObj;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {

        return ROLE_PRIVILEGE_ID + rolePrivilegeId + ROLE_ID + role.getUserRoleId() + PRIVILEGE_ID
                + privilege.getPrivilegeId();
    }
    
}
