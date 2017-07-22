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

package com.virtusa.akura.student.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * ParentDaoImpl implementation of ParentDao interface.
 * 
 * @author Virtusa Corporation
 */
public class ParentDaoImpl extends BaseDaoImpl<Parent> implements ParentDao {
    
    /**
     * String attribute for query name getParentByNIC.
     */
    private static final String GET_PARENT_BY_NIC = "getParentByNIC";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(ParentDaoImpl.class);
    
    /**
     * Retrieve available Parent information given by NIC number.
     * 
     * @param nicNo specifies the NIC Number, defined by a String.
     * @return Parent object.
     * @throws AkuraAppException SMS Exception
     */
    public Parent getParentInfoByNIC(String nicNo) throws AkuraAppException {
    
        try {
            Parent nicParent = null;
            
            @SuppressWarnings("unchecked")
            List<Parent> parentList = (List<Parent>) getHibernateTemplate().findByNamedQuery(GET_PARENT_BY_NIC, nicNo);
            
            if (!parentList.isEmpty()) {
                nicParent = parentList.get(0);
            }
            
            return nicParent;
            
        } catch (DataAccessException e) {
            LOG.error("error occured while searching  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
}
