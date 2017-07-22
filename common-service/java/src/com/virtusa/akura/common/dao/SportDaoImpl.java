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
import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;


/**
 * This class provides the persistence level implementation for the Sport
 * domain object.
 * @author Virtusa Corporation
 *
 */

public class SportDaoImpl extends  BaseDaoImpl<Sport> implements SportDao{

    /** String constant for holding respective query name. */
    private static final String GET_ANY_SPORT_BY_NAME = "getAnySportByName";
    
    /**
     * Retrieve all the sport's with the name.
     * 
     * @param sportName The name of the sport.
     * @return sport - the sport with the sportName.
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    public Sport getAnySportByName(String sportName) throws AkuraAppException {

        List<Sport> sportList = null;
        Sport sport = null;
        
        try {
            sportList = getHibernateTemplate().findByNamedQuery(GET_ANY_SPORT_BY_NAME, sportName);
            if (sportList != null && !sportList.isEmpty()) {
                sport = sportList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return sport;
    }
}
