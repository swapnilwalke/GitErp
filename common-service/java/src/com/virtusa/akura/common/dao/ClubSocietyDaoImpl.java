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
import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for ClubSocietyDao interface.
 * It handles all the persistent related functionalities for the
 * ClubSocietyDao object.
 *
 * @author Virtusa Corporation
 */
public class ClubSocietyDaoImpl extends BaseDaoImpl<ClubSociety> implements ClubSocietyDao{

    /** String constant for holding respective query name. */
    private static final String GET_ANY_CLUBSOCIETY_BY_NAME = "getAnyClubSocietyByName";
    
    /**
     * Retrieve all the ClubSociety with its name.
     * 
     * @param clubSocietyName - String.
     * @return clubSociety - ClubSociety.
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    public ClubSociety getAnyClubSocietyByName(String clubSocietyName)
            throws AkuraAppException {

        List<ClubSociety> clubSocietyList = null;
        ClubSociety clubSociety = null;
        
        try {
            clubSocietyList =
                    getHibernateTemplate().findByNamedQuery(GET_ANY_CLUBSOCIETY_BY_NAME, clubSocietyName);
            if (clubSocietyList != null && !clubSocietyList.isEmpty()) {
                clubSociety = clubSocietyList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return clubSociety;
    }
    
}
