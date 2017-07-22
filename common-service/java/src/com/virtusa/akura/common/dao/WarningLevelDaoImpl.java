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

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.WarningLevel;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This class provides persistence layer functionality for the warning level domain object.
 * 
 * @author Virtusa Corporation
 */

public class WarningLevelDaoImpl extends BaseDaoImpl<WarningLevel> implements WarningLevelDao {
    
    /**
     * Check whether the Warning Level is exists.
     * 
     * @param description - String
     * @param color - String
     * @throws AkuraAppException Detailed exception
     * @return returns a boolean value.
     */
    
    public boolean isExistWarningLevel(String description, String color) {
    
        boolean isExist = false;
        
        List<WarningLevel> warnigLevelList =
                getHibernateTemplate().findByNamedQuery("isExistWarningLevel", description, color);
        if (warnigLevelList != null && !warnigLevelList.isEmpty()) {
            isExist = true;
        }
        
        return isExist;
    }
    
    /**
     * Check whether the Warning Level description is exists.
     * 
     * @param description - String
     * @param color - String
     * @throws AkuraAppException Detailed exception
     * @return returns a boolean value.
     */
    
    public boolean isExistWarningLevelDescription(String description) throws AkuraAppException {
    
        boolean isExist = false;
        
        List<WarningLevel> warnigLevelList =
                getHibernateTemplate().findByNamedQuery("isExistWarningLevelDescription", description);
        if (warnigLevelList != null && !warnigLevelList.isEmpty()) {
            isExist = true;
        }
        
        return isExist;
    }
    
    /**
     * Check whether the Warning Level color is exists.
     * 
     * @param description - String
     * @param color - String
     * @throws AkuraAppException Detailed exception
     * @return returns a boolean value.
     */
    
    public boolean isExistWarningLevelColor(String color) throws AkuraAppException {
    
        boolean isExist = false;
        
        List<WarningLevel> warnigLevelList = getHibernateTemplate().findByNamedQuery("isExistWarningLevelColor", color);
        if (warnigLevelList != null && !warnigLevelList.isEmpty()) {
            isExist = true;
        }
        
        return isExist;
    }
    
}
