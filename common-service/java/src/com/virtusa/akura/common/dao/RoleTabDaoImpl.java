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
import com.virtusa.akura.api.dto.RoleTab;
import com.virtusa.akura.api.dto.Tab;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the RoleTab object.
 * 
 * @author Virtusa Corporation
 */
public class RoleTabDaoImpl extends BaseDaoImpl<RoleTab> implements RoleTabDao {
    
    /** The constant for String "getRoleTabsByUserRole". */
    private static final String GET_ROLE_TABS_BY_USER_ROLE = "getRoleTabsByUserRole";
    
    /** The constant for String "getRoleTabsByUserRole". */
    private static final String GET_TABS_BY_USER_ROLE = "getTabsByUserRole";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(RoleTabDaoImpl.class);
    
    /**
     * Delete Roletabs by role.
     * 
     * @param userRole - userRole of the records.
     * @throws AkuraAppException - The exception details that occurred when deleting the RoleTabs By user
     *         Role.
     */
    public void deleteRoleTabsByUserRole(UserRole userRole) throws AkuraAppException {

        try {
            
            getHibernateTemplate().deleteAll(getRoleTabsByUserRole(userRole));
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * get tabs by role.
     * 
     * @param userRole - userRole of the records.
     * @return list of tabs.
     * @throws AkuraAppException - The exception details that occurred when deleting the RoleTabs By user
     *         Role.
     */
    @SuppressWarnings("unchecked")
    public List<RoleTab> getRoleTabsByUserRole(UserRole userRole) throws AkuraAppException {

        try {
            
            return getHibernateTemplate().findByNamedQuery(GET_ROLE_TABS_BY_USER_ROLE, userRole);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * get Tabs by role.
     * 
     * @param userRole - userRole of the records.
     * @return list of tabs.
     * @throws AkuraAppException - The exception details that occurred when deleting the RolePrivileges By
     *         user Role.
     */
    @SuppressWarnings("unchecked")
    public List<Tab> getTabsByUserRole(UserRole userRole) throws AkuraAppException {

        try {
            
            return getHibernateTemplate().findByNamedQuery(GET_TABS_BY_USER_ROLE, userRole);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
