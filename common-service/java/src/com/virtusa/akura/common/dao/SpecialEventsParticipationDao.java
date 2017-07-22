/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the SpecialEventsParticipation object.
 * 
 * @author Virtusa Corporation
 */
public interface SpecialEventsParticipationDao extends BaseDao<SpecialEventsParticipation> {
    
    /**
     * Retrieve SpecialEventsParticipation list for given special event.
     * 
     * @param selectSpecialEvent of type int
     * @return list of SpecialEventsParticipation objects
     * @throws AkuraAppException throws if exception occurs.
     */

    List<SpecialEventsParticipation> getSpecialEventParticipationListById(int selectSpecialEvent)
            throws AkuraAppException;
    

    /**
     * Retrieve SpecialEventsParticipation list for given special event.
     * 
     * @param selectSpecialEvent of type int
     * @return list of SpecialEventsParticipation objects
     * @throws AkuraAppException throws if exception occurs.
     */
    List<SpecialEventsParticipation> getSpecialEventsParticipationsList(int selectSpecialEvent)
            throws AkuraAppException;
    
    /**
     * Retrieve SpecialEventsParticipation list for given special event.
     * 
     * @param specialEvent of type SpecialEvents
     * @return list of SpecialEventsParticipation objects
     * @throws AkuraAppException throws if exception occurs.
     */
    List<SpecialEventsParticipation> getParticipantListBySpecialEvent(SpecialEvents specialEvent)
            throws AkuraAppException;
    
    /**
     * Deletes the relevant specialEventsParticipation object list.
     * 
     * @param specialEventsParticipationList - SpecialEventsParticipation list.
     * @throws AkuraAppException - if error occurs when deleting a specialEventsParticipation instance.
     */
    void deleteAllSpecialEventsParticipation(List<SpecialEventsParticipation> specialEventsParticipationList)
            throws AkuraAppException;

}
