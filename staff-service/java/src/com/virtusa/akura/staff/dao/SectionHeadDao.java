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

package com.virtusa.akura.staff.dao;

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.SectionHead;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the Class SectionHead object.
 * 
 * @author Virtusa Corporation
 */
public interface SectionHeadDao extends BaseDao<SectionHead> {
    
    /**
     * Search Section Head members.
     * 
     * @param staff staff member to be searched.
     * @param year year of interest.
     * @exception AkuraAppException - The exception details that occurred when searching ClassTeachers for a
     *            given criteria.
     * @return list of Section Head members.
     */
    List<SectionHead> sectionHeadSearch(Staff staff, String year) throws AkuraAppException;
    
    /**
     * Get the current Sectional Heads.
     * 
     * @param today The current date.
     * @exception AkuraAppException - The exception details that occurred when getting the list of current
     *            sectional heads.
     * @return list of Section Head members.
     */
    List<SectionHead> getCurrentSectionalHeadList(Date today) throws AkuraAppException;
    
    /**
     * Gets the section head list if any, for given staff id and departure date.
     * 
     * @param staffId the id of the staff member
     * @param dateOfDeparture the date
     * @return section head list
     * @throws AkuraAppException when exception occurs.
     */
    List<SectionHead> getSectionalHeadsById(int staffId, Date dateOfDeparture) throws AkuraAppException;
    
    /**
     * Update the section head list with correct end date..
     * 
     * @param updatedSectionHeadList selection head list
     * @throws AkuraAppException when exception occurs.
     */
    void updateList(List<SectionHead> updatedSectionHeadList) throws AkuraAppException;
    
    /**
     * Delete the section heads after the departure date.
     * 
     * @param deletedSectionHeadList selection head list
     * @throws AkuraAppException when exception occurs.
     */
    void deleteList(List<SectionHead> deletedSectionHeadList) throws AkuraAppException;
    
}
