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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.SectionHead;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the SectionHeadDao object.
 * 
 * @author Virtusa Corporation
 */
public class SectionHeadDaoImpl extends BaseDaoImpl<SectionHead> implements SectionHeadDao {
    
    /** The constant for String "sectionHeadList". */
    private static final String SECTION_HEAD_LIST = "sectionHeadList";
    
    /** The constant for String "yyyy". */
    private static final String YEAR_FORMAT = "yyyy";
    
    /** The constant for Character "%". */
    private static final String MODULO_STRING = "%";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(SectionHeadDaoImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<SectionHead> sectionHeadSearch(Staff staff, String year) throws AkuraAppException {

        String lastName = MODULO_STRING + staff.getLastName() + MODULO_STRING;
        String empNo = staff.getRegistrationNo();
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(YEAR_FORMAT);
        try {
            date = (Date) formatter.parse(year);
        } catch (ParseException e) {
            throw new AkuraAppException(AkuraConstant.DATE_CONVERSION_ERROR, e);
        }
        return (List<SectionHead>) getHibernateTemplate().findByNamedQuery(SECTION_HEAD_LIST, lastName, lastName,
                empNo, empNo, date, date);
    }
    
    /**
     * Get the current Sectional Heads.
     * 
     * @param today The current date.
     * @exception AkuraAppException - The exception details that occurred when getting the list of current
     *            sectional heads.
     * @return list of Section Head members.
     */
    @SuppressWarnings("unchecked")
    public List<SectionHead> getCurrentSectionalHeadList(Date today) throws AkuraAppException {

        try {
            
            return (List<SectionHead>) getHibernateTemplate().findByNamedQuery("getCurrentSectionalHeadsList", today);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_RETRIEVING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Gets the section head list if any, for given staff id and departure date.
     * 
     * @param staffId the id of the staff member
     * @param dateOfDeparture the date
     * @return section head list
     * @throws AkuraAppException when exception occurs.
     */
    @SuppressWarnings("unchecked")
    public List<SectionHead> getSectionalHeadsById(int staffId, Date dateOfDeparture) throws AkuraAppException {

        try {
            return (List<SectionHead>) getHibernateTemplate().findByNamedQuery("getCurrentSectionalHeadsById", staffId,
                    dateOfDeparture);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_RETRIEVING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Update the section head list with correct end date.
     * 
     * @param updatedSectionHeadList selection head list
     * @throws AkuraAppException when exception occurs.
     */
    public void updateList(List<SectionHead> updatedSectionHeadList) throws AkuraAppException {

        try {
            getHibernateTemplate().saveOrUpdateAll(updatedSectionHeadList);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_RETRIEVING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Delete the section head lists after the departure date.
     * 
     * @param deletedSectionHeadList selection head list
     * @throws AkuraAppException when exception occurs.
     */
	public void deleteList(List<SectionHead> deletedSectionHeadList)
			throws AkuraAppException {

		try {
			getHibernateTemplate().deleteAll(deletedSectionHeadList);
		} catch (DataAccessException e) {
			LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_RETRIEVING_THE_OBJECT
					+ e.toString());
			throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
		}
	}
    
}
