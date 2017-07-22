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

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.FaithLifeRating;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides the persistence level implementation for the FaithLifeRating domain object.
 * 
 * @author Virtusa Corporation
 */
public class FaithLifeRatingDaoImpl extends BaseDaoImpl<FaithLifeRating> implements FaithLifeRatingDao {
    
    /** Logger to log the exceptions. */    
    private static final Logger LOG = Logger.getLogger(FaithLifeRatingDaoImpl.class);
    
    /** key to hold error message to log. */    
    private static final String ERROR_WHILE_RETRIEVING_FAITH_LIFE_RATING_INFO =
            "Error while retrieving faithLifeRating info ---> ";
    
    /** The Constant GET_FAITH_LIFE_RATE_FOR_STUDENT_BY_STUDENT_ID_ONLY. */
    private static final String GET_FAITH_LIFE_RATE_FOR_STUDENT_BY_STUDENT_ID_ONLY =
            "getFaithLifeRateForStudentByStudentIdOnly";
    
    /** key to hold the named query getFaithLifeRateForStudentById. */
    private static final String GET_FAITH_LIFE_RATE_FOR_STUDENT_BY_ID = "getFaithLifeRateForStudentById";
    
    /** key to hold the named query getFaithLifeRateForStudentById. */
    private static final String GET_FAITH_LIFE_RATE_VALUE_FOR_STUDENT_BY_ID = "getFaithLifeRateingForStudentById";

    
    /**
     * retrieving list of FaithLifeRating for a given studentId.
     * 
     * @param studentId the student Id to get Sports detail.
     * @param year the Date
     * @return List {@link FaithLifeRating}
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<FaithLifeRating> getFaithLifeRatingsListForStudent(int studentId, Date year) throws AkuraAppException {

        List<FaithLifeRating> faithLifeRatings;
        try {
            if (year != null) {
                faithLifeRatings =
                        getHibernateTemplate().findByNamedQuery(GET_FAITH_LIFE_RATE_FOR_STUDENT_BY_ID, studentId, year);
            } else {
                faithLifeRatings =
                        getHibernateTemplate().findByNamedQuery(GET_FAITH_LIFE_RATE_FOR_STUDENT_BY_STUDENT_ID_ONLY,
                                studentId);
            }
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_FAITH_LIFE_RATING_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return faithLifeRatings;
    }
    
    /**
     * retrieving list of FaithLifeRating for a given studentId.
     * 
     * @param studentId the student Id to get Sports detail.
     * @param year -current year
     * @return List {@link FaithLifeRating}
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    @Override
  public List<Object> getFaithLifeRateListForStudent(int studentId, Date year) throws AkuraAppException {

        try {

            List<Object> faithliferating = getHibernateTemplate().
                    findByNamedQuery(GET_FAITH_LIFE_RATE_VALUE_FOR_STUDENT_BY_ID, studentId,year);

            return faithliferating;
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }

}
