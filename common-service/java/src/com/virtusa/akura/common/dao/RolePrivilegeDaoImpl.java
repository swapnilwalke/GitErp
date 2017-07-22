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
import com.virtusa.akura.api.dto.RolePrivilege;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the RolePrivilege object.
 * 
 * @author Virtusa Corporation
 */
public class RolePrivilegeDaoImpl extends BaseDaoImpl<RolePrivilege> implements RolePrivilegeDao {
    
    /** The constant for String "getPrivilegesByUserRole". */
    private static final String GET_PRIVILEGES_BY_USER_ROLE = "getPrivilegesByUserRole";
    
    /** The constant for String "getRolePrivilegesByUserRole". */
    private static final String GET_ROLE_PRIVILEGES_BY_USER_ROLE = "getRolePrivilegesByUserRole";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(RolePrivilegeDaoImpl.class);
    
    /**
     * Delete RolePrivileges by role.
     * 
     * @param userRole - userRole of the records.
     * @throws AkuraAppException - The exception details that occurred when deleting the RolePrivileges By
     *         user Role.
     */
    public void deleteRolePrivilegesByUserRole(UserRole userRole) throws AkuraAppException {

        try {
            
            getHibernateTemplate().deleteAll(getRolePrivilegesByUserRole(userRole));
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * get RolePrivileges by role.
     * 
     * @param userRole - userRole of the records.
     * @return list of privileges.
     * @throws AkuraAppException - The exception details that occurred when deleting the RolePrivileges By
     *         user Role.
     */
    @SuppressWarnings("unchecked")
    public List<RolePrivilege> getRolePrivilegesByUserRole(UserRole userRole) throws AkuraAppException {

        try {
            
            return (List<RolePrivilege>) getHibernateTemplate().findByNamedQuery(GET_ROLE_PRIVILEGES_BY_USER_ROLE,
                    userRole);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * get Privileges by role.
     * 
     * @return list of privileges.
     * @param userRole - userRole of the records.
     * @throws AkuraAppException - The exception details that occurred when deleting the RolePrivileges By
     *         user Role.
     */
    @SuppressWarnings("unchecked")
    public List<Privilege> getPrivilegesByUserRole(UserRole userRole) throws AkuraAppException {

        try {
            
            return (List<Privilege>) getHibernateTemplate().findByNamedQuery(GET_PRIVILEGES_BY_USER_ROLE, userRole);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
}
