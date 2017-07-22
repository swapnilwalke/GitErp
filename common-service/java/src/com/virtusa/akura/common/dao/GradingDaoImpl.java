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
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for GradingDao interface.
 * It handles all the persistent related functionalities for the
 * Grading object.
 *
 * @author Virtusa Corporation
 *
 */
public class GradingDaoImpl extends BaseDaoImpl<Grading> implements GradingDao {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(GradingDaoImpl.class);

    /** Represents the query name for the key of the Grade by description. */
    private static final String FIND_GRADING_BY_DES = "findGradingByDes";
    
    /** Represents the query name for the key of the Grade by description. */
    private static final String GET_ANY_GRADE_BY_DESCRIPTION = "getAnyGradeByDescription";
    
    /** Represents the query name for the key of the Grade by description. */
    private static final String GET_ANY_GRADE_BY_INFORMATION = "getAnyGradeByInformation";
    
    /** Represents the query name for the key of the Grade by description. */
    private static final String GET_ANY_GRADE_BY_ACROYNM = "getAnyGradeByAcroynm";

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<Integer> findGradingByDes(final String marks) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_GRADING_BY_DES , marks);
        } catch (DataAccessException ex) {
            LOG.error(AkuraConstant.DB_CONNECTION_ERROR + ex.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }
    
    /**
     * Retrieve the grades by passing the grade acronym and description.
     * 
     * @param gradeAcronym - String
     * @param decription - String
     * @return Grade object.
     * @throws AkuraAppException throws the detailed exception.
     */
    @SuppressWarnings("unchecked")
    public Grading getAnyGradeByInformation(String gradeAcronym,String decription) throws AkuraAppException {

        List<Grading> gradingList = null;
        Grading grading = null;
        
        try {
            gradingList = getHibernateTemplate().findByNamedQuery
            (GET_ANY_GRADE_BY_INFORMATION, gradeAcronym,decription);
            if (gradingList != null && !gradingList.isEmpty()) {
                grading = gradingList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return grading;
    }
    
    /**
     * Retrieve the grades by passing the grade acronym and description.
     * 
     * 
     * @param decription - String
     * @return Grade object.
     * @throws AkuraAppException throws the detailed exception.
     */
    @SuppressWarnings("unchecked")
    public Grading getAnyGradeByDecription(String decription) throws AkuraAppException {

        List<Grading> gradingList = null;
        Grading grading = null;
        
        try {
            gradingList = getHibernateTemplate().findByNamedQuery
            (GET_ANY_GRADE_BY_DESCRIPTION,decription);
            if (gradingList != null && !gradingList.isEmpty()) {
                grading = gradingList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return grading;
    }
    
    /**
     * Retrieve the grades by passing the grade acronym and description.
     * 
     * 
     * @param gradeAcronym - String
     * @return Grade object.
     * @throws AkuraAppException throws the detailed exception.
     */
    @SuppressWarnings("unchecked")
    public Grading getAnyGradeByAcronym(String gradeAcronym) throws AkuraAppException {

        List<Grading> gradingList = null;
        Grading grading = null;
        
        try {
            gradingList = getHibernateTemplate().findByNamedQuery
            (GET_ANY_GRADE_BY_ACROYNM,gradeAcronym);
            if (gradingList != null && !gradingList.isEmpty()) {
                grading = gradingList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return grading;
    }
}
