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

package com.virtusa.akura.student.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Donation;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * DonationDaoImpl implementation of DonationDao interface.
 * 
 * @author Virtusa Corporation
 */
public class DonationDaoImpl extends BaseDaoImpl<Donation> implements DonationDao {
    
    /**
     * String attribute for query name getDonationsByStudentId.
     */
    private static final String GET_DONATIONS_BY_STUDENT_ID = "getDonationsByStudentId";
    
    /**
     * String attribute for query name getDonations.
     */
    private static final String GET_DONATIONS = "getDonations";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(DonationDaoImpl.class);
    
    /**
     * Retrieve all the available Student Parent information given by student Id.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @return List of StudentParent
     * @throws AkuraAppException SMS Exception
     */
    @SuppressWarnings("unchecked")
    public final List<Donation> getDonationsByStudentId(int studentId) throws AkuraAppException {

        try {
            return (List<Donation>) getHibernateTemplate().findByNamedQuery(GET_DONATIONS_BY_STUDENT_ID, studentId);
            
        } catch (DataAccessException e) {
            LOG.error("error occured while getting DonationsByStudentId  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Retrieve all the available donation information given by purpose, amount, date and donationTypeId.
     * 
     * @param purpose - specifies the purpose of donation
     * @param amount - specifies the amount of donation
     * @param date - specifies the date of donation
     * @param donationTypeId - specifies the date of donationTypeId
     * @return List of Donations
     * @throws AkuraAppException SMS Exception
     */
    @SuppressWarnings("unchecked")
    public List<Donation> getDonations(String purpose, String amount, Date date, int donationTypeId)
            throws AkuraAppException {

        try {
            return (List<Donation>) getHibernateTemplate().findByNamedQuery(GET_DONATIONS, purpose, amount, date,
                    donationTypeId);
            
        } catch (DataAccessException e) {
            LOG.error("error occured while getting Donations the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
}
