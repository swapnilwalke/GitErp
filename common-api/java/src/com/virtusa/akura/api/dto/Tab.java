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

import java.util.Set;

/**
 * This class represents all properties of Tab domain object.
 * 
 * @author Virtusa Corporation
 */
public class Tab extends BaseDomain implements Comparable<Tab>{
    
    /** attribute for holding string. */
    private static final String INDEX = ", index=";

    /** attribute for holding string. */
    private static final String MODULE_STR = ", module=";
    
    /** attribute for holding string. */
    private static final String NAME_STR = ", name=";
    
    /** attribute for holding string. */
    private static final String TAB_ID = "tabId=";
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the ID of Tab.
     */
    private int tabId;
    
    /**
     * Represents the name of Tab.
     */
    private String name;
    
    /**
     * Represents the module of Tab.
     */
    private Module module;
    
    /**
     * Represents the parentTab of Tab.
     */
    private Tab parentTab;
    
    /**
     * Represents the index of Tab.
     */
    private int index;
    
    /**
     * Represents the pages of this Tab.
     */
    private Set<Page> pages;
    
    /**
     * Represents the tabs of this Tab.
     */
    private Set<Tab> tabs;
    
    /**
     * Represents the RoleTabs of this Tab.
     */
    private Set<RoleTab> roleTabs;
    
    /**
     * Default constructor.
     */
    public Tab() {

    }
    
    /**
     * Returns the ID of the Tab object.
     * 
     * @return - the ID of the Tab.
     */
    public int getTabId() {

        return tabId;
    }
    
    /**
     * Sets the ID for this Tab with a given key.
     * 
     * @param tabIdObj - the ID of the Tab.
     */
    public void setTabId(int tabIdObj) {

        this.tabId = tabIdObj;
    }
    
    /**
     * Returns the index of the Tab object.
     * 
     * @return - the index of the Tab.
     */
    public int getIndex() {

        return index;
    }
    
    /**
     * Sets the index for this Tab with a given key.
     * 
     * @param indexObj - the index of the Tab.
     */
    public void setIndex(int indexObj) {

        this.index = indexObj;
    }
    
    /**
     * Returns the Module of the Tab object.
     * 
     * @return - the Module of the Tab.
     */
    public Module getModule() {

        return module;
    }
    
    /**
     * Sets the Module for this Tab.
     * 
     * @param moduleObj - the module of the Tab.
     */
    public void setModule(Module moduleObj) {

        this.module = moduleObj;
    }
    
    /**
     * Returns the name of the Tab object.
     * 
     * @return - the name of the Tab.
     */
    public String getName() {

        return name;
    }
    
    /**
     * Sets the name for this Tab.
     * 
     * @param nameObj - the name of the Tab.
     */
    public void setName(String nameObj) {

        this.name = nameObj;
    }
    
    /**
     * Returns the parentTab of the Tab object.
     * 
     * @return - the parentTab of the Tab.
     */
    public Tab getParentTab() {

        return parentTab;
    }
    
    /**
     * Sets the parentTab for this Tab.
     * 
     * @param parentTabObj - the parentTab of the Tab.
     */
    public void setParentTab(Tab parentTabObj) {

        this.parentTab = parentTabObj;
    }
    
    /**
     * Returns the pages of the Tab object.
     * 
     * @return - the pages of the Tab.
     */
    public Set<Page> getPages() {

        return pages;
    }
    
    /**
     * Returns the tabs of the Tab object.
     * 
     * @return - the tabs of the Tab.
     */
    public Set<Tab> getTabs() {

        return tabs;
    }
    
    /**
     * Sets the pages of this Tab.
     * 
     * @param pageSet - the pages of the Tab.
     */
    public void setPages(Set<Page> pageSet) {

        this.pages = pageSet;
    }
    
    /**
     * Sets the tabs of this Tab.
     * 
     * @param tabSet - the tabs of the Tab.
     */
    public void setTabs(Set<Tab> tabSet) {

        this.tabs = tabSet;
    }
    
    /**
     * Returns the RoleTabs of the Tab object.
     * 
     * @return - the RoleTabs of the Tab.
     */
    public Set<RoleTab> getRoleTabs() {

        return roleTabs;
    }
    
    /**
     * Sets the RoleTabs of this Tab.
     * 
     * @param roleTabSet - the RoleTabs of the Tab.
     */
    public void setRoleTabs(Set<RoleTab> roleTabSet) {

        this.roleTabs = roleTabSet;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {

        return TAB_ID + tabId + NAME_STR + name + MODULE_STR + module + INDEX + index;
    }
    /**
     * Returns a string representation of the object.
     * @param o - get the object to compare.
     * @return - result .
     */
    @Override
    public int compareTo(Tab o) {

        return (this.getIndex() < o.getIndex() ? -1 : (this.getIndex() == o.getIndex() ? 0 : 1));
    }
}
