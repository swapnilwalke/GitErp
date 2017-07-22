/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the StaffCategory object.
 * 
 * @author Virtusa Corporation
 */
public class StaffCategoryDaoImpl extends BaseDaoImpl<StaffCategory> implements StaffCategoryDao {
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StaffCategoryDaoImpl.class);
    
    /** The constant for String "isAcademicStaffCategory". */
    private static final String IS_ACADEMIC_STAFF_CATEGORY = "isAcademicStaffCategory";
    
    /** The Constant ERROR_WHEN_CHECK_WHETHER_IT_S_ACADEMIC_STAFF_CATEGORY. */
    private static final String ERROR_WHEN_CHECK_WHETHER_IT_S_ACADEMIC_STAFF_CATEGORY =
            "Error when check whether it's Academic Staff Category--->";
    
    /**
     * {@inheritDoc}
     */
    public boolean isAcademicStaffCategory(int categoryId) throws AkuraAppException {

        boolean isAcademic = false;
        try {
            isAcademic =
                    (Boolean) getHibernateTemplate().findByNamedQuery(IS_ACADEMIC_STAFF_CATEGORY, categoryId).get(0);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHEN_CHECK_WHETHER_IT_S_ACADEMIC_STAFF_CATEGORY + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR + e);
        }
        return isAcademic;
        
    }
    
}
