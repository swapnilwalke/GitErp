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

package com.virtusa.akura.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;

import com.virtusa.akura.api.dto.Tab;

/**
 * This class is responsible for configuring the header tab for the application. <br>
 * It will reads the header.properties files which defines the main tabs, sub tabs and the url for the defined
 * tabs.
 * 
 * @author Virtusa Corporation
 */
public final class HeaderTabConfigurator {
    
    /** Holds date formatter. */
    private static SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
    
    /**
     * The default constructor.
     */
    private HeaderTabConfigurator() {

    }
    
    /**
     * Get date when the jsp calls the EL function.
     * 
     * @return the Date string.
     */
    public static String getDate() {

        return formatter.format(new Date());
    }
    
    /**
     * Get List of properties according to the given attribute.
     * 
     * @param bundle the property bundle
     * @param tab the tab category name
     * @return the Date string.
     */
    public static String[] getTabContent(PropertyResourceBundle bundle, String tab) {

        return bundle.getString(tab + ".subTabs").split(",");
    }
    
    /**
     * Get List of properties according to the given attribute.
     * 
     * @param parentTabId the parent tab ID.
     * @param roleTabMap the list of accessible tabs current user role.
     * @return the Date string.
     */
    public static List<Tab> getTabs(Map<Integer, Tab> roleTabMap, String parentTabId) {

        List<Tab> tabList = null;
        int parentTabIdInt = Integer.parseInt(parentTabId);
        
        if (parentTabIdInt != 0) {
            
            if (!roleTabMap.isEmpty() && roleTabMap.get(parentTabIdInt) != null
                    && roleTabMap.get(parentTabIdInt).getTabs().size() != 0) {
                
                tabList = new ArrayList<Tab>(roleTabMap.get(parentTabIdInt).getTabs());
            } else {
                tabList = new ArrayList<Tab>();
                tabList.add(roleTabMap.get(parentTabIdInt));
            }
            
        } else {
            tabList = new ArrayList<Tab>();
            for (int tabId : roleTabMap.keySet()) {
                if (roleTabMap.get(tabId).getParentTab() == null) {
                    tabList.add(roleTabMap.get(tabId));
                }
            }
        }
        Collections.sort(tabList);
        return tabList;
    }
    
}
