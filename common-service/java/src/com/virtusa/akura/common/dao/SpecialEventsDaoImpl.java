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

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the working
 * ParticipantCategory object.
 *
 * @author Virtusa Corporation
 */
public class SpecialEventsDaoImpl extends BaseDaoImpl<SpecialEvents> implements SpecialEventsDao {

    /** String constant for holding respective query name. */
    private static final String GET_ANY_SPECIALEVENTS_BY_NAME = "getAnySpecialEventsByName";
    
    /**
     * Retrieve all the SpecialEvents with its name.
     * 
     * @param event - String.
     * @param date - Date.
     * @return specialEvents - SpecialEvents.
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    public SpecialEvents getAnySpecialEventsByName(String event, Date date)
            throws AkuraAppException {

        List<SpecialEvents> specialEventsList = null;
        SpecialEvents specialEvents = null;
        
        try {
            specialEventsList =
                    getHibernateTemplate().findByNamedQuery(GET_ANY_SPECIALEVENTS_BY_NAME, event, date);
            if (specialEventsList != null && !specialEventsList.isEmpty()) {
                specialEvents = specialEventsList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return specialEvents;
    }
   
}
