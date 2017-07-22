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

package com.virtusa.akura.student.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.ParentDonation;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * ParentDonationDaoImpl implementation of ParentDonationDao interface.
 * 
 * @author Virtusa Corporation
 */
public class ParentDonationDaoImpl extends BaseDaoImpl<ParentDonation> implements ParentDonationDao {
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(ParentDonationDaoImpl.class);
    
    /** The Constant GET_PARENT_DONATIONS_BY_DONATION_ID. */
    private static final String GET_PARENT_DONATIONS_BY_DONATION_ID = "getParentDonationsByDonationId";
    
    /**
     * Gets the parent donations by donation id.
     * 
     * @param donationId the donation id
     * @return the parent donations by donation id
     * @throws AkuraAppException the throw exception
     */
    @SuppressWarnings("unchecked")
    public List<ParentDonation> getParentDonationsByDonationId(int donationId) throws AkuraAppException {

        try {
            return (List<ParentDonation>) getHibernateTemplate().findByNamedQuery(GET_PARENT_DONATIONS_BY_DONATION_ID,
                    donationId);
            
        } catch (DataAccessException e) {
            LOG.error("error occured while getting ParentDonations By DonationId  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
