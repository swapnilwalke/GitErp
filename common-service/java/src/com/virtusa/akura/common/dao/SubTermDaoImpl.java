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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for SubTermDao interface. It handles all the
 * persistent related functionalities for the sub term object.
 *
 * @author Virtusa Corporation
 *
 */
public class SubTermDaoImpl extends BaseDaoImpl<SubTerm> implements SubTermDao {

    /**
     * String attribute for query name sub term list.
     */
    private static final String QUERY_NAME_SUB_TERM_LIST = "subTermList";
    
    /**
     * String attribute for query name sub term list.
     */
    private static final String GET_ANY_SUBTERM_BY_DESCRIPTION = "getAnySubTermByDescription";
    
    /**
     * String attribute for query name sub term list.
     */
    private static final String GET_ANY_SUBTERM_BY_TODATE = "getAnySubTermByTodate";
    
    /**
     * String attribute for query name sub term list.
     */
    private static final String GET_ANY_SUBTERM_BY_FROMDATE = "getAnySubTermByFromdate";
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(SubTermDaoImpl.class);

    /**
     * Get list of sub terms of a term.
     *
     * @param termId - termId id.
     * @return a list of  SubTerm.
     * @throws AkuraAppException throws if exception occurs when
     */
    @SuppressWarnings("unchecked")
	public List<SubTerm> getSubTermsOfATerm(int termId)throws AkuraAppException {

        List<SubTerm> subTermList = null;
        try {
            subTermList = getHibernateTemplate().findByNamedQuery(QUERY_NAME_SUB_TERM_LIST, termId);
        } catch (DataAccessException e) {
            LOG.error("Error while retrieving subterms ---> " + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return subTermList;
    }
    
    /**
     * Retrieve the subTerm by passing the subTermName and termId.
     * @param termId - Integer
     * @param subTermName - String
     * @return SubTerm object.
     * @throws AkuraAppException throws the detailed exception.
     */
    @SuppressWarnings("unchecked")
     public SubTerm getAnySubTermByName(int termId,String subTermName) throws AkuraAppException{
         List<SubTerm> subTermList = null;
         SubTerm subTerm = null;
         
         try {
             subTermList = getHibernateTemplate().findByNamedQuery
             (GET_ANY_SUBTERM_BY_DESCRIPTION, termId,subTermName);
             if (subTermList != null && !subTermList.isEmpty()) {
                 subTerm = subTermList.get(0);
             }
         } catch (DataAccessException e) {
             throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
         }
         
         return subTerm;
     }
    
    /**
     * Retrieve the subTerm by passing the toDate.
     * 
     * @param toDate - Date
     * @return SubTerm object.
     * @throws AkuraAppException throws the detailed exception.
     */
    @SuppressWarnings("unchecked")
     public SubTerm getAnySubTermByToDate(Date toDate) throws AkuraAppException{
         List<SubTerm> subTermList = null;
         SubTerm subTerm = null;
         
         try {
             subTermList = getHibernateTemplate().findByNamedQuery
             (GET_ANY_SUBTERM_BY_TODATE, toDate);
             if (subTermList != null && !subTermList.isEmpty()) {
                 subTerm = subTermList.get(0);
             }
         } catch (DataAccessException e) {
             throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
         }
         
         return subTerm;
     }
    
    /**
     * Retrieve the subTerm by passing the fromDate.
     * 
     * @param fromDate - Date
     * @return SubTerm object.
     * @throws AkuraAppException throws the detailed exception.
     */
    @SuppressWarnings("unchecked")
     public SubTerm getAnySubTermFromDate(Date fromDate) throws AkuraAppException{
         List<SubTerm> subTermList = null;
         SubTerm subTerm = null;
         
         try {
             subTermList = getHibernateTemplate().findByNamedQuery
             (GET_ANY_SUBTERM_BY_FROMDATE, fromDate);
             if (subTermList != null && !subTermList.isEmpty()) {
                 subTerm = subTermList.get(0);
             }
         } catch (DataAccessException e) {
             throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
         }
         
         return subTerm;
     }
}
