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

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * Implementation for UserRoleDao.
 * 
 * @author Virtusa Corporation
 */
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements UserRoleDao {
    
    /** String constant for holding respective query name. */
    private static final String GET_USER_ROLE_BY_ROLE_NAME = "getUserRoleByRoleName";
    
    /**
     * Retrieve user role role with the rolename.
     * 
     * @param role The user role name of the user role.
     * @return userRole the user with the user role name "role".
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    public UserRole getUserRoleByRoleName(String role) throws AkuraAppException {

        List<UserRole> userRoleList = null;
        UserRole userRole = null;
        
        try {
            userRoleList = getHibernateTemplate().findByNamedQuery(GET_USER_ROLE_BY_ROLE_NAME, role);
            if (userRoleList != null && !userRoleList.isEmpty()) {
                userRole = userRoleList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return userRole;
    }
}
