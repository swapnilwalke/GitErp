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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.FaithLifeRating;
import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * This class provides the persistence level implementation for the FaithLifeRating 
 * domain object.
 * @author Virtusa Corporation
 *
 */
public interface FaithLifeRatingDao extends BaseDao<FaithLifeRating>{
    
    /**
     * retrieving list of FaithLifeRating for a given studentId.
     * @param studentId the student Id to get Sports detail. 
     * @param year the Date   
     * @return List {@link FaithLifeRating}
     * @throws AkuraAppException - The detailed exception.
     */
	List<FaithLifeRating> getFaithLifeRatingsListForStudent(int studentId,
			Date year) throws AkuraAppException;
	
	/**
     * retrieving list of FaithLifeRating for a given studentId.
     * @param studentId the student Id to get Sports detail. 
     * @param year - Current year   
     * @return List {@link FaithLifeRating}
     * @throws AkuraAppException - The detailed exception.
     */
	List<Object> getFaithLifeRateListForStudent(int studentId ,Date year
           ) throws AkuraAppException;


}
