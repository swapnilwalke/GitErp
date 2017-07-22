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

import java.io.Serializable;

/**
 * This class is to associates all the properties of Staff Service Category domain object.
 * 
 * @author Virtusa Corporation
 */

public class StaffServiceCategory extends BaseDomain implements Serializable {
    
    /** String attribute for name of the staff service category. */
    private static final String NAME_VALUE = ", staffServiceCatogeryName=";
    
    /** String attribute for staff service category id. */
    private static final String STAFF_SERVICE_CATEGORY_ID = "StaffServiceCategory [serviceId=";
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * property serviceId type integer.
     */
    private int serviceId;
    
    /**
     * property description type String.
     */
    private String description;
    
    /**
     * default constructor to create StaffServiceCategory object.
     */
    public StaffServiceCategory() {

    }
    
    /**
     * Gets the service id.
     * 
     * @return the service id
     */
    public int getServiceId() {

        return serviceId;
    }
    
    /**
     * sets the Id of the staffServiceCategory.
     * 
     * @param staffServiceID the serviceId to set.
     */
    public void setServiceId(int staffServiceID) {

        serviceId = staffServiceID;
    }
    
    /**
     * gets the description of the staffServiceCategory.
     * 
     * @return the description.
     */
    public String getDescription() {

        return description;
    }
    
    /**
     * sets the description of the staffServiceCategory.
     * 
     * @param strDescription the staffServiceCategory description to set.
     */
    public void setDescription(String strDescription) {

        description = strDescription;
    }
    
    /**
     * Returns the details for the StaffServiceCategory object.
     * 
     * @return - the StaffServiceCategory object details.
     */
    @Override
    public String toString() {

        return STAFF_SERVICE_CATEGORY_ID + serviceId + NAME_VALUE + description;
    }
    
}
