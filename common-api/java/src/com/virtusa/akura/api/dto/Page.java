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
 * This class represents all properties of Page domain object.
 * 
 * @author Virtusa Corporation
 */
public class Page extends BaseDomain implements Comparable<Page>{
    
    /** attribute for holding string. */
    private static final String URL_STR = ", url=";
    
    /** attribute for holding string. */
    private static final String INDEX = ", index=";
    
    /** attribute for holding string. */
    private static final String NAME_STR = ", name=";
    
    /** attribute for holding string. */
    private static final String PAGE_ID = "pageId=";
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the ID of a Page.
     */
    private int pageId;
    
    /**
     * Represents the name of a Page.
     */
    private String name;
    
    /**
     * Represents the index of Page.
     */
    private int index;
    
    /**
     * Represents the name of an related tab.
     */
    private Tab tab;
    
    /**
     * Represents the URL of a Page.
     */
    private String url;
    
    /**
     * Represents the privileges of this Page.
     */
    private Set<Privilege> privileges;
    
    /**
     * Default constructor.
     */
    public Page() {

    }
    
    /**
     * Returns the ID of the Page object.
     * 
     * @return - the ID of the Page.
     */
    public int getPageId() {

        return pageId;
    }
    
    /**
     * Sets the ID for this Page with a given key.
     * 
     * @param pageIdObj - the ID of the Page.
     */
    public void setPageId(int pageIdObj) {

        this.pageId = pageIdObj;
    }
    
    /**
     * Returns the index of the Page object.
     * 
     * @return - the index of the Tab.
     */
    public int getIndex() {

        return index;
    }
    
    /**
     * Sets the index for this Page with a given key.
     * 
     * @param indexObj - the index of the Tab.
     */
    public void setIndex(int indexObj) {

        this.index = indexObj;
    }
    
    /**
     * Returns the name of the Page object.
     * 
     * @return - the name of the Page.
     */
    public String getName() {

        return name;
    }
    
    /**
     * Sets the name for this Page with a given name.
     * 
     * @param nameObj - the name of the Page.
     */
    public void setName(String nameObj) {

        this.name = nameObj;
    }
    
    /**
     * Returns the URL of the Page object.
     * 
     * @return - the URL of the Page.
     */
    public String getUrl() {

        return url;
    }
    
    /**
     * Sets the URL for this Page with a given name.
     * 
     * @param urlObj - the URL of the Page.
     */
    public void setUrl(String urlObj) {

        this.url = urlObj;
    }
    
    /**
     * Returns the related tab of the Page object.
     * 
     * @return - the related tab of the Page.
     */
    public Tab getTab() {

        return tab;
    }
    
    /**
     * Sets the related tab for this Page.
     * 
     * @param tabObj - the related tab of the Page.
     */
    public void setTab(Tab tabObj) {

        this.tab = tabObj;
    }
    
    /**
     * Returns the related privileges of the Page object.
     * 
     * @return - the related privileges of the Page.
     */
    public Set<Privilege> getPrivileges() {

        return privileges;
    }
    
    /**
     * Sets the related privileges for this Page.
     * 
     * @param privilegeSet - the related privileges of the Page.
     */
    public void setPrivileges(Set<Privilege> privilegeSet) {

        this.privileges = privilegeSet;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {

        return PAGE_ID + pageId + NAME_STR + name + URL_STR + url+ INDEX + index;
    }
    
    /**
     * Returns a string representation of the object.
     * @param o - get the object to compare.
     * @return - result .
     */
    @Override
    public int compareTo(Page o) {

        return (this.getIndex() < o.getIndex() ? -1 : (this.getIndex() == o.getIndex() ? 0 : 1));
    }
}
