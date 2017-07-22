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

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Scholarship;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for ScholarshipDao interface. It handles all the persistent related
 * functionalities for the scholarship object.
 * 
 * @author Virtusa Corporation
 */
public class ScholarshipDaoImpl extends BaseDaoImpl<Scholarship> implements ScholarshipDao {

    /** The constant for String object "getSponsorship". */
    private static final String GET_SPONSORSHIP = "getSponsorship";
    
    /**
     * @return list of scholarship.
     * @throws AkuraAppException - The exception
     */
    public List<Scholarship> findScholarshiList() throws AkuraAppException {
    
        try {
            return null;
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
        
    }
    /**
     * @param scholarship Scholarship
     * @return list of scholarship.
     * @throws AkuraAppException - The exception
     */
    public List<String> findSponsorship(String scholarship) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_SPONSORSHIP, scholarship);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
        
    }
    
}
