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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.Privilege;
import com.virtusa.akura.api.dto.RolePrivilege;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the RolePrivilege domain object.
 * 
 * @author Virtusa Corporation
 */
public interface RolePrivilegeDao extends BaseDao<RolePrivilege> {
    
    /**
     * Delete RolePrivileges by role.
     * 
     * @param userRole - userRole of the records.
     * @throws AkuraAppException - The exception details that occurred when deleting the RolePrivileges By
     *         user Role.
     */
    void deleteRolePrivilegesByUserRole(UserRole userRole) throws AkuraAppException;
    
    /**
     * get Privileges by role.
     * 
     * @param userRole - userRole of the records.
     * @return list of privileges.
     * @throws AkuraAppException - The exception details that occurred when deleting the RolePrivileges By
     *         user Role.
     */
    List<Privilege> getPrivilegesByUserRole(UserRole userRole) throws AkuraAppException;
    
    /**
     * get RolePrivileges by role.
     * 
     * @param userRole - userRole of the records.
     * @return list of privileges.
     * @throws AkuraAppException - The exception details that occurred when deleting the RolePrivileges By
     *         user Role.
     */
    List<RolePrivilege> getRolePrivilegesByUserRole(UserRole userRole) throws AkuraAppException;
}
