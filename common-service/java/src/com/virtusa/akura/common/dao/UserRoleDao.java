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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the UserRole object.
 * 
 * @author Virtusa Corporation
 */
public interface UserRoleDao extends BaseDao<UserRole> {
    
    /**
     * Retrieve the user role by passing the user role's rolename.This returns any user role with the user
     * role name passed.
     * 
     * @param role - String
     * @return UserRole object.
     * @throws AkuraAppException SMS Exceptions.
     */
    UserRole getUserRoleByRoleName(String role) throws AkuraAppException;
}
