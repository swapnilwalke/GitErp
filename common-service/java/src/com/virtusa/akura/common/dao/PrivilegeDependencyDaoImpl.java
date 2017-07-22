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
import com.virtusa.akura.api.dto.Privilege;
import com.virtusa.akura.api.dto.PrivilegeDependency;
import com.virtusa.akura.api.dto.Tab;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the PrivilegeDependency object.
 * 
 * @author Virtusa Corporation
 */
public class PrivilegeDependencyDaoImpl extends BaseDaoImpl<PrivilegeDependency> implements PrivilegeDependencyDao {
    
    /** string constant for hold `privilegeIdList`. */
    private static final String PRIVILEGE_ID_LIST = "privilegeIdList";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(RoleTabDaoImpl.class);
    
    /** The constant for String "getDependenciesList". */
    private static final String GET_DEPENDENCIES_LIST = "getDependenciesList";
    
    /** The constant for String "getDependenciesTabList". */
    private static final String GET_DEPENDENCIES_TAB_LIST = "getDependenciesTabList";
    
    /**
     * get Dependencies privilege ID list.
     * 
     * @param privilegeIdList - privilege IDs list.
     * @return list of dependencies IDs.
     * @throws AkuraAppException when fails.
     */
    @SuppressWarnings("unchecked")
    public List<Privilege> getDependenciesList(List<Integer> privilegeIdList) throws AkuraAppException {

        try {
            
            return getHibernateTemplate().findByNamedQueryAndNamedParam(GET_DEPENDENCIES_LIST,
                    new String[] { PRIVILEGE_ID_LIST }, new Object[] { privilegeIdList });
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * get Dependencies tab ID list.
     * 
     * @param privilegeIdList - privilege IDs list.
     * @return list of dependencies IDs.
     * @throws AkuraAppException when fails.
     */
    @SuppressWarnings("unchecked")
    public List<Tab> getDependenciesTabIdList(List<Integer> privilegeIdList) throws AkuraAppException {

        try {
            
            return getHibernateTemplate().findByNamedQueryAndNamedParam(GET_DEPENDENCIES_TAB_LIST,
                    new String[] { PRIVILEGE_ID_LIST }, new Object[] { privilegeIdList });
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
