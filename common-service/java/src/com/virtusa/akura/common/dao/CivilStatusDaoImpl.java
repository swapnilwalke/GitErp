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
import com.virtusa.akura.api.dto.CivilStatus;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;


/**
 * This class provides persistence layer functionality for the working
 * civil status object.
 * 
 * @author Virtusa Corporation
 *
 */

public class CivilStatusDaoImpl extends BaseDaoImpl<CivilStatus> implements CivilStatusDao{

    /** String constant for holding respective query name. */
    private static final String GET_ANY_CIVILSTATUS_BY_NAME = "getAnyCivilStatusByName";
    
    /**
     * Retrieve all the civil status Types.
     * 
     * @param civilStatusName The user type of the civil status.
     * @return CivilStatus the user with the user name "userName".
     * @throws AkuraAppException Detail Exceptions.
     */
    @SuppressWarnings("unchecked")
    public CivilStatus getAnyCivilStatusByName(String civilStatusName) throws AkuraAppException {

        List<CivilStatus> civilStatusList = null;
        CivilStatus civilStatus = null;
        
        try {
            civilStatusList = getHibernateTemplate().findByNamedQuery(GET_ANY_CIVILSTATUS_BY_NAME, civilStatusName);
            if (civilStatusList != null && !civilStatusList.isEmpty()) {
                civilStatus = civilStatusList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return civilStatus;
    }
}
