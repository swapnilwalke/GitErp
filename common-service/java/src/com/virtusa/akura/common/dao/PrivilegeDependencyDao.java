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
import com.virtusa.akura.api.dto.PrivilegeDependency;
import com.virtusa.akura.api.dto.Tab;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the PrivilegeDependency domain object.
 * 
 * @author Virtusa Corporation
 */
public interface PrivilegeDependencyDao extends BaseDao<PrivilegeDependency> {
    
    /**
     * get Dependencies privilege ID list.
     * 
     * @param privilegeIdList - privilege IDs list.
     * @return list of dependencies IDs.
     * @throws AkuraAppException when fails.
     */
    List<Privilege> getDependenciesList(List<Integer> privilegeIdList) throws AkuraAppException;
    
    /**
     * get Dependencies tab ID list.
     * 
     * @param privilegeIdList - privilege IDs list.
     * @return list of dependencies IDs.
     * @throws AkuraAppException when fails.
     */
    List<Tab> getDependenciesTabIdList(List<Integer> privilegeIdList) throws AkuraAppException;
}
