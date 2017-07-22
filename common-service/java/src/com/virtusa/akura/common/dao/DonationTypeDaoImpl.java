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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.DonationType;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * DonationTypeDaoImpl implementation of DonationTypeDao interface.
 * 
 * @author Virtusa Corporation
 */
public class DonationTypeDaoImpl extends BaseDaoImpl<DonationType> implements DonationTypeDao {
    
    /**Logger to log the exceptions.*/
    private static final Logger LOG = Logger.getLogger(DonationTypeDaoImpl.class);
    
    /** String that holds the error message to logged.*/
    private static final String ERROR_WHILE_SEARCHING_DONATION_TYPE =
                                                  "Error While Searching Donation Type --->";
    
    /** String constant for holds respective query name `getAnyDonationTypeByName`. */
    
    private static final String GET_ANY_DONATION_TYPE_BY_NAME = "getAnyDonationTypeByName";
    

    
    /**
     * Retrieve all Donation Type by its Description(Name).
     * 
     * @param donationTypeName - searching Donation Type name.
     * @return DonationType object
     * @throws AkuraAppException - Detailed Exception
     */
    @SuppressWarnings("unchecked")
    public DonationType getDonationTypeByName(String donationTypeName) throws AkuraAppException {

        List<DonationType> donationTypeList = null;
        DonationType doantionType = null;
        
        try {
            donationTypeList =
                    getHibernateTemplate().findByNamedQuery(GET_ANY_DONATION_TYPE_BY_NAME, donationTypeName);
            if (donationTypeList != null && !donationTypeList.isEmpty()) {
                doantionType = donationTypeList.get(0);              
            }
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_SEARCHING_DONATION_TYPE + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return doantionType;        
    }   
}
