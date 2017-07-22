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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the
 * SpecialEvents object.
 *
 * @author Virtusa Corporation
 */
public interface SpecialEventsDao extends BaseDao<SpecialEvents> {
    
    /**
     * Retrieve the SpecialEvents  by passing the SpecialEvents name.
     * This returns any SpecialEvents with the SpecialEvents name passed.
     * 
     * @param specialEvents - String
     * @param date - Date
     * @return SpecialEvents object.
     * @throws AkuraAppException SMS Exceptions.
     */
    SpecialEvents getAnySpecialEventsByName(String specialEvents, Date date) throws AkuraAppException;
   
}
