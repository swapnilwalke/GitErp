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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * GradingDao interface provides persistent related
 * functionalities for Grading object.
 *
 * @author Virtusa Corporation
 */
public interface GradingDao extends BaseDao<Grading> {

    /**
     * Finds the grading by the given description.
     *
     * @param marks - the description of the marks.
     * @return - the grading by the given description.
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<Integer> findGradingByDes(String marks)throws AkuraAppException;
    
    /**
     * Retrieve the grades by passing the grade acronym and description.
     * 
     * @param strGradeAcronym - String
     * @param decription - String
     * @return Grade object.
     * @throws AkuraAppException throws the detailed exception.
     */
    Grading getAnyGradeByInformation(String strGradeAcronym,String decription) throws AkuraAppException;
    
    /**
     * Retrieve the grades by passing the grade acronym and description.
     * 
     * 
     * @param decription - String
     * @return Grade object.
     * @throws AkuraAppException throws the detailed exception.
     */
    Grading getAnyGradeByDecription(String decription) throws AkuraAppException;
    
    /**
     * Retrieve the grades by passing the grade acronym and description.
     * 
     * 
     * @param strGradeAcronym - String
     * @return Grade object.
     * @throws AkuraAppException throws the detailed exception.
     */
    Grading getAnyGradeByAcronym(String strGradeAcronym) throws AkuraAppException;
}
