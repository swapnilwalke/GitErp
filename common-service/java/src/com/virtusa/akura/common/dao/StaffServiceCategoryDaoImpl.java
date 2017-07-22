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

package com.virtusa.akura.common.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StaffServiceCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for StaffServiceCategoryDao interface. It handles all the persistent
 * related functionalities for the StaffServiceCategory object.
 * 
 * @author Virtusa Corporation
 */

public class StaffServiceCategoryDaoImpl extends BaseDaoImpl<StaffServiceCategory> implements StaffServiceCategoryDao {
    
    /** String constant for holds respective query name `getAnyStaffServiceCategoryByName`. */
    private static final String GET_ANY_STAFF_SERVICE_CATEGORY_BY_NAME = "getStaffServiceCategoryByName";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StaffServiceCategoryDaoImpl.class);
    
    /** String that holds the error message for log. */
    private static final String ERROR_WHILE_SEARCHING_STAFF_SERVICE_CATEGORY =
            "Error While Searching Staff Service Category --->";
    
    /**
     * Retrieve all Staff Service Category by its Description(Name).
     * 
     * @param staffServiceCategoryName - searching Staff Service Category name.
     * @return StaffServiceCategory
     * @throws AkuraAppException - Detailed Exception
     */
    @SuppressWarnings("unchecked")
    public StaffServiceCategory getStaffServiceCategoryByName(String staffServiceCategoryName)
                                                                        throws AkuraAppException {

        List<StaffServiceCategory> staffServiceCategoryList = null;
        StaffServiceCategory staffServiceCategory = null;
        
        try {
            staffServiceCategoryList =
                    getHibernateTemplate().findByNamedQuery(GET_ANY_STAFF_SERVICE_CATEGORY_BY_NAME,
                            staffServiceCategoryName);
            if (staffServiceCategoryList != null && !staffServiceCategoryList.isEmpty()) {
                staffServiceCategory = staffServiceCategoryList.get(0);
                
            }
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_SEARCHING_STAFF_SERVICE_CATEGORY + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return staffServiceCategory;
        
    }
    
}
