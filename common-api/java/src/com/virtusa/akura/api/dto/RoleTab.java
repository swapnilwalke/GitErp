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
 * This class represents all properties of RoleTab domain object.
 * 
 * @author Virtusa Corporation
 */
public class RoleTab extends BaseDomain {
    
    /** attribute for holding string. */
    private static final String TAB_ID = ", tabId=";
    
    /** attribute for holding string. */
    private static final String ROLE_ID = ", roleID=";
    
    /** attribute for holding string. */
    private static final String ROLE_TAB_ID = "roleTabId=";
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the ID of a RoleTab.
     */
    private int roleTabId;
    
    /**
     * Represents the related user role of a RoleTab.
     */
    private UserRole role;
    
    /**
     * Represents the related tab object of a RoleTab.
     */
    private Tab tab;
    
    /**
     * Default constructor.
     */
    public RoleTab() {

    }
    
    /**
     * Returns the ID of the RoleTab object.
     * 
     * @return - the ID of the RoleTab.
     */
    public int getRoleTabId() {

        return roleTabId;
    }
    
    /**
     * Sets the ID for this RoleTab with a given key.
     * 
     * @param roleTabIdObj - the ID of the Page.
     */
    public void setRoleTabId(int roleTabIdObj) {

        this.roleTabId = roleTabIdObj;
    }
    
    /**
     * Returns the related user role of the RoleTab object.
     * 
     * @return - the related user role of the RoleTab.
     */
    public UserRole getRole() {

        return role;
    }
    
    /**
     * Sets the related user role for this RoleTab with a given key.
     * 
     * @param roleObj - the related user role of the Page.
     */
    public void setRole(UserRole roleObj) {

        this.role = roleObj;
    }
    
    /**
     * Returns the related tab of the RoleTab object.
     * 
     * @return - the related tab of the RoleTab.
     */
    public Tab getTab() {

        return tab;
    }
    
    /**
     * Sets the related tab for this RoleTab with a given key.
     * 
     * @param tabObj - the related tab of the Page.
     */
    public void setTab(Tab tabObj) {

        this.tab = tabObj;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {

        return ROLE_TAB_ID + roleTabId + ROLE_ID + role.getUserRoleId() + TAB_ID + tab.getTabId();
    }
}
