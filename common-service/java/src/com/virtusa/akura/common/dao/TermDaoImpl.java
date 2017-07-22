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
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for TermDao interface. It handles all
 * the persistent related functionalities for the term object.
 *
 * @author Virtusa Corporation
 *
 */
public class TermDaoImpl extends BaseDaoImpl<Term> implements TermDao {
    
    /** String constant for holding respective query name. */
    private static final String GET_TERM_BY_NAME = "getTermByName";
    
    /** Represents the query name for the number of term count. */
    private static final String GET_NO_OF_TERMS = "getNoOfTerms";
    
    /**
     * Retrieve all the term's with the termName.
     * 
     * @param termName - String
     * @param startDate start date
     * @param endDate end date
     * @return term the term with the name "termName".
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    public Term getTermByName(String termName, Date startDate, Date endDate) throws AkuraAppException {

        List<Term> termList = null;
        Term term = null;
        
        try {
            termList = getHibernateTemplate().findByNamedQuery(GET_TERM_BY_NAME, termName,startDate,endDate);
            if (termList != null && !termList.isEmpty()) {
                term = termList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return term;
    }

    /**  {@inheritDoc} */
    public List<Long> getNoOfTerms() throws AkuraAppException {
        
        try {
            return getHibernateTemplate().findByNamedQuery(GET_NO_OF_TERMS);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
    }
}
